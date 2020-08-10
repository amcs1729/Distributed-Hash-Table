import java.io.Serializable;

public class Request implements Serializable {
    public int value;
    public String key;
    public String choice;

    Request(String choice, String key, int value) {
        this.choice = choice;
        this.key = key;
        this.value = value;
    }
}