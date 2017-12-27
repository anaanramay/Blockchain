import java.security.NoSuchAlgorithmException;


public class BlockChain {

    private class Node {
        Block value;
        Node next;

        Node(Block value) {
            this.value = value;
        }
    }

    private Node first;
    private Node last;
    
    //Creates new blockchain with a single block
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        first = new Node(new Block(0, initial, null));
        last = first;
    }

    //Mines a new block that can be appended to the end of the list
    public Block mine(int amount) throws NoSuchAlgorithmException {
        int num = getSize();
        Hash prevHash = getHash();
        return new Block(num, amount, prevHash);
    }
    
    //Gets the size of the chain
    public int getSize() {
        return last.value.getNum() + 1;
    }

    //Gets the value of the hash of the last block    
    public Hash getHash() {
        return last.value.getHash();
    }

    //Adds a new block to the end of the list
    public void append(Block block) throws IllegalArgumentException {
        if (block.getPrevHash().equals(last.value.getHash())) {
            last.next = new Node(block);
            last = last.next;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    //Removes the last block of the list
    public boolean removeLast() {
        if (first == last) {
            return false;
        } else {
            Node secondLast = first;
            while (secondLast.next != last) {
                secondLast = secondLast.next;
            }
            secondLast.next = null;
            last = secondLast;
            return true;
        }
    }
    
    //Checks and returns true if the blockchain is valid
    public boolean isValidBlockChain() {
        int netAmount = first.value.getAmount();
        if (netAmount < 0 || !first.value.getHash().isValid()) {
            return false;
        }
        Node prev = first;
        Node curr = first.next;
        while (curr != null) {
            netAmount += curr.value.getAmount();
            if (netAmount < 0 || !curr.value.getHash().isValid() || !prev.value.getHash().equals(curr.value.getPrevHash())) {
                return false;
            }
            prev = prev.next;
            curr = curr.next;
        }
        return true;
    }

    //Prints Alice's and Bob's Balances
    public void printBalances() {
        int aliceAmount = first.value.getAmount();
        Node curr = first.next;
        while (curr != null) {
            aliceAmount += curr.value.getAmount();
            curr = curr.next;
        }
        int bobAmount = first.value.getAmount() - aliceAmount;
        System.out.println(String.format("Alice: %d, Bob: %d", aliceAmount, bobAmount));
    }

    //Returns a string representation of the blockchain
    public String toString() {
        String str = first.value.toString();
        Node curr = first.next;
        while (curr != null) {
            str += "\n" + curr.value.toString();
            curr = curr.next;
        }
        return str;
    }
}
