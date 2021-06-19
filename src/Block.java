// source: https://www.baeldung.com/java-blockchain

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Block {
	
	private static Logger logger = Logger.getLogger(Block.class.getName());
	
	private String hash;			// Hash of this block, calculated based on other data
	private String previousHash;	// Hash of the previous block, an important part to build the chain
	private String data;			// The actual data, any information having value, like a contract
	private long timeStamp;			// The timeStamp of the creation of this block
	private int nonce;				// A nonce, which is an arbitrary number used in cryptography
	
	public Block(String data, String previousHash, long timeStamp) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = timeStamp;
		this.hash = calculateBlockHash();
	}
	
	/*
	 * First, we concatenate different parts of the block to generate a hash from
		Then, we get an instance of the SHA-256 hash function from MessageDigest
		Then, we generate the hash value of our input data, which is a byte array
		Finally, we transform the byte array into a hex string, a hash is typically represented as a 32-digit hex number
	 */
	public String calculateBlockHash() {
		String dataToHash = previousHash
				+ Long.toString(timeStamp)
				+ Integer.toString(nonce)
				+ data;
		MessageDigest digest = null;
		byte[] bytes = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
			bytes = digest.digest(dataToHash.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			logger.log(Level.SEVERE, ex.getMessage());
		}
		
		StringBuffer buffer = new StringBuffer();
		for (byte b : bytes) {
			buffer.append(String.format("%02x", b));
		}
		
		return buffer.toString();
	}
	
	/*
	 * We start by defining the prefix we desire to find
		Then we check whether we've found the solution
		If not we increment the nonce and calculate the hash in a loop
		The loop goes on until we hit the jackpot
	 */
	public String mineBlock(int prefix) {
		String prefixString = new String(new char[prefix]).replace('\0', '0');
		while (!hash.substring(0, prefix).equals(prefixString)) {
			nonce++;
			hash = calculateBlockHash();
		}
		return hash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
}
