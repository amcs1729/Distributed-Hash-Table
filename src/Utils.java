public class Utils
{
    Node node;
    Hash hash;
    Utils(Node node)
    {
        this.node = node;
        this.hash = new Hash();
    }
    public int find_appropriate(int id)
    {
        if(node.getPredecessor_hashed() == -1)
        {
                System.out.println("CASE 1");
                return (node.getPort());
        }
        if (node.getPredecessor_hashed() != -1)
        {
            if(  (id> node.getPredecessor_hashed()) && (id<= node.getMyself_hashed())  )
            {
                System.out.println("CASE 2");
                return (node.getPort());
            }
        }

        if(  (id>node.getMyself_hashed())   &&   (id<= node.getSuccessor_hashed())  )
        {
            System.out.println("CASE 3");
            return (node.getSuccessor_port());
            // Returns the port number of the successsor
        }
        else
        {
            System.out.println("CASE 4");
            Request request = new Request("find_appropriate" ,null ,id);
            SendMessage message = new SendMessage(request, node.getSuccessor_port());
            Response response = message.send();

            return (response.int_response);
            // Returns whatever value it gets from it's successors
        }
    }

    public boolean change_predecessor(int node_port, int hashed_node)
    {
        // Check if predecessor is none or if it is correct
        boolean status = false;
        if(node.getPredecessor_hashed() == -1)
        {
            node.setPredecessor_port(node_port);
            node.setPredecessor_hashed(hashed_node);
            status = true;
        }

        else
        {
            if (hashed_node>node.getPredecessor_hashed())
            {
                node.setPredecessor_port(node_port);
                node.setPredecessor_hashed(hashed_node);
                status = true;
            }
        }
        return status;
    }
}
