import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.zip.CRC32;

public class ZeckendorfTest {
    
    @Test
    public void massTest() {
        Random rng = new Random(12345);
        BigInteger two = new BigInteger("2");
        CRC32 check = new CRC32(); 
        for(int i = 1; i <= 300; i++) {
            List<BigInteger> orig = new ArrayList<>();
            BigInteger b = new BigInteger("" + (1 + rng.nextInt(i*i)));
            orig.add(b);
            for(int j = 0; j < i; j++) {
                b = b.multiply(two);
                b = b.add(new BigInteger("" + rng.nextInt(100)));
                orig.add(b);
            }
            String zits = Zeckendorf.encode(orig);
            check.update(zits.length());
            for(int k = 0; k < zits.length(); k++) {
                assertTrue(zits.charAt(k) == '0' || zits.charAt(k) == '1');
            }
            List<BigInteger> back = Zeckendorf.decode(zits);
            assertEquals(orig, back);
        }
        assertEquals(278965465L, check.getValue());
    }
}
