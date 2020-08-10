import java.io.Serializable;

public class Response implements Serializable {
    public boolean status;
    public int int_response;
    Response() {
        this.status = false;
        this.int_response = 0;
    }
}