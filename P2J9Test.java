import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

public class P2J9Test {

    private void printTrues(boolean[] a) {
        for(int i = 0; i < a.length; i++) {
            if(a[i]) { System.out.print(i + " "); }
        }
        System.out.println("");
    }
    
    @Test public void testSubtractSquareThousand() {
        test(1000, 4122798422L, 0);
    }
    
    @Test public void testSubtractSquareMillion() {
        test(1_000_000, 1504185187L, 0);
    }
    
    @Test public void testSubtractSquareTenMillion() {
        test(10_000_000, 3315207453L, 0);
    }
    
    @Test public void testSumOfTwoDistinctSquaresThousand() {
        test(1000, 4110419952L, 1);
    }
    
    @Test public void testSumOfTwoDistinctSquaresMillion() {
        test(1_000_000, 2362619161L, 1);
    }
    
    private void test(int n, long expected, int mode) {
        CRC32 check = new CRC32();
        boolean[] result;
        if(mode == 0) { result = P2J9.subtractSquare(n); }
        else { result = P2J9.sumOfTwoDistinctSquares(n); }
        //printTrues(result);
        for(int i = 0; i < n; i++) {
            check.update(result[i] ? i : 0);
        }
        assertEquals(expected, check.getValue());
    }
}
