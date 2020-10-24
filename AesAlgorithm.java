import java.util.*;
import java.security.MessageDigest;
import javax.crypto.*;
import javax.crypto.spec.*;

class AesAlgorithm {
  private static int opt = 0;
  private static byte[] key_bytes = new byte[16];
  private static String key = null, message = null;
  private static Scanner ip = new Scanner(System.in);

  public static void keyGen() {
    try {
      ip.nextLine(); // absorb line
      System.out.print(" Enter Key: ");
      key = ip.nextLine(); 
      byte[] temp_bytes = key.getBytes("UTF-8");
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      temp_bytes = sha.digest(temp_bytes);
      key_bytes = Arrays.copyOf(temp_bytes, 16);
      String temp = Base64.getEncoder().encodeToString(key_bytes);
      System.out.println(" Key: " + temp);
      System.out.println(String.format(" Key size: %d bytes",key_bytes.length));
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void encryptMsg() {
    keyGen(); // key_bytes obtained here
    try{
      System.out.print(" Enter Message: ");
      message = ip.nextLine();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      SecretKey secret_key = new SecretKeySpec(key_bytes, "AES");
      cipher.init(Cipher.ENCRYPT_MODE, secret_key);
      byte[] ct = cipher.doFinal(message.getBytes("UTF-8"));
      String ciphertext = Base64.getEncoder().encodeToString(ct);
      System.out.println(" Cipher Text: " + ciphertext);
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void decryptMsg() {
    keyGen();
    try {
      System.out.print(" Enter Cipher Message: ");
      message = ip.nextLine();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      SecretKey secret_key = new SecretKeySpec(key_bytes, "AES");
      cipher.init(Cipher.DECRYPT_MODE, secret_key);
      byte[] pt = Base64.getDecoder().decode(message);
      pt = cipher.doFinal(pt);
      String plaintext = new String(pt);
      System.out.println(" Plain Text: " + plaintext);
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void main(String[] args) {
  	System.out.println("\n AES (Advanced Encryption Standard)\n === ========= ========== ========");
  	System.out.println("\n 1. Encrypt\n 2. Decrypt\n 3. Key Generation\n");
  	while(opt != -1) {
  		System.out.print("\n Enter an option: ");
  		opt = ip.nextInt();
  		switch(opt) {
  			case 1: encryptMsg(); break;
  			case 2: decryptMsg(); break;
  			case 3: keyGen(); break;
  			default: opt = -1;
  		}
  	}
  }
}
