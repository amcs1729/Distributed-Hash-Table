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

            else if (incoming_request.choice.equalsIgnoreCase("change_predecessor"))
            {
                int previous = node.getPredecessor_hashed();
                int incoming = incoming_request.value;
                int incoming_hashed = hash.hash_string(Integer.toString(incoming));

                if(previous == -1) {
                    node.setPredecessor_port(incoming);
                    node.setPredecessor_hashed(incoming_hashed);
                    response.status = true;

                    if (node.getSuccessor_port() == node.getPort()) {
                        // Special case
                        node.setSuccessor_port(incoming);
                        node.setSuccessor_hashed(incoming_hashed);

                    }

                }

                else
                {
                    int modulo_previous = (int) Math.abs(node.getMyself_hashed() - previous);
                    int modulo_incoming_hashed = (int) Math.abs(node.getMyself_hashed() - incoming_hashed);

                    if(modulo_incoming_hashed < modulo_previous)
                    {
                        node.setPredecessor_port(incoming);
                        node.setPredecessor_hashed(incoming_hashed);
                        response.status = true;
                    }
                }

                //if (previous != -1)
                //{
                //Request request = new Request("change_successor", null , incoming);
                //    SendMessage message = new SendMessage(request , previous_port);
                //    message.send();
                //}
            }

            else if (incoming_request.choice.equalsIgnoreCase("change_successor"))
            {
                int incoming = incoming_request.value;
                int incoming_hashed = hash.hash_string(Integer.toString(incoming));
                node.setSuccessor_port(incoming);
                node.setSuccessor_hashed(incoming_hashed);
                response.status = true;
            }

            else if (incoming_request.choice.equalsIgnoreCase("send_predecessor"))
            {
                response.int_response = node.getPredecessor_port();
                response.status = true;
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
