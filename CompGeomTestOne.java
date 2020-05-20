import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.zip.CRC32;
import java.util.Random;

public class CompGeomTestOne {

    public static int cross(int x0, int y0, int x1, int y1) {
        return x0 * y1 - x1 * y0;
    }
    
    public static int ccw(int x0, int y0, int x1, int y1, int x2, int y2) {
        return cross(x1 - x0, y1 - y0, x2 - x0, y2 - y0);
    }
    
    @Test public void testSegmentIntersectHundred() {
        testSegmentIntersect(100, 3714949161L, true);
    }
    
    @Test public void testSegmentIntersectMillion() {
        testSegmentIntersect(1_000_000, 1447273406L, false);
    }
    
    private void testSegmentIntersect(int n, long expected, boolean verbose) {
        CRC32 check = new CRC32();
        Random rng = new Random(12345);
        int[] c = new int[8];
        for(int i = 0; i < n; i++) {
            int r = Math.min(3 + i / 3, 10000);
            int rr = r / 2;
            for(int j = 0; j < 8; j++) {
                c[j] = rng.nextInt(r) - rr;
            }
            boolean result = CompGeom.segmentIntersect(
                c[0], c[1], c[2], c[3], c[4], c[5], c[6], c[7]
            );
            if(verbose) {
                System.out.print("(" + c[0] + "," + c[1] + ")-(" + c[2] + "," + c[3] + ") to ");
                System.out.print("(" + c[4] + "," + c[5] + ")-(" + c[6] + "," + c[7] + "): ");
                System.out.println(result);
            }
            check.update(result ? i: 0);
            // Swapping the roles of segments should not affect the result.
            boolean result2 = CompGeom.segmentIntersect(
                c[4], c[5], c[6], c[7], c[0], c[1], c[2], c[3]
            );
            assertEquals(result, result2);
        }
        assertEquals(expected, check.getValue());
    }
    
    @Test public void testLineWithMostPointsFifty() {
        testLineWithMostPoints(50, 1959874018L, false);
    }
    
    @Test public void testLineWithMostPointsThousand() {
        testLineWithMostPoints(1000, 2048170366L, false);
    }

    private void testLineWithMostPoints(int n, long expected, boolean verbose) {
        CRC32 check = new CRC32();
        Random rng = new Random(12345);
        for(int i = 0; i < n; i++) {
            int nn = 3 + (i / 5);
            int[] xs = new int[nn], ys = new int[nn];
            boolean[][] taken = new boolean[nn][nn];
            for(int j = 0; j < nn; j++) {
                int x, y;
                do {
                    x = rng.nextInt(nn);
                    y = rng.nextInt(nn);
                } while(taken[x][y]);
                xs[j] = x - (nn/2); ys[j] = y - (nn/2); taken[x][y] = true;
            }
            int result = CompGeom.lineWithMostPoints(xs, ys);
            if(verbose) {
                System.out.println(Arrays.toString(xs));
                System.out.println(Arrays.toString(ys));
                System.out.println(result);
            }
            check.update(result);
        }
        assertEquals(expected, check.getValue());
    }
    
}
