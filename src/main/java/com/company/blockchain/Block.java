package com.company.blockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Block {

    private static final Logger logger = Logger.getLogger(Block.class.getName());

    private String hash;
    private final String previousHash;
    private String data;
    private final long timeStamp;
    private int nonce;

    public Block(String data, String previousHash, long timeStamp) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = calculateBlockHash();
    }

    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
       
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash();
        }
        
        logger.log(Level.INFO, String.valueOf(nonce));
        return hash;
    }
    
    public String calculateBlockHash() {
        String dataToHash = data + previousHash + Long.toString(timeStamp) + Integer.toString(nonce);
        MessageDigest digest = null;
        byte[] bytes = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes)
            buffer.append(String.format("%02x", b));

        return buffer.toString();
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public void setData(String data) {
        this.data = data;
    }

}
