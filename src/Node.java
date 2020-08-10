import java.util.concurrent.ConcurrentHashMap;

public class Node
{
    private int port;
    private int successor_port;
    private int myself_hashed;
    private int successor_hashed;
    private int predecessor_port;
    private int predecessor_hashed;

    private ConcurrentHashMap<String , Integer> map;

    Node(int port, int myself_hashed)
    {
        this.port = port;
        this.myself_hashed = myself_hashed;
        this.predecessor_hashed = -1;
        this.predecessor_port = -1;
        this.successor_hashed = myself_hashed;
        this.successor_port = port;
        map = new ConcurrentHashMap<String, Integer>();
    }

    public int getPort() {
        return port;
    }

    public int getSuccessor_port() {
        return successor_port;
    }

    public void setSuccessor_port(int successor_port) {
        this.successor_port = successor_port;
    }

    public int getMyself_hashed() {
        return myself_hashed;
    }

    public int getSuccessor_hashed() {
        return successor_hashed;
    }

    public void setSuccessor_hashed(int successor_hashed) {
        this.successor_hashed = successor_hashed;
    }
    public void putvalue(String key, int value)
    {
        try {
            map.put(key, value);
            System.out.println("SET VALUE "+ value +" FOR KEY  ->   "+key+"   @ port  "+port);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public int getvalue(String key)
    {
        try {
            System.out.println("Returning value for key  ->  "+key+"   from port    "+port);
            return(map.get(key));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public int getPredecessor_port() {
        return predecessor_port;
    }

    public void setPredecessor_port(int predecessor_port) {
        this.predecessor_port = predecessor_port;
    }

    public int getPredecessor_hashed() {
        return predecessor_hashed;
    }

    public void setPredecessor_hashed(int predecessor_hashed) {
        this.predecessor_hashed = predecessor_hashed;
    }
}
