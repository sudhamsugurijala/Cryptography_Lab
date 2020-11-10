import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;

class Sha1Algorithm {

	private static Scanner ip = new Scanner(System.in);
	private static String message = "";

	public static String getShaDigest(String message) {
		String result = "";
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] message_digest = md.digest(message.getBytes());
			BigInteger num = new BigInteger(1, message_digest);
			result = num.toString(16);
			while(result.length() < 32) {
				result = "0"+result;
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.print("\nSHA1 Algorithm \n==== =========\n");
		System.out.print("\nEnter Message: ");
		message = ip.nextLine();
		System.out.println("\nGenerated Hash:");
		String digest = getShaDigest(message);
        System.out.println(digest+"\n");
	}
}
