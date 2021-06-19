import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class main {

	List<Block> blockchain = new ArrayList<>();		// store blockchain in an ArrayList
	static int prefix = 4;
	String prefixString = new String(new char[prefix]).replace('\0', '0');

	public static void main(String[] args) {
		
	}

	/*
	 * Add a block to the blockchain
	 */
	public void givenBlockchain_whenNewBlockAdded_thenSucess() {
		Block newBlock = new Block(
			"This is a New Block.",
			blockchain.get(blockchain.size()-1).getHash(),
			new Date().getTime());

		newBlock.mineBlock(prefix);
		
		assertTrue(newBlock.getHash().substring(0,prefix)
				.equals(prefixString));
		
		blockchain.add(newBlock);
	}
	
	/*
	 * Blockchain Verification: How can a node validate that a blockchain is valid?
	 * Here we're making three specific checks for every block:
		- The stored hash of the current block is actually what it calculates
		- The hash of the previous block stored in the current block is the hash of the previous block
		- The current block has been mined
	 */
	public void givenBlockchain_whenValidated_thenSuccess() {
		boolean flag = true;
		
		for (int i = 0; i < blockchain.size(); i++) {
			String previousHash = i==0 ? "0" : blockchain.get(i-1).getHash();
			flag = blockchain.get(i).getHash().equals(blockchain.get(i).calculateBlockHash())
					&& previousHash.equals(blockchain.get(i).getPreviousHash())
					&& blockchain.get(i).getHash().substring(0,prefix).equals(prefixString);
			
			if (!flag) break;
		}
		
		assertTrue(flag);
	}
}
