import java.util.*;

class VigenereCipher {

	private static String plaintext = null, ciphertext = null, key = null;
	private static Scanner ip = new Scanner(System.in);
	private static char[][] key_matrix = new char[26][26];
	private static int opt = 0;
	
	public static void checkText(String text) throws Exception {
    text = text.replaceAll(" ", "");
    for(int i=0; i < text.length(); ++i) {
      if(!Character.isLowerCase(text.charAt(i))) {
        throw new Exception(" Invalid text!");
      }
    }
  }

  public static void initializeKeyMatrix() {
  	int i=0;
  	for(int x=0; x<26; ++x) {
  		for(int y=0; y<26; ++y) {
  			key_matrix[x][y] = (char) (97 + ((i + y)%26)); 
  		}
  		++i;
  	}
  }
	
  public static void encryptMsg() {
  	try {
  		ip.nextLine(); // absorb newline
  		System.out.print("\n Enter Plaintext (lower case letters): ");
    	plaintext = ip.nextLine();
    	checkText(plaintext);
    	System.out.print(" Enter Key (lower case letters): ");
    	key = ip.nextLine();
    	checkText(key);
    	int l=0;
    	while(key.length() < plaintext.length()) {
    		key = key + key.charAt(l%key.length());
    		++l;
    	}
    	StringBuffer result = new StringBuffer();
    	for (int i = 0; i < plaintext.length(); i++) {
    		int x = (int)key.charAt(i) - 97;
      	int y = (int)plaintext.charAt(i) - 97;
      	result.append(key_matrix[x][y]);
      }
      ciphertext = result.toString();
      System.out.print(" Cipher Text: " + ciphertext + "\n");
    } catch(Exception e) {
    	System.out.println(e.toString());
    }
  }

  public static void decryptMsg() {
  	try {
  		ip.nextLine(); // absorb newline
  		System.out.print("\n Enter Ciphertext (lower case letters): ");
    	ciphertext = ip.nextLine();
    	checkText(ciphertext);
    	System.out.print(" Enter Key (lower case letters): ");
    	key = ip.nextLine();
    	checkText(key);
    	int l=0;
    	while(key.length() < ciphertext.length()) {
    		key = key + key.charAt(l%key.length());
    		++l;
    	}
    	StringBuffer result = new StringBuffer();
    	for (int i = 0; i < ciphertext.length(); i++) {
    			int x = (int)key.charAt(i) - 97;
    			int y = 0;
    			for(y=0; y<26; ++y) {
    				if(key_matrix[x][y] == ciphertext.charAt(i)) break;
    			}
    	  	result.append((char)(97+y));
    	  }
    	plaintext = result.toString();
    	System.out.print(" Plain Text: " + plaintext + "\n");
    } catch(Exception e) {
    	System.out.println(e.toString());
    }
  }

	public static void main(String[] args) {
		System.out.println("\n Vigenere Cipher\n ======== ======");
		System.out.println("\n 1. Encrypt\n 2. Decrypt\n");

		initializeKeyMatrix();
		while(opt != -1) {
			System.out.print("\n Enter an option: ");
			opt = ip.nextInt();
			switch(opt) {
				case 1: encryptMsg(); break;
				case 2: decryptMsg(); break;
				default: opt = -1;
			}
		}
	}
}