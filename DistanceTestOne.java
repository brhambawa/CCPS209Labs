import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.zip.CRC32;

public class DistanceTestOne {

    private static final int SEED = 123456;

    @Test public void testExtractSquares() {
        CRC32 check = new CRC32();
        for(int n = 0; n < 1_000_000; n++) {
            int sp = Distance.extractSquares(n);
            assertEquals(0, n % (sp*sp));
            int a = n / (sp*sp); // Integer division truncates
            assertEquals(n, sp*sp*a);
            check.update(sp);
        }
        assertEquals(2907073306L, check.getValue());
    }

    @Test public void testConstruction() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        int[] c = new int[4];
        for(int i = 0; i < 10_000; i++) {
            int whole = rng.nextInt(3 * (i + 2));
            if(rng.nextBoolean()) { whole = -whole; }
            int base = rng.nextInt(3 * (i + 2)) + 1;
            Distance d = new Distance(whole, base);
            String rep = d.toString();
            // if(i % 50 == 0 && i < 1000) { 
                // System.out.println(whole + " " + base + " : <" + rep + ">"); 
            // }
            check.update(rep.getBytes());
        }
        assertEquals(4065287689L, check.getValue());
    }    
}