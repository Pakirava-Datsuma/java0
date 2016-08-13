package practice_7.Encryption;

/**
 * Created by swanta on 10.07.16.
 */
public class EncryptKey {
    private String code;
    private int current;

    protected EncryptKey() {
    }

    protected EncryptKey(String code) {
        this();
        setCode(code);
    }

    protected byte getNext() {
        byte b = code.getBytes()[current];
        if (current >= code.length()-1){
            current = 0;
        } else {
            current++;
        }
        return b;
    }

    protected boolean isEmpty() {
        return code.isEmpty();
    }

    public void setCode(String code) {
        this.code = code;
        current = code.isEmpty() ? -1 : 0; // getNext() will falls if code is empty
    }

    public String getKey() {
        return code;
    }
}

