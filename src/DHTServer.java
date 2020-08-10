import java.net.ServerSocket;
import java.net.Socket;

public class DHTServer extends Thread
{
    public Node node;
    DHTServer(Node node)
    {
        this.node = node;
    }
    @Override
    public void run()
    {
        while (true) {
            try {
                int PORT = node.getPort();
                ServerSocket ss = new ServerSocket(PORT);
                System.out.println("Server running\nListening to PORT " + PORT);
                while (true) {
                    Socket connection = ss.accept();
                    Thread t = new RequestHandler(node, connection);
                    t.run();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
