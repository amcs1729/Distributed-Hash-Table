import java.util.concurrent.TimeUnit;

public class Stabilization extends Thread
{
    Node node;
    Hash hash;
    Utils utils;
    Stabilization(Node node)
    {
        this.node = node;
        this.hash = new Hash();
        this.utils = new Utils(node);
    }

    void check_successor()
    {
        if(node.getPort() == node.getSuccessor_port())
        {
            return ;
        }

        try {
            Request request = new Request("heart_beat",null,0 );
            SendMessage message = new SendMessage(request, node.getSuccessor_port());
            Response response = message.send();

            if(response.status)
            {
                return ;
            }

            else
            {
                System.out.println("Could not connect to SUCCESSOR @ PORT   "+node.getSuccessor_port());
                change_successor_list();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void update_successor_list()
    {
        int successor = node.getSuccessor_port();
        for(int i=1;i<6;i++)
        {
            Request request = new Request("send_successor" , null , 0);
            SendMessage message = new SendMessage(request, successor);
            Response response = message.send();
            node.successor_list[i] = response.int_response;
            successor = response.int_response;
        }
    }

    void change_successor_list()
    {
        for(int i=1;i<6;i++)
        {
            node.successor_list[i-1] = node.successor_list[i];
        }
        node.setSuccessor_hashed(hash.hash_string(Integer.toString(node.successor_list[0])));
        node.setSuccessor_port(node.successor_list[0]);
    }
    void check_predecessor()
    {
        if(node.getPredecessor_port() == -1)
        {
            return ;
        }

        try {
            Request request = new Request("heart_beat",null,0 );
            SendMessage message = new SendMessage(request, node.getPredecessor_port());
            Response response = message.send();
            if(response.status)
            {
                return ;
            }

            else
            {
                System.out.println("Could not connect to PREDECESSOR @ PORT   "+node.getPredecessor_port());
                node.setPredecessor_port(-1);
                node.setPredecessor_hashed(-1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void notify_successor()
    {
        Request request = new Request("change_predecessor", null, node.getPort());
        SendMessage message = new SendMessage(request, node.getSuccessor_port());
        message.send();
    }

    void stabilize()
    {
        Request request = new Request("send_predecessor", null, 0);
        SendMessage message = new SendMessage(request, node.getSuccessor_port());
        Response response = message.send();
        int x = response.int_response;
        if(node.getPort() != node.getSuccessor_port()) {
            int hashed_x = hash.hash_string(Integer.toString(x));
            if(x!= -1 && x!= node.getPort())
            {
                node.setSuccessor_port(x);
                node.setSuccessor_hashed(hashed_x);
            }
            notify_successor();
        }
    }

    void fix_fingers()
    {
        if(node.getPort() == node.getSuccessor_port())
        {
            return;
        }
        for(int i=0; i<node.fingertable.length; i++)
        {
            int successor = utils.find_appropriate(node.fingertable[i][0]);
            node.fingertable[i][1] = successor;
            node.fingertable[i][2] = hash.hash_string(Integer.toString(successor));
        }
    }

    @Override
    public void run()
    {
        while(true)
        {

            check_successor();
            check_predecessor();
            update_successor_list();
            stabilize();
            fix_fingers();


            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
