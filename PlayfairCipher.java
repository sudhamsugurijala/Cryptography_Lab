import java.util.*;

class PlayfairCipher {
	private static char[][] key_matrix = new char[5][5];
	private static int[][] letter_pos = new int[26][2];
	private static boolean[] vis = new boolean[26];
	private static Scanner ip = new Scanner(System.in);
	private static String plaintext = null, ciphertext = null, key = null;
	private static int opt = 0;

	public static void checkText(String text) throws Exception {
    for(int i=0; i < text.length(); ++i) {
      if(!Character.isLetter(text.charAt(i))) {
        throw new Exception(" Invalid text!");
      }
    }
  }

  public static void genKey(String key) {
  	key = key.toLowerCase();
  	vis[(int)'j' - 97] = true; // i and j are same
  	int x=0, y=0;
  	for(int i=0; i<key.length(); ++i) {
  		int index = (int)key.charAt(i) - 97;
  		if(!vis[index]) {
  			vis[index] = true;
  			key_matrix[x][y] = key.charAt(i);
  			letter_pos[index][0] = x;
  			letter_pos[index][1] = y;
  			if(++y == 5) {
  				++x; y=0;
  			}
  		}
  	}

  	for(int i=0; i<26; ++i) {
  		if(!vis[i]) {
  			vis[i] = true;
  			key_matrix[x][y] = (char)(i+97);
  			letter_pos[i][0] = x;
  			letter_pos[i][1] = y;
  			if(++y==5) {
  				++x; y=0;
  			}
  		}
  	}
  }

	public static void encryptMsg() {
		try {
  		System.out.print("\n Enter Plaintext: ");
      plaintext = ip.next();
      checkText(plaintext);
      System.out.print(" Enter Key: ");
      key = ip.next();
      checkText(key);
    
    	genKey(key);
    	StringBuffer text_buff = new StringBuffer();
  		if(plaintext.length() % 2 != 0) plaintext = plaintext + 'x'; // append x for odd length strings
  		for(int i=1; i<plaintext.length(); i = i+2) {
  			if(plaintext.charAt(i) == plaintext.charAt(i-1)) {
  				text_buff.append(plaintext.charAt(i-1));
  				text_buff.append('x');
  				text_buff.append(plaintext.charAt(i));
  				text_buff.append('x');
  			}
  			else {
  				text_buff.append(plaintext.charAt(i-1));
  				text_buff.append(plaintext.charAt(i));	
  			}
  		}
  		plaintext = text_buff.toString();
  		StringBuffer ct = new StringBuffer();
  		for(int i=1; i<plaintext.length(); i = i+2) {
  			int x1 = letter_pos[(int)plaintext.charAt(i-1) - 97][0];
				int y1 = letter_pos[(int)plaintext.charAt(i-1) - 97][1];
				int x2 = letter_pos[(int)plaintext.charAt(i) - 97][0];
				int y2 = letter_pos[(int)plaintext.charAt(i) - 97][1];	

				if(x1 == x2) {
					ct.append(key_matrix[x1][(y1+1)%5]);
					ct.append(key_matrix[x1][(y2+1)%5]);
				}
				else if(y1 == y2) {
					ct.append(key_matrix[(x1+1)%5][y1]);
					ct.append(key_matrix[(x2+1)%5][y1]);
				}
				else {
					ct.append(key_matrix[x1][y2]);
					ct.append(key_matrix[x2][y1]);	
				}
  		}	
  		ciphertext = ct.toString();
  		System.out.print(" Cipher Text: " + ciphertext + "\n");
  	} catch(Exception e) {
      System.out.println(e.toString());
    }
	}

	public static void decryptMsg() {
		try {
  		System.out.print("\n Enter Ciphertext: ");
      ciphertext = ip.next();
      checkText(ciphertext);
      System.out.print(" Enter Key: ");
      key = ip.next();
      checkText(key);
    
    	genKey(key);
    	StringBuffer text_buff = new StringBuffer();
  		if(ciphertext.length() % 2 != 0) ciphertext = ciphertext + 'x'; // append x for odd length strings
	  	for(int i=1; i<ciphertext.length(); i = i+2) {
	  		if(ciphertext.charAt(i) == ciphertext.charAt(i-1)) {
	  			text_buff.append(ciphertext.charAt(i-1));
	  			text_buff.append('x');
	  			text_buff.append(ciphertext.charAt(i));
	  			text_buff.append('x');
	  		}
	  		else {
	  			text_buff.append(ciphertext.charAt(i-1));
	  			text_buff.append(ciphertext.charAt(i));	
	  		}
	  	}
	  	ciphertext = text_buff.toString();
	  	StringBuffer pt = new StringBuffer();
	  	for(int i=1; i<ciphertext.length(); i = i+2) {
	  		int x1 = letter_pos[(int)ciphertext.charAt(i-1) - 97][0];
				int y1 = letter_pos[(int)ciphertext.charAt(i-1) - 97][1];
				int x2 = letter_pos[(int)ciphertext.charAt(i) - 97][0];
				int y2 = letter_pos[(int)ciphertext.charAt(i) - 97][1];

				if(x1 == x2) {
					pt.append(key_matrix[x1][(y1-1+5)%5]);
					pt.append(key_matrix[x1][(y2-1+5)%5]);
				}
				else if(y1 == y2) {
					pt.append(key_matrix[(x1-1+5)%5][y1]);
					pt.append(key_matrix[(x2-1+5)%5][y1]);
				}
				else {
					pt.append(key_matrix[x1][y2]);
					pt.append(key_matrix[x2][y1]);	
				}
	  	}
	  	plaintext = pt.toString();
	  	System.out.print(" Plain Text: " + plaintext + "\n");
	  } catch(Exception e) {
      System.out.println(e.toString());
    }
	}

	public static void main(String[] args) {
		System.out.println("\n Playfair Cipher\n ======== ======");
  	System.out.println("\n 1. Encrypt\n 2. Decrypt");
  	System.out.println("\n (lower case letters only, no spaces, assuming letters i, j are same)\n");
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
