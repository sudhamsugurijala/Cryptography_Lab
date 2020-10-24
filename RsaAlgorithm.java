import java.util.*;
import java.math.BigInteger;

class RsaAlgorithm {

	private static Scanner ip = new Scanner(System.in);
	private static BigInteger p, q, n;
    private static BigInteger phi, e, d;
    private static int bitlength = 1024;
    private static Random r;
	private static String plaintext, ciphertext;
	private static byte[] encrypted; 
	
	public static void genKey() throws Exception{
		r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);

        System.out.println("Public key:\n\ne value:");
        System.out.println(e);
        System.out.println("\nn value: ");
        System.out.println(n);

        System.out.println("\nPrivate Key (This User):\n\nd value:");
        System.out.println(d);
        System.out.println();
	}

	public static byte[] encrypt(byte[] message) {
        return (new BigInteger(message)).modPow(e, n).toByteArray();
    }

    public static byte[] decrypt(byte[] message) {
        return (new BigInteger(message)).modPow(d, n).toByteArray();
    }

	public static void encryptMsg() {
		ip.nextLine();
		System.out.print("Enter Plaintext: ");
		plaintext = ip.nextLine();
		System.out.println("Plaintext (Bytes):\n" + Base64.getEncoder().encodeToString(plaintext.getBytes())+"\n");
		encrypted = encrypt(plaintext.getBytes());
		ciphertext = Base64.getEncoder().encodeToString(encrypted);
		System.out.println("Ciphertext:\n"+ciphertext+"\n");
	}

	public static void decryptMsg() {
		ip.nextLine();
		System.out.println("Enter Ciphertext:");
		ciphertext = ip.nextLine();
		encrypted = Base64.getDecoder().decode(ciphertext);
		byte[] decrypted = decrypt(encrypted);
        plaintext =  new String(decrypted);
		System.out.println("\nPlaintext (Original): "+plaintext+"\n");		
	}

	public static void main(String[] args) {
		System.out.println("RSA Algorithm (1024 bit keys)\n=== ========= ===== === =====\n");
		System.out.println("1. Encrypt\n2. Decrypt\n");
		try {
			genKey();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		int opt = 0;
		while(opt != -1) {
			System.out.print("Enter Choice: ");
			opt = ip.nextInt();
			switch(opt) {
				case 1: encryptMsg(); break;
				case 2: decryptMsg(); break;
				default: opt = -1;
			}
		}
	}
}

