package practice_7.Encryption;


/**
 * Created by swanta on 10.07.16.
 */
public class Enigma {
    private final byte FLAG_ENCRYPTED = 13;
    private final View view;
    private EncryptKey key;

    public Enigma(View view) {
        this.view = view;
        key = new EncryptKey();

    }

    protected String decryptBytes(byte[] buffer) {
        if (buffer.length > 0) {
            boolean isEncrypted = buffer[0] == FLAG_ENCRYPTED;
            if (isEncrypted) {
                setCode();
                buffer = encrypt(buffer);
            }
        }
        return new String(buffer);
    }

    protected byte[] encryptText(String text) {
        setCode();
        byte[] encryptedBuffer = encrypt(text.getBytes());
        int bufferLength = encryptedBuffer.length+1;
        byte[] result = new byte[bufferLength];
        result[0] = FLAG_ENCRYPTED;
        for (int i = 1; i < bufferLength; i++) {
            result[i] = encryptedBuffer[i - 1];
        }
        return result;

    }

    private byte[] encrypt(byte[] buffer) {
        byte[] encryptedBuffer = new byte[buffer.length];
        for (int i = 1; i < buffer.length; i++) {
            byte keyB = key.getNext();
            encryptedBuffer[i] = (byte) (buffer[i] ^ keyB);
            view.println(i +" : " +
                    (char)buffer[i] + "(" + buffer[i] + ") -> " +
                    (char)keyB + "(" +keyB+ ") -> " +
                    (char)encryptedBuffer[i]+ "("+encryptedBuffer[i]+")");
        }
//        view.println("buffer = " + new String(buffer));
//        view.println("key = " + key.getKey());
//        view.println("encryptedBuffer = " + new String(encryptedBuffer.toString()));
        return encryptedBuffer;
    }
    private boolean setCode() {
        String code;
        do {
            code = view.askUser("Enter password:");
        } while (code.isEmpty() && !view.askUserDefaultAnswer("Your message will be *UN*encrypted while code is empty." +
                "Proceed? n/", View.ANSWER_YES));
        if (code.isEmpty()){
            return false;
        }
        key.setCode(code);
        return true;
    }
}
