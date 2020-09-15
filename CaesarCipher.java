import java.util.*;

class CaesarCipher {

  private static String plaintext = null, ciphertext = null;
  private static Scanner ip = new Scanner(System.in);
  private static int key = 0, opt=0;
  

  public static void checkText(String text) throws Exception {
    text = text.replaceAll(" ", "");
    for(int i=0; i < text.length(); ++i) {
      if(!Character.isLetter(text.charAt(i))) {
        throw new Exception(" Invalid text!");
      }
    }
  }

  public static void checkKey(int key) throws Exception {
    if(key < 0 || key > 25) {
      throw new Exception("Invalid Key\n");
    }
  }

  public static void encryptMsg() {

  	try {
      ip.nextLine(); // absorb newline
      System.out.print("\n Enter Plaintext: ");
      plaintext = ip.nextLine();
      checkText(plaintext);
      System.out.print(" Enter Key: ");
      key = ip.nextInt();
      checkKey(key);
      StringBuffer result = new StringBuffer();
      for (int i = 0; i < plaintext.length(); i++) {
       if (Character.isUpperCase(plaintext.charAt(i))) {
         char ch = (char) (((int) plaintext.charAt(i) + key - 65) % 26 + 65);
         result.append(ch);
       }
       else if(Character.isLowerCase(plaintext.charAt(i))) {
         char ch = (char) (((int) plaintext.charAt(i) + key - 97) % 26 + 97);
         result.append(ch);
       }
       else result.append(' ');
      }
      ciphertext = result.toString();
      System.out.print(" Cipher Text: " + ciphertext + "\n");
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public static String decryptMsg(String ciphertext, int key) {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < ciphertext.length(); i++) {
      if (Character.isUpperCase(ciphertext.charAt(i))) {
        char ch = (char) (((int) ciphertext.charAt(i)  + 26 - key - 65) % 26 + 65);
        result.append(ch);
      } 
      else if(Character.isLowerCase(ciphertext.charAt(i))) {
        char ch = (char) (((int) ciphertext.charAt(i) + 26 - key - 97) % 26 + 97);
        result.append(ch);
      }
      else result.append(' ');
    }
    plaintext = result.toString();
    return plaintext;
  }

  public static void decryptMsg() {
  	try {
  		ip.nextLine();
  		System.out.print("\n Enter Ciphertext: ");
      ciphertext = ip.nextLine();
      checkText(ciphertext);
      System.out.print(" Enter Key: ");
      key = ip.nextInt();
      checkKey(key);

      StringBuffer result = new StringBuffer();
      for (int i = 0; i < ciphertext.length(); i++) {
       if (Character.isUpperCase(ciphertext.charAt(i))) {
         char ch = (char) (((int) ciphertext.charAt(i)  + 26 - key - 65) % 26 + 65);
         result.append(ch);
       } 
       else if(Character.isLowerCase(ciphertext.charAt(i))) {
         char ch = (char) (((int) ciphertext.charAt(i) + 26 - key - 97) % 26 + 97);
         result.append(ch);
       }
       else result.append(' ');
      }
      plaintext = result.toString();
      System.out.print(" Plain Text: " + plaintext + "\n");
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void cryptAnalysis() {
  	HashMap<String, Integer> dictionary = new HashMap<>();
  	dictionary.put("chair", 1);
  	dictionary.put("table", 1);
  	dictionary.put("apple", 1);
  	dictionary.put("the", 1);
  	dictionary.put("hello", 1);

  	try {
  		ip.nextLine();
  		System.out.print("\n Enter Ciphertext: ");
      ciphertext = ip.nextLine();
      checkText(ciphertext);
    } catch(Exception e) {
      System.out.println(e.toString());
    }
    key = -1;
    for(int i=0; i<26; ++i) {
    	plaintext = decryptMsg(ciphertext, i);
    	System.out.print(" Key = "+i+", Plain Text: " + plaintext + "\n");
    	String[] splits = plaintext.split("\\s+"); // split with whitespace
    	for(String str : splits) {
    		if(dictionary.containsKey(str.toLowerCase())) key = i;
    	}
    }
    if(key != -1) System.out.print(" Suggested Key: " + key + ", Decrypted Text: "+decryptMsg(ciphertext, key)+"\n");
    else System.out.print(" No key matching dictionary!\n");
  }

  public static void main(String[] args) {
  	System.out.println("\n Caesar Cipher\n ====== ======");
  	System.out.println("\n 1. Encrypt\n 2. Decrypt\n 3. Crypt Analysis\n");
  	while(opt != -1) {
  		System.out.print("\n Enter an option: ");
  		opt = ip.nextInt();
  		switch(opt) {
  			case 1: encryptMsg(); break;
  			case 2: decryptMsg(); break;
  			case 3: cryptAnalysis(); break;
  			default: opt = -1;
  		}
  	}
  }
}
