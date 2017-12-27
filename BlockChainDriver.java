import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class BlockChainDriver {
    
    //Creates the blockchain and then lets the user interact until the user enters quit
    public static void driver(int initAmount) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        BlockChain chain = new BlockChain(initAmount);
        boolean loop = true;
        while (loop) {
            System.out.println(chain.toString());
            loop = readCommand(scanner, chain);
            System.out.print("\n");
        }
        scanner.close();
    }

    //Scans users input and executes the appropriate set of commands 
    public static boolean readCommand(Scanner scanner, BlockChain chain) throws NoSuchAlgorithmException {
        System.out.print("Command? ");
        String command = scanner.next();
        if (command.equals("mine")) {   
            System.out.print("Amount transferred? ");
            int amount = Integer.parseInt(scanner.next());
            Block block = chain.mine(amount);
            System.out.println("amount = " + amount + ", nonce = " + block.getNonce());
        } else if (command.equals("append")) {
            System.out.print("Amount transferred? ");
            int amount = Integer.parseInt(scanner.next());
            System.out.print("Nonce? ");
            long nonce = Long.parseLong(scanner.next());
            Block block = new Block(chain.getSize(), amount, chain.getHash(), nonce);
            chain.append(block);
        } else if (command.equals("remove")) {
            chain.removeLast();
        } else if (command.equals("check")) {
            if(chain.isValidBlockChain()){
                System.out.println("Chain is valid!");
            } else{
                System.out.println("Chain is invalid!");
            }
        } else if (command.equals("report")) {
            chain.printBalances();
        } else if (command.equals("help")) { //Prints all valid commands inside the program
            System.out.print("Valid commands:\n    mine: discovers the nonce for a given transaction\n    append: appends a new block onto the end of the chain\n    remove: removes the last block from the end of the chain\n    check: checks that the block chain is valid\n    report: reports the balances of Alice and Bob\n    help: prints this list of commands\n    quit: quits the program\n");
        } else if (command.equals("quit")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws NumberFormatException, NoSuchAlgorithmException {
        driver(Integer.parseInt(args[0]));
    }

}
