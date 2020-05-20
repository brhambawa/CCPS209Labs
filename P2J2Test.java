import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

public class P2J2Test {

    private static final int RUNS = 100000;
    private static final int SEED = 12345;
    
    @Test public void testRemoveDuplicates() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();        
        for(int i = 0; i < RUNS; i++) {
            StringBuilder sb = new StringBuilder();
            int len = rng.nextInt(500);
            for(int j = 0; j < len; j++) {
                char c = (char)(1 + rng.nextInt(50000));
                int rep = rng.nextInt(10) + 1;
                for(int k = 0; k < rep; k++) {
                    sb.append(c);
                }
            }
            check.update(P2J2.removeDuplicates(sb.toString()).getBytes());
        }
        assertEquals(1098912702L, check.getValue());
    }
    
    private char randomChar(Random rng) {
        return (char)(rng.nextInt(200) + 97);
    }
    
    private String buildString(Random rng, int len) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < len; j++) {
            sb.append(randomChar(rng));
        }
        return sb.toString();
    }
    
    @Test
    public void testUniqueCharacters() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32(); 
        for(int i = 0; i < RUNS; i++) {
            int len = rng.nextInt(100) + (2 << rng.nextInt(5));
            String s = buildString(rng, len);
            String res = P2J2.uniqueCharacters(s);
            check.update(res.getBytes());
        }    
        assertEquals(3756363171L, check.getValue());
    }
    
    @Test
    public void testCountSafeSquaresRooks() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32(); 
        int total = 0, answer;
        LinkedList<Integer> qxl = new LinkedList<Integer>();
        LinkedList<Integer> qyl = new LinkedList<Integer>();
        for(int n = 3; n < 100; n++) {
            boolean[][] board = new boolean[n][n];
            int count = 1;
            for(int trials = 0; trials < n + 1; trials++) {
                answer = P2J2.countSafeSquaresRooks(n, board);
                total += answer;
                check.update(answer);
                int nx, ny;
                for(int i = 0; i < count; i++) {
                    do { // find a square that does not have a rook yet
                        nx = rng.nextInt(n);
                        ny = rng.nextInt(n);
                    } while(board[nx][ny]);
                    board[nx][ny] = true;
                    qxl.add(nx);
                    qyl.add(ny);
                    answer = P2J2.countSafeSquaresRooks(n, board);
                    total += answer;
                    check.update(answer);
                }
                for(int i = 0; i < count - 1; i++) {
                    nx = qxl.removeFirst();
                    ny = qyl.removeFirst();
                    board[nx][ny] = false;
                    answer = P2J2.countSafeSquaresRooks(n, board);
                    total += answer;
                    check.update(answer);
                }
                count++;
            }
        }
        assertEquals(23172158, total);
        assertEquals(3221249387L, check.getValue());
    }
    
    @Test public void testRecaman() {
        int[] inputs = { 1, 2, 3, 4, 5, 6, 15, 99, 222, 2654, 8732, 14872, 20000,
            76212, 98721, 114322, 158799, 178320, 221099, 317600 };
        int[] expected = { 1, 3, 6, 2, 7, 13, 24, 64, 47, 5457, 18416, 18382,
            14358, 340956, 298489, 199265, 351688, 183364, 364758, 657230 }; 
        CRC32 check = new CRC32();
        for(int i = 0; i < inputs.length; i++) {
            int rec = P2J2.recaman(inputs[i]);
            assertEquals(rec, expected[i]);
            check.update(rec);
        }
        assertEquals(2348649420L, check.getValue());
    }
    
}