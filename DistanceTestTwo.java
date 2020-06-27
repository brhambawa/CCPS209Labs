import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.zip.CRC32;

public class DistanceTestTwo {

    public static final Distance ZERO = new Distance(0, 1);
    
    private static final int SEED = 123456;
    private void randomPoints(int[] out, int n, Random rng) {
        for(int j = 0; j < 4; j++) {
            int v = rng.nextInt(n);
            if(rng.nextBoolean()) { v = -v; }
            out[j] = v;
        }
    }

    @Test public void testAdd() {
        testArithmetic(true, 836768866L);
    }
    
    @Test public void testSubtract() {
        testArithmetic(false, 4281111779L);
    }
    
    private void testArithmetic(boolean add, long expected) {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        int N = 100_000;
        int[] c = new int[4];
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
                ds[i] = add ? ds[j1].add(ds[j2]) : ds[j1].subtract(ds[j2]);
                String verb = add ? "added" : "subtracted";
                Distance sub = ds[i].subtract(ds[i]);
                assertEquals(ZERO.toString(), sub.toString());
                if(i > 0) { assertEquals(ds[i].toString(), ds[i].add(ds[i-1]).subtract(ds[i-1]).toString()); }
                check.update(ds[i].toString().getBytes());
            }
        }
        assertEquals(expected, check.getValue());
    }

    @Test public void testMultiply() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        int N = 100;
        int[] c = new int[4];
        Distance[] ds = new Distance[3 * N];
        for(int i = 0; i < 3 * N; i++) {
            if(i < N) { // create some random distances
                int whole = rng.nextInt(i + 3);
                if(rng.nextBoolean()) { whole = -whole; }
                int base = rng.nextInt(10 * (i + 3)) + 1;
                ds[i] = new Distance(whole, base);
            }
            else if(i < 2 * N) { // some more complex distances by adding things together
                int j1 = rng.nextInt(i);
                int j2 = rng.nextInt(i);
                ds[i] = ds[j1].add(ds[j2]);
            }
            else { // multiply random distances from part two
                int j1 = rng.nextInt(N) + N;
                int j2 = rng.nextInt(N) + N;
                ds[i] = ds[j1].multiply(ds[j2]);
                // System.out.println("");
                // System.out.println(pts[j1]);
                // System.out.println(pts[j2]);
                // System.out.println(pts[i]);
            }
            check.update(ds[i].toString().getBytes());
        }
        assertEquals(2108081313L, check.getValue());
    }
    
    
}
