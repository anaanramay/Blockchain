import java.util.Arrays;


public class Hash {

    private byte[] data;

    public Hash(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }
    
    //Checks if hash is valid. That is when the first 3 bytes are 0
    public boolean isValid() {
        return data[0] == 0 && data[1] == 0 && data[2] == 0;
    }
    
    //Returns a string representation of the hash
    public String toString() {
        String str = "";
        for (int i = 0; i < data.length; i++) {
            int num = Byte.toUnsignedInt(data[i]);
            str += String.format("%x", num);
        }
        return str;
    }
    
    //Checks if two hashes are equal
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            return Arrays.equals(data, o.data);
        }
        return false;
    }

}
