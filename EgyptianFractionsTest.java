import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;
import java.math.BigInteger;

public class EgyptianFractionsTest
{
    
    private static boolean addsUp(List<BigInteger> egyptian, Fraction f) {
        Fraction sum = new Fraction(BigInteger.ZERO);
        for(BigInteger n: egyptian) {
            sum = sum.add(new Fraction(BigInteger.ONE, n));
        }
        return sum.equals(f);
    }
    
    @Test public void testGreedy() {
        CRC32 check = new CRC32();
        Random rng = new Random(444);
        for(int i = 0; i < 400; i++) {
            int a, b;
            do {
                b = rng.nextInt(10 * i + 1) + 6;
                a = rng.nextInt(b - 1) + 1;
            } while(a % 2 == 0 && b % 2 == 0);
            Fraction apb = new Fraction(a, b);
            List<BigInteger> gRes = EgyptianFractions.greedy(apb);
            check.update(gRes.toString().getBytes());
            //System.out.println(a + "/" + b + " greedy : " + gRes);
            assertTrue(addsUp(gRes, apb));
        }
        assertEquals(93321355L, check.getValue());
    }
    
    @Test public void testSplitting() {
        CRC32 check = new CRC32();
        Random rng = new Random(444);
        for(int i = 0; i < 400; i++) {
            int a, b;
            do {
                b = rng.nextInt(10 * i + 1) + 6;
                a = rng.nextInt(b - 1) + 1;
            } while(a % 2 == 0 && b % 2 == 0);
            Fraction apb = new Fraction(a, b);
            List<BigInteger> gRes = EgyptianFractions.splitting(apb);
            check.update(gRes.toString().getBytes());
            //System.out.println(a + "/" + b + " splitting : " + gRes);
            assertTrue(addsUp(gRes, apb));
        }
        assertEquals(2886553470L, check.getValue());
    }

}
