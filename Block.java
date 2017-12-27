import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Block {

    private int num;
    private int amount;
    private Hash prevHash;
    private long nonce;
    private Hash hash;

    //Creates a new block and compute the hash without mining
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.hash = calculateHash(num, amount, prevHash, nonce);
    }
    //Creates a new block and performs mining to compute hash
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        nonce = 0;
        hash = calculateHash(num, amount, prevHash, nonce);
        while (!hash.isValid()) {
            nonce++;
            hash = calculateHash(num, amount, prevHash, nonce);
        }
    }

    public int getNum() {
        return num;
    }

    public int getAmount() {
        return amount;
    }

    public long getNonce() {
        return nonce;
    }

    public Hash getPrevHash() {
        return prevHash;
    }

    public Hash getHash() {
        return hash;
    }

    public String toString() {
        return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)", num, amount, nonce, prevHash, hash);
    }

    //Calculates the hash using sha-256 
    static Hash calculateHash(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(ByteBuffer.allocate(4).putInt(num).array());
        md.update(ByteBuffer.allocate(4).putInt(amount).array());
        if (prevHash != null) {
            md.update(prevHash.getData());
        }
        md.update(ByteBuffer.allocate(8).putLong(nonce).array());
        return new Hash(md.digest());
    }
}
