public class Utils {
    Node node;
    Hash hash;

    Utils(Node node) {
        this.node = node;
        this.hash = new Hash();
    }

    public int closest_preceeding(int id) {
        // 2^  [i][0] .....  port  [i][1]  .....   hashed  [i][2]
        int diff = 29792458; // Since I am taking modulo 64, for any valid, it is less than 64
        // For those of you don't know, 299792458 is the speed of light in vaccum ;)
        int port = node.getSuccessor_port();
        int port_hashed = node.getSuccessor_hashed();
        if(id>=port_hashed)
        { diff = id-port_hashed;}

        for(int i=0;i<6;i++)
        {
            if(node.fingertable[i][2] == id)
            { return node.fingertable[i][1];}
            if(node.fingertable[i][2] >id )
            { continue;}
            int temp_diff = (id-node.fingertable[i][2]);
            if(temp_diff<diff)
            {
                port = node.fingertable[i][1];
                diff = temp_diff;
            }
        }
        return port;
    }

    public int find_appropriate(int id) {
        // If i am the boss
        int pred = node.getPredecessor_hashed();
        int succ = node.getSuccessor_hashed();
        int me = node.getMyself_hashed();

        if (pred > me) {
            pred = 0;
        }
        if (succ < me) {
            succ += 63;
        }

        if ((id > pred) && (id <= me)) {
            return node.getPort();
        } else if ((id > me) && (id <= succ)) {
            return node.getSuccessor_port();
        }

        // Check in finger table and send message to that and return the result
        else {
            // See closest preceeding in fingertabe and forward to it . If no such , send to successor.
            int closest_port =closest_preceeding(id);
            Request request = new Request("find_appropriate", null, id);
            SendMessage message = new SendMessage(request, closest_port);
            Response response = message.send();
            return (response.int_response);
        }
    }
}

