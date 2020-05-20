import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.zip.CRC32;
import java.math.*;

public class DistanceTestThree {

    private static final int SEED = 12345;
    private static final int PREC = 30;
    
    @Test public void testApproximate() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        MathContext mc = new MathContext(PREC, RoundingMode.HALF_UP);
        MathContext mc2 = new MathContext(PREC + 2, RoundingMode.HALF_UP);
        BigDecimal epsilon = new BigDecimal(1).scaleByPowerOfTen(PREC);
        for(int i = 1; i < 100; i++) {
            Distance d = Distance.ZERO;
            BigDecimal prev = new BigDecimal(0, mc2);
            for(int j = 0; j*j < i; j++) {
                int whole = rng.nextInt(i + 3) + 1;
                if(rng.nextBoolean()) { whole = -whole; }
                int base = rng.nextInt(10 * (i + 3)) + 2;
                Distance dd = new Distance(whole, base);
                //System.out.println("Adding " + dd);
                d = d.add(dd);
                
                BigDecimal curr = d.approximate(mc2);
                //System.out.println(d + " is approximately " + curr);
                check.update(curr.toString().getBytes());
                BigDecimal dc = dd.approximate(mc2);
                BigDecimal diff = curr.subtract(prev);

                assertTrue(diff.abs().compareTo(epsilon) < 0);
                prev = curr;
            }
        }
    }  
    
    @Test public void testCompareTo() {
        MathContext mc = new MathContext(10);
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        int N = 100;
        Distance[] ds = new Distance[3 * N];
        for(int i = 0; i < ds.length; i++) {
            if(i < N) {
                int whole = rng.nextInt(i + 3);
                if(rng.nextBoolean()) { whole = -whole; }
                int base = rng.nextInt(10 * (i + 3)) + 1;
                ds[i] = new Distance(whole, base);
            }
            else {
                int j1 = rng.nextInt(i);
                int j2 = rng.nextInt(i);
                ds[i] = ds[j1].add(ds[j2]);
            }
        }
        for(int i = 0; i < N; i++) {
            for(int j = i + 1; j < N; j++) {
                int comp = ds[i].compareTo(ds[j]);
                comp = (comp > 0) ? +1 : ((comp < 0) ? -1 : 0);
                check.update(comp);
            }
        }
        assertEquals(1405771108L, check.getValue());
    }
}