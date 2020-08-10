import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    public static void main(String args[]) throws IOException
    {
        Hash hash = new Hash();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Port");

        int port = Integer.parseInt(br.readLine());
        int hashed= hash.hash_string(Integer.toString(port));
        System.out.println("Myself hashed = "+hashed);

        Node node = new Node(port,hashed);

        Thread t = new DHTServer(node);
        t.start();

        DHTClient client = new DHTClient(node);
        client.start();
    }
}
