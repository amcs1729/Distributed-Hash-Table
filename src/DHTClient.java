import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DHTClient
{
    Node node;
    Utils utils;
    Hash hash;
    DHTClient(Node node)
    {
        this.node = node;
        this.utils = new Utils(this.node);
        this.hash = new Hash();
    }
    public  void start() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Join or Create ?");
        String choice_1 = br.readLine();

        if(choice_1.equalsIgnoreCase("join"))
        {
            System.out.println("Enter known PORT");
            int known_port = Integer.parseInt(br.readLine());
            Request request = new Request("find_appropriate",null, node.getMyself_hashed());
            SendMessage message = new SendMessage(request,known_port);
            Response response = message.send();

            System.out.println("My successor = "+response.int_response);
            node.setSuccessor_port(response.int_response);
            node.setSuccessor_hashed(hash.hash_string(Integer.toString(response.int_response)));

            request = new Request("change_predecessor",null,node.getPort());
            message = new SendMessage(request, node.getSuccessor_port());
            message.send();
        }


            while(true) {
            System.out.println("Insert 1\nGet 2");
            int choice = Integer.parseInt(br.readLine());

            if (choice == 1) {
                System.out.println("Enter key");
                String key = br.readLine();
                System.out.println("Enter value");
                int value = Integer.parseInt(br.readLine());

                int port = utils.find_appropriate(hash.hash_string(key));

                Request request = new Request("put", key, value);

                SendMessage message = new SendMessage(request, port);
                Response response = message.send();

                if(response.status)
                {
                    System.out.println("Operation successful");
                }
                else
                {
                    System.out.println("Oops error ....Could not put entry");
                }

            } else if (choice == 2) {
                System.out.println("Enter key");
                String key = br.readLine();

                int port = utils.find_appropriate(hash.hash_string(key));

                Request request = new Request("get", key, -1);

                SendMessage message = new SendMessage(request, port);
                Response response = message.send();

                System.out.println(response.int_response);
            } else {
                System.out.println("Wrong choice");
            }
        }

    }
}
