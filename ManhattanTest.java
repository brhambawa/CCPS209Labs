import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.zip.CRC32;

public class ManhattanTest {

    @Test public void testTotalArea() {
        CRC32 check = new CRC32();
        //Random rng = new Random(12345);
        Random rng = new Random(777);
        for(int i = 2; i < 500; i++) {
            int n = rng.nextInt(3 * i) + 1;
            int[] s = new int[n];
            int[] e = new int[n];
            int[] h = new int[n];
            for(int j = 0; j < n; j++) {
                s[j] = rng.nextInt(4 * n);
                
            }
            Arrays.sort(s);
            for(int j = 0; j < n; j++) {
                int w = 1 + rng.nextInt(2 * n);
                e[j] = s[j] + w;
                if(j > 0 && s[j-1] == s[j]) { e[j] = Math.max(e[j], e[j-1] + 1); }
                h[j] = 1 + (j / w) + rng.nextInt(3);    
            }
            //System.out.println("s: " + Arrays.toString(s));
            //System.out.println("e: " + Arrays.toString(e));
            //System.out.println("h: " + Arrays.toString(h));
            int r = Manhattan.totalArea(s, e, h);
            check.update(r);
            //System.out.println("Result: " + r + "\n");
        }
        assertEquals(2174298203L, check.getValue());
    }
    
}
