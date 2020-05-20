import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

public class P2J7Test {

    private static final int SEED = 76543;

    @Test public void testHuntingtonHill() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        HashSet<Integer> seen = new HashSet<>();
        int scale = 1;
        for(int i = 1; i < 400; i++) {
            if(i % 50 == 0) { scale *= 10; }
            int[] pops = new int[i + 1];
            seen.clear();
            for(int j = 0; j < pops.length; j++) {
                int p;
                int count = 0;
                do {
                    if(j > 0 && rng.nextDouble() < .2) {
                        p = pops[j-1] + 1;
                    }
                    else {
                        p = (rng.nextInt(50) + 1) * scale;
                        p += rng.nextInt(p);
                    }
                } while(seen.contains(p));
                seen.add(p);
                pops[j] = p;
            }
            int seats = 2 * i + rng.nextInt(i + 2);
            int[] result = P2J7.huntingtonHill(pops, seats);
            check.update(Arrays.toString(result).getBytes());
            // if(i < 10) {
                // System.out.println("Populations are " + Arrays.toString(pops));
                // System.out.println("Results for " + seats + " is " + Arrays.toString(result));
            // }
        }
        assertEquals(3779035071L, check.getValue());
    }

    @Test public void testJosephus() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        List<String> people = Arrays.asList(
                "bob", "ted", "alice", "ringo",
                "ross", "rachel", "joey",
                "phoebe", "chandler", "monica",
                "charlie", "alan", "walden"
            );
        List<String> items = new ArrayList<String>();
        for(int i = 0; i < 1000; i++) {
            String next = people.get(i % people.size());
            if(i >= people.size()) { next += i; }
            items.add(next);
            int k = rng.nextInt(2 * i + 2) + 1;
            // Sometimes you want to have stuff like this for debugging purposes:
            // if(k < 10) { System.out.println(k + " " + items); }
            items = P2J7.josephus(items, k);
            check.update(items.toString().getBytes());
        }
        assertEquals(3746131365L, check.getValue());
    }

}
