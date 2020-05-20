import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.CRC32;
import java.util.Random;

public class ClumpsTest {
    
    @Test public void testClumps() {
        CRC32 check = new CRC32();
        Random rng = new Random(12345);
        int n = 10_000, trials = 1000;
        
        for(int i = 0; i < trials; i++) {
            Clumps c = new Clumps(n);
            // Keep melding until everything has become one clump.
            while(c.clumpSize(0) < n) {
                int a = rng.nextInt(n);
                int b = rng.nextInt(n);
                int as = c.clumpSize(a);
                int bs = c.clumpSize(b);
                check.update(as);
                check.update(bs);
                if(c.sameClump(a, b)) {
                    assertEquals(as, bs);
                }
                else {
                    assertTrue(c.meld(a, b));
                    assertTrue(c.sameClump(a, b));
                    assertEquals(as + bs, c.clumpSize(a));
                    assertEquals(as + bs, c.clumpSize(b));
                }
            }
        }
        assertEquals(3107366522L, check.getValue());
    }
    
}
