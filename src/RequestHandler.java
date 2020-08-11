import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread
{
    Socket connection;
    Node node;
    Hash hash;
    Utils utils;

    RequestHandler(Node node, Socket connection)
    {
        this.node = node;
        this.connection = connection;
        this.hash = new Hash();
        this.utils = new Utils(this.node);
    }

    @Override
    public void run()
    {
        try {
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());

            Request incoming_request = (Request) in.readObject();

            //System.out.println("New REQUEST !!");
            //System.out.println(incoming_request.choice);
            //System.out.println(incoming_request.key);
            //System.out.println(incoming_request.value);

            Response response = new Response();


            if(incoming_request.choice.equalsIgnoreCase("heart_beat"))
            {
                response.status = true;
            }

            else if(incoming_request.choice.equalsIgnoreCase("put"))
            {
                node.putvalue(incoming_request.key , incoming_request.value);
                response.status = true;
            }

            else if(incoming_request.choice.equalsIgnoreCase("get"))
            {
                response.status = true;
                response.int_response = node.getvalue(incoming_request.key);

            }
            else if(incoming_request.choice.equalsIgnoreCase("find_appropriate"))
            {
                response.status = true;
                response.int_response = utils.find_appropriate(incoming_request.value);
            }
            else if(incoming_request.choice.equalsIgnoreCase("change_predecessor"))
            {
                boolean status = utils.change_predecessor(incoming_request.value, hash.hash_string(Integer.toString(incoming_request.value)));
                if(status)
                {
                    // Check special Case
                    if(node.getSuccessor_port() == node.getPort())
                    {
                        node.setSuccessor_hashed(hash.hash_string(Integer.toString(incoming_request.value)));
                        node.setSuccessor_port(incoming_request.value);

                        Request request = new Request("change_predecessor" , null , node.getPort());
                        SendMessage message = new SendMessage(request, incoming_request.value);
                        message.send();
                    }

                    response.status = true;
                }
            }

            else if (incoming_request.choice.equalsIgnoreCase("send_predecessor"))
            {
                response.status = true;
                response.int_response = node.getPredecessor_port();
            }

            else
                {
                System.out.println("Oops!! Unknown Request :(");
                System.out.println("WRONG REQUEST    "+incoming_request.choice);
            }
            out.writeObject(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
