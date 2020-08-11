import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessage
{
    Request request;
    int port;
    SendMessage(Request request,int port)
    {
        this.request = request;
        this.port = port;
    }

    public Response send() {
        Response response = new Response();
        try {
            //System.out.println("trying to send msg to "+port);
            Socket s = new Socket("localhost",port);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            out.writeObject(request);

            response = (Response) in.readObject();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (response);
    }
}