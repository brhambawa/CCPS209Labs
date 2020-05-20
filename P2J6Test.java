import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

public class P2J6Test {

    private static final int SEED = 12345;
    
    @Test public void testSumOfDistinctCubes() {
        CRC32 check = new CRC32();
        Random rng = new Random(SEED);
        int c = 1, step = 2, next = 10;
        while(c > 0) {
            List<Integer> result = P2J6.sumOfDistinctCubes(c);
            check.update(result.toString().getBytes());
            c += rng.nextInt(step) + 1;
            if(c > next) { 
                next = 2 * next;
                step = 2 * step;
            }
        }
        assertEquals(4219145223L, check.getValue());
    }
    
    private String createString(String alphabet, Random rng, int n) {
        String result = "";
        for(int i = 0; i < n; i++) {
            result += alphabet.charAt(rng.nextInt(alphabet.length()));
        }
        return result;
    }
    
    @Test public void testForbiddenSubstrings() {
        CRC32 check = new CRC32();
        Random rng = new Random(SEED);
        String alphabet = "ABCDEF";
        List<String> tabu = new ArrayList<>();
        for(int i = 0; i < 500; i++) {
            int an = Math.max(2, rng.nextInt(alphabet.length()));
            String alpha = alphabet.substring(0, an);
            tabu.clear();
            int tn = rng.nextInt(10);
            for(int j = 0; j < tn; j++) {
                tabu.add(createString(alpha, rng, rng.nextInt(4) + 2));
            }
            int n = rng.nextInt(7) + 2;
            List<String> result = P2J6.forbiddenSubstrings(alpha, n, tabu);
            check.update(result.toString().getBytes());
        }
        assertEquals(2852450563L, check.getValue());
    }
}
