import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.*;
import java.util.zip.CRC32;

public class TenFourTest {

    @Test public void testShortestPathThousand() {
        testShortestPath(500, 1154212991L);
    }
    
    private void testShortestPath(int n, long expected) {
        CRC32 check = new CRC32();
        for(int i = 1; i < n; i++) {
            int limit = 100 * n + 1;
            List<Integer> result;
            do {
                result = TenFour.shortestPath(i, limit);
                limit = limit * 2;
            } while(result.size() == 0);
            //System.out.println(i + ": " + result);
            check.update(result.toString().getBytes());
        }
        assertEquals(expected, check.getValue());
    }
    
}
