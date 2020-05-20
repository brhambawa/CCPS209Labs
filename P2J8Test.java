import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;
import java.math.BigInteger;

public class P2J8Test {
    
    @Test public void testHittingIntegerPowers() {
        CRC32 check = new CRC32();
        int[] out = new int[2];
        int[] tens = { 1, 10, 100, 10000, 10000, 100000 };
        for(int b = 3; b < 20; b++) {
            for(int a = 2; a < b; a++) {
                int t = 2 + (a + b) % 3;
                P2J8.hittingIntegerPowers(a, b, tens[t], out);
                //System.out.println(a + " " + b + " " + t + " " + Arrays.toString(out));
                check.update(out[0]);
                check.update(out[1]);
            }
        }
        assertEquals(3805180419L, check.getValue());
    }
    
    private static final BigInteger TWO = new BigInteger("2");
    @Test public void testNearestPolygonalNumber() {
        CRC32 check = new CRC32();
        Random rng = new Random(12345);
        BigInteger curr = new BigInteger("1");
        int[] tens = { 1, 10, 100, 1000, 10000 };
        int scale = 2;
        for(int i = 0; i < 1000; i++) {
            if(i % 5 == 0) { curr = curr.multiply(TWO); }
            int s = rng.nextInt(100) + 2;
            BigInteger result = P2J8.nearestPolygonalNumber(curr, s);
            //System.out.println(curr + " " + s + " : " + result);
            check.update(result.toString().getBytes());
            curr = curr.add(new BigInteger("" + rng.nextInt(100) * tens[(i/5) % tens.length]));
        }
        assertEquals(3138704967L, check.getValue());
    }
}
