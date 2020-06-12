public class FermatPrimality {

    public static long powerMod(long a, long b, long m) {
        if(b == 0) { return 1; }
        else if(b == 1) { return a % m; }
        else if(b % 2 == 0) {
            return powerMod((a * a) % m, b / 2, m);
        }
        else {
            return (a * powerMod(a, b - 1, m)) % m;
        }
    }
    
    public static boolean isNegativeFermatWitness(long p, int a) {
        return powerMod(a, p - 1, p) != 1;
    }
}
