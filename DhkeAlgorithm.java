import java.util.*;
import java.math.BigInteger;

class DhkeAlgorithm {

	private static Scanner ip = new Scanner(System.in);
	private static BigInteger p, g;
	private static long pr, phi;
	private static BigInteger private_key_a, private_key_b;
	private static BigInteger public_key_a, public_key_b;
	private static BigInteger shared_key_a, shared_key_b;

	private static Vector<Long> genPrimesFactorsList(){
        Vector<Long> primesFactors = new Vector<>();
        while(phi % 2 == 0){
            primesFactors.add((long) 2);
            phi /= 2;
        }
        for(long i=3;i<=Math.sqrt(phi);i+=2){
            if(phi % i == 0){
                primesFactors.add(i);
                phi /= i;
            }
        }
        if(phi > 2){
            primesFactors.add(phi);
        }
        return primesFactors;
    }

    private static Vector<Long> getPrimitiveRoot(){
        Vector<Long> primeFactors = genPrimesFactorsList();
        Vector<Long> primitiveRoots = new Vector<>();
        for(long i = 2;i<p.longValue();i++){
            boolean flg = false;
            for(Long l: primeFactors){
                BigInteger iBig = BigInteger.valueOf(i);
                BigInteger phiBig = BigInteger.valueOf(phi/l);
                BigInteger pBig = BigInteger.valueOf(p.longValue());
                BigInteger pRootBig = iBig.modPow(phiBig, pBig);
                if(pRootBig.compareTo(BigInteger.valueOf(1))==0){
                    flg = true;
                    break;
                }
            }
            if(!flg)primitiveRoots.add(i);
        }
        return primitiveRoots;
    }


	public static long primitiveRootGen(){
        phi = p.longValue() - 1;
        Vector<Long> primitiveRoots =  getPrimitiveRoot();
        pr = primitiveRoots.get(new Random().nextInt(primitiveRoots.size()));
        return pr;
    }


    public static long modPow(long a, long b, long c) {
        long res = 1;
        for (int i = 0; i < b; i++) {
            res *= a;
            res %= c; 
        }
        return res % c;
    }

    public static long mulMod(long a, long b, long mod) {
    	return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();
    }

	public static boolean millerRabinTest(long n) {
		if (n == 0 || n == 1)
			return false;
		if (n == 2)
			return true;
        if (n % 2 == 0)
        	return false;

        long s = n - 1;
        while (s % 2 == 0)
        	s /= 2;

        Random rand = new Random();
        for(int i=0; i<10; i++) {
        	long r = Math.abs(rand.nextLong());
        	long a = r % (n-1) + 1, temp = s;
        	long mod = modPow(a, temp, n);

        	while (temp != n-1 && mod != 1 && mod != n-1) {
        		mod = mulMod(mod, mod, n);
        		temp *= 2;
        	}
        	if (mod != n-1 && temp%2 == 0)
        		return false;
        }
        return true;
    }

	public static void genKey() throws Exception{
		
		Random r = new Random();
		boolean turn = false;
		while(!turn) {
			p = BigInteger.probablePrime(15, r);
			System.out.print("\nGenerated Prime p: ");
			System.out.println(p);
			turn = millerRabinTest(p.longValue());
			System.out.println("Miller Rabin Test for Primality (10 rounds)");
			if(!turn) System.out.println("Number is not prime, generating new number");
			else System.out.println("Number is prime!");
        }
        g = BigInteger.valueOf(primitiveRootGen());
        System.out.print("\ng (Primitive Root) value: ");
        System.out.println(g);
        System.out.println();
	}

	public static void getA() throws Exception{
		System.out.print("Enter private key of A: ");
		private_key_a = BigInteger.valueOf(ip.nextInt());
	}

	public static void getB() {
		System.out.print("Enter private key of B: ");
		private_key_b = BigInteger.valueOf(ip.nextInt());
	}

	public static void main(String[] args) {
		System.out.print("DHKE Algorithm \n==== =========\n");
		try {
			genKey();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		try {
			getA();
			getB();
		} catch(Exception e){
			System.out.println(e.toString());
		}
		public_key_a = g.modPow(private_key_a, p);
		System.out.println("\nPublic key of A: ");
		System.out.println(public_key_a);

		public_key_b = g.modPow(private_key_b, p);
		System.out.println("\n\nPublic key of B: ");
		System.out.println(public_key_b);

		shared_key_a = public_key_b.modPow(private_key_a, p);
		shared_key_b = public_key_a.modPow(private_key_b, p);
		
		System.out.println("\n\nShared key of A: ");
		System.out.println(shared_key_a);
		System.out.println("\n\nShared key of B: ");
		System.out.println(shared_key_b);

		System.out.println("\n");
	}
}

