
import com.company.blockchain.Block;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlockchainUnitTest {

    public static List<Block> blockchain = new ArrayList<>();
    public static int prefix = 4;
    public static String prefixString;

    @BeforeAll
    public static void setUp() {
        prefixString = new String(new char[prefix]).replace('\0', '0');

        Block genesisBlock = new Block("The is the Genesis Block.", "0", new Date().getTime());
        genesisBlock.mineBlock(prefix);
        blockchain.add(genesisBlock);
        
        Block firstBlock = new Block("The is the First Block.", genesisBlock.getHash(), new Date().getTime());
        firstBlock.mineBlock(prefix);
        blockchain.add(firstBlock);
    }

    @Test
    public void addNewBlock() {
        Block newBlock = new Block("The is a New Block.", blockchain.get(blockchain.size() - 1).getHash(), new Date().getTime());
        newBlock.mineBlock(prefix);
        
        assertTrue(newBlock.getHash().substring(0, prefix).equals(prefixString));
        
        blockchain.add(newBlock);
    }

    @Test
    public void givenBlockchain_whenValidated_thenSuccess() {
        boolean flag = true;
        
        for (int i = 0; i < blockchain.size(); i++) {
            String previousHash = i == 0 ? "0" : blockchain.get(i - 1).getHash();
            flag = blockchain.get(i).getHash().equals(blockchain.get(i).calculateBlockHash()) &&
                    previousHash.equals(blockchain.get(i).getPreviousHash()) &&
                    blockchain.get(i).getHash().substring(0, prefix).equals(prefixString);
            if (!flag)
                break;
        }
        
        assertTrue(flag);
    }

    @AfterAll
    public static void tearDown() {
        blockchain.clear();
    }

}
