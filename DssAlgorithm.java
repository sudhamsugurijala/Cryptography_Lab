import java.security.KeyPair; 
import java.security.KeyPairGenerator; 
import java.security.PrivateKey; 
import java.security.PublicKey; 
import java.security.SecureRandom; 
import java.security.Signature; 
import java.util.*;

class DssAlgorithm {

	private static final String signing_algorithm = "SHA256withRSA"; 
    private static final String rsa = "RSA";
    private static String input_text;
    private static Scanner ip = new Scanner(System.in);

	public static byte[] createDigitalSignature(byte[] input, PrivateKey key) throws Exception { 
        Signature signature = Signature.getInstance(signing_algorithm); 
        signature.initSign(key); 
        signature.update(input); 
        return signature.sign(); 
    }
  
    public static KeyPair generateRSAKeyPair() throws Exception { 
        SecureRandom secureRandom = new SecureRandom(); 
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(rsa); 
        keyPairGenerator.initialize(2048, secureRandom); 
        return keyPairGenerator.generateKeyPair(); 
    } 

	public static boolean verifyDigitalSignature(byte[] input, byte[] signatureToVerify, PublicKey key) throws Exception { 
        Signature signature = Signature.getInstance(signing_algorithm); 
        signature.initVerify(key); 
        signature.update(input); 
        return signature.verify(signatureToVerify); 
    }
	public static void main(String[] args) {
		System.out.print("\nDigital Signature Standard\n======= ========= ========\n");
		System.out.print("\nEnter text: ");
		input_text = ip.nextLine();
		try{
			KeyPair keyPair = generateRSAKeyPair(); 
        	byte[] signature = createDigitalSignature(input_text.getBytes(), keyPair.getPrivate()); 
  
        	System.out.println("\nSignature:\n"+ Base64.getEncoder().encodeToString(signature)); 
        	System.out.println("\nSignature Verification: "+ 
        		verifyDigitalSignature(input_text.getBytes(), signature, keyPair.getPublic())+"\n");

        	System.out.print("\nEnter data: ");
			input_text = ip.nextLine();
			System.out.println("\nSignature:\n"+ Base64.getEncoder().encodeToString(signature)); 
        	System.out.println("\nSignature Verification: "+ 
        	verifyDigitalSignature(input_text.getBytes(), signature, keyPair.getPublic())+"\n");			

        } catch(Exception e) {
        	System.out.println(e.toString());
        } 
	}
}

