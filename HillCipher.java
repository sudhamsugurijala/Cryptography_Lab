import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
class HillCipher {

	private static String plaintext = null, ciphertext = null;
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Scanner ip = new Scanner(System.in);
	private static int[][] key_matrix = new int[3][3];
	private static int[] input_trigram = new int[3];
	private static int[] output_trigram = new int[3];
	private static int det_inverse = -1;
	private static int opt = 0;

	public static void checkText(String text) throws Exception {
    text = text.replaceAll(" ", "");
    for(int i=0; i < text.length(); ++i) {
      if(!Character.isLowerCase(text.charAt(i))) {
        throw new Exception(" Invalid text!");
      }
    }
  }

  public static void formInputTrigram(char x, char y, char z) {
  	input_trigram[0] = (int)x - 97;
  	input_trigram[1] = (int)y - 97;
  	input_trigram[2] = (int)z - 97;
  }

  public static int retMinor(int x, int y) {
  	int[] temp = new int[4];
  	int index = 0;
  	for(int i=0; i<3; ++i) {
  		for(int j=0; j<3; ++j) {
  			if(i==x) break;
  			else if(j==y) continue;
  			else {
  				temp[index] = key_matrix[i][j]; ++index;
  			}
  		}
  	}
  	return (temp[0]*temp[3])-(temp[1]*temp[2]);
  }

  public static int retDeterminant() {
  	int first = key_matrix[0][0]*retMinor(0, 0);
  	int second = -1*key_matrix[0][1]*retMinor(0, 1);
  	int third = key_matrix[0][2]*retMinor(0, 2);
  	int result = first + second + third;
  	return result;
  }

  public static void findDetInverse() {
  	int det = retDeterminant();
  	while(det < 0) det += 26;
  	det = det%26;
  	for(int i=1; i<26; ++i) {
  		if((det*i)%26 == 1) {
  			det_inverse = i;
  			break;
  		}
  	}
  }

  public static void formInverseMatrix() {
  	int[] temp = new int[9];
  	int index = 0, sign = 1;
  	findDetInverse();
  	for(int i=0; i<3; ++i) {
  		for(int j=0; j<3; ++j) {
  			temp[index] = sign*retMinor(j, i);
  			while(temp[index] < 0) temp[index] += 26;
  			temp[index] %= 26; ++index;
  			sign *= -1;
  		}
  	}
    index = 0;
  	for(int i=0; i<3; ++i) {
  		for(int j=0; j<3; ++j) {
  			key_matrix[i][j] = ((temp[index]*det_inverse)%26); ++index;
  		}
  	}
  }

  public static void formOutputTrigram() {
  	for(int i=0; i<3; ++i) {
  		output_trigram[i] = input_trigram[0]*key_matrix[i][0] + 
  		  									input_trigram[1]*key_matrix[i][1] + 
  		  									input_trigram[2]*key_matrix[i][2];
  		while(output_trigram[i] < 0) output_trigram[i] = output_trigram[i] + 26;
  		output_trigram[i] = output_trigram[i]%26;
  	}
  }

  public static void getKeyMatrix() throws Exception {
  	for(int i=0; i<3; ++i) {
  		StringTokenizer st = new StringTokenizer(br.readLine());
  		for(int j=0; j<3; ++j) {
  			key_matrix[i][j] = (int)Integer.parseInt(st.nextToken());
  			if(key_matrix[i][j] < 0 || key_matrix[i][j] > 25)
  				throw new Exception(" Key value exceeds range!!");
  		}
  	}
  	findDetInverse();
  	if(det_inverse == -1) throw new Exception(" Invalid Matrix (Non Invertible)!");
  }

  public static void encryptMsg() {
  	try {
  		ip.nextLine(); // absorb newline
  		System.out.print("\nEnter Plaintext (lower case letters): ");
    	plaintext = ip.nextLine();
    	while(plaintext.length() % 3 != 0) plaintext = plaintext + "x"; // padding x
    	checkText(plaintext);
    	System.out.println("Enter Key Matrix (3X3 Matrix): ");
    	getKeyMatrix();

    	StringBuffer sb = new StringBuffer();
    	for(int i=0; i<plaintext.length(); i=i+3) {
    		formInputTrigram(plaintext.charAt(i), plaintext.charAt(i+1), plaintext.charAt(i+2));
    		formOutputTrigram();
    		sb.append((char)(97 + output_trigram[0]));
    		sb.append((char)(97 + output_trigram[1]));
    		sb.append((char)(97 + output_trigram[2]));
    	}
    	ciphertext = sb.toString();
    	System.out.print("Cipher Text: " + ciphertext + "\n");
  	} catch(Exception e) {
  		System.out.println(e.toString());
  	}
  }

  public static void decryptMsg() {
  	try {
  		ip.nextLine(); // absorb newline
  		System.out.print("\nEnter Ciphertext (lower case letters): ");
    	ciphertext = ip.nextLine();
    	checkText(ciphertext);
    	System.out.println("Enter Key Matrix (3X3 Matrix):");
    	getKeyMatrix();
    	formInverseMatrix();

    	StringBuffer sb = new StringBuffer();
    	for(int i=0; i<ciphertext.length(); i=i+3) {
    		formInputTrigram(ciphertext.charAt(i), ciphertext.charAt(i+1), ciphertext.charAt(i+2));
    		formOutputTrigram();
    		sb.append((char)(97 + output_trigram[0]));
    		sb.append((char)(97 + output_trigram[1]));
    		sb.append((char)(97 + output_trigram[2]));
    	}
    	plaintext = sb.toString();
    	System.out.print("Plain Text: " + plaintext + "\n");
  	} catch(Exception e) {
  		System.out.println(e.toString());
  	}
  }
	
	public static void main(String[] args) {
		System.out.println("\nHill Cipher (Trigrams)\n==== ====== ==========");
		System.out.println("\n1. Encrypt\n2. Decrypt\n");
		while(opt != -1) {
			System.out.print("\nEnter an option: ");
			opt = ip.nextInt();
			switch(opt) {
				case 1: encryptMsg(); break;
				case 2: decryptMsg(); break;
				default: opt = -1;
			}
		}
	}
}
