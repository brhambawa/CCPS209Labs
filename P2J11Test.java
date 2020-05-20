import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.CRC32;
import java.util.Random;

public class P2J11Test {

    @Test public void testBounceHundred() {
        testBounce(100, 2606770538L, false);
    }
    
    @Test public void testBounceTenThousand() {
        testBounce(10000, 442411979L, false);
    }
    
    private void testBounce(int n, long expected, boolean verbose) {
        CRC32 check = new CRC32();
        Random rng = new Random(12345);
        for(int i = 0; i < n; i++) {
            int w = rng.nextInt(3*i + 3) + 3;
            int h = rng.nextInt(3*i + 3) + 3;
            int x = rng.nextInt(w - 2) + 1;
            int y = rng.nextInt(h - 2) + 1;
            int dx = rng.nextInt(10 * w + 1) - 5 * w;
            int dy = rng.nextInt(10 * h + 1) - 5 * h;
            int t = 0;
            for(int j = 0; j < 1 + i / 10; j++) {
                t += 2 * t + rng.nextInt(10) + 1;
                int rx = P2J11.getBounceX(w, h, x, y, dx, dy, t);
                int ry = P2J11.getBounceY(w, h, x, y, dx, dy, t);
                check.update(rx);
                check.update(ry);
                if(verbose) {
                    System.out.printf("%d %d %d %d %d %d %d %d %d\n", w, h, x, y, dx, dy, t, rx, ry);
                }
            }
        }
        assertEquals(expected, check.getValue());
    }
    
    private static final String[] good = {"onion", "celery", "beans"};
    
    @Test public void testContainsBroccoliHundred() {
        testContainsBroccoli(100);
    }
    
    @Test public void testContainsBroccoliTenThousand() {
        testContainsBroccoli(10_000);
    }
    
    private void testContainsBroccoli(int n) {
        Random rng = new Random(12345);
        for(int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            // The initial ingredients.
            int m = rng.nextInt(i + 3) + 1;
            for(int j = 0; j < m; j++) {
                String veg = good[rng.nextInt(3)];
                //System.out.print(veg);
                sb.append(veg);
            }
            // Add some broccoli by random coin flips.
            boolean broccoli = rng.nextBoolean();
            if(broccoli) { 
                sb.append("broccoli"); 
                //System.out.print("broccoli");
            }
            // Chop the salad as fine as we can.
            for(int j = 0; j < sb.length(); j++) {
                int k = rng.nextInt(sb.length() - j) + j;
                char tmp = sb.charAt(j);
                sb.setCharAt(j, sb.charAt(k));
                sb.setCharAt(k, tmp);
            }
            // Does the method correctly recognize the broccoli?
            boolean result = P2J11.containsBroccoli(sb.toString());
            //System.out.println("\n" + sb.toString() + " " + broccoli + " " + result);
            assertEquals(broccoli, result);
        }
    }   
}