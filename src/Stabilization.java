import java.net.Socket;
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
                request = new Request("find_appropriate",null, node.getMyself_hashed());
                message = new SendMessage(request,node.getKnown_ip());
                response = message.send();
                System.out.println("Changed  successor = "+response.int_response);
                node.setSuccessor_port(response.int_response);
                node.setSuccessor_hashed(hash.hash_string(Integer.toString(response.int_response)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

            if ((hashed_x > node.getMyself_hashed()) && (hashed_x < node.getSuccessor_hashed())) {
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
            stabilize();
            fix_fingers();


            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
