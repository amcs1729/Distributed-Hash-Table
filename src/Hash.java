import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hash {
    public int hash_string(String value) {

        int hash = 7;
        for (int i = 0; i < value.length(); i++) {
            hash = hash * 31 + value.charAt(i);
        }
        hash %= 64;

        return hash;
    }
}