import java.util.concurrent.ConcurrentHashMap;

public class Node
{
    private int port;
    private int successor_port;
    private int myself_hashed;
    private int successor_hashed;
    private int predecessor_port;
    private int predecessor_hashed;
    private int known_ip;
    public int fingertable[][];
    private ConcurrentHashMap<String , Integer> map;

    Node(int port, int myself_hashed)
    {
        this.port = port;
        this.myself_hashed = myself_hashed;
        this.predecessor_hashed = -1;
        this.predecessor_port = -1;
        this.successor_hashed = myself_hashed;
        this.successor_port = port;
        this.known_ip = -1;
        this.fingertable = new int[6][3];
        map = new ConcurrentHashMap<String, Integer>();
    }
    public void initialize_fingers()
    {
        for (int i=0;i<6;i++)
        {
            int to_find = (myself_hashed+ (int) Math.pow(2,i)) % 64 ;
            fingertable[i][0] = (to_find);
            fingertable[i][1] = port;
            fingertable[i][2] = myself_hashed;
        }
    }

    public int getPort() {
        return port;
    }

    public int getSuccessor_port() {
        return successor_port;
    }

    public void setSuccessor_port(int successor_port) {
        this.successor_port = successor_port;
        System.out.println("Successor PORT changed to .....  "+successor_port);
    }

    public int getMyself_hashed() {
        return myself_hashed;
    }

    public int getSuccessor_hashed() {
        return successor_hashed;
    }

    public void setSuccessor_hashed(int successor_hashed) {
        this.successor_hashed = successor_hashed;
        System.out.println("Successor Hashed changed to .....  "+successor_hashed);
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
        System.out.println("Predecessor PORT changed to .....  "+predecessor_port);
    }

    public int getPredecessor_hashed() {
        return predecessor_hashed;
    }

    public void setPredecessor_hashed(int predecessor_hashed) {
        this.predecessor_hashed = predecessor_hashed;
        System.out.println("Predecessor Hashed changed to .....  "+predecessor_hashed);

    }

    public void show_finger_table()
    {
        System.out.println("\t\t\tFinger Table");
        System.out.println("|\t\tENTRY \t\t FOR \t\t SUCCESSOR\t\t PORT|");
        for(int i=0;i< fingertable.length;i++)
        {
            System.out.println("|\t\t("+i+")\t\t\t\t"+fingertable[i][0]+"\t\t\t"+fingertable[i][2]+"\t\t\t"+fingertable[i][1]+"|");
            System.out.println("|____________________________________________________|");

        }
    }

    public void get_configurations()
    {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("PORT .... "+ port);
        System.out.println("MYSELF HASHED .... "+myself_hashed);
        System.out.println("SUCCESSOR PORT  .... "+successor_port);
        System.out.println("SUCCESSOR HASHED  .... "+ successor_hashed);
        System.out.println("PREDECESSOR PORT  ....  "+ predecessor_port);
        System.out.println("PREDECESSOR HASHED ....  "+ predecessor_hashed);
        show_finger_table();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$#");
    }

    public int getKnown_ip() {
        return known_ip;
    }

    public void setKnown_ip(int known_ip) {
        this.known_ip = known_ip;
    }
}
