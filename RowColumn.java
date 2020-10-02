import java.util.*;

class RowColumn {
	private static int opt = 0, n=0, m=0;
	private static Scanner ip = new Scanner(System.in);
	private static String text = null, key = null;

	public static void checkText(String text) throws Exception {
    	text = text.replaceAll(" ", "");
    	for(int i=0; i < text.length(); ++i) {
    	  if(!Character.isLetter(text.charAt(i))) {
    	    throw new Exception(" Invalid text!\n");
    	  }
    	}
  	}

	public static void encryptDecrypt(boolean mode) {
		ip.nextLine(); // take extra buffer
		try{
			System.out.print("\nEnter Text: ");
			text = ip.nextLine();
			checkText(text);
			System.out.print("Enter number of rows: ");
			n = ip.nextInt();
			if(n < 1) throw new Exception("Invalid input!!\n");
			System.out.print("Enter number of columns: ");
			m = ip.nextInt();
			if(m < 1) throw new Exception("Invalid input!!\n");
			ip.nextLine(); // absorb new line
			System.out.print("Enter key: ");
			key = ip.nextLine();
			if(key.length() != m) 
				throw new Exception("Key length does not match number of columns!\n");

			int str_len = text.length();
			System.out.print("Output Text: ");
			if(mode) { // ENCRYPTION
				char[][] matrix = new char[n][m];
				int k=0;
				for(int i=0; i<n; ++i) {
					for(int j=0; j<m; ++j) {
						if(k < str_len) matrix[i][j] = text.charAt(k++);
						else matrix[i][j] = ' '; //blank padding
					}
				}
				for(int i=0; i<m; ++i) {
					k = (key.charAt(i) - '0'); 
					--k; // 0 indexing
					for(int j=0; j<n; ++j) 
						if(Character.isLetter(matrix[j][k]) || matrix[j][k] == ' ')
							System.out.print(matrix[j][k]);
				}
			}
			else { // DECRYPTION
				char[][] matrix = new char[m][n];
				int k=0;
				for(int i=0; i<m; ++i) {
					for(int j=0; j<n; ++j) {
						if(k < str_len) matrix[i][j] = text.charAt(k++);
						else matrix[i][j] = ' '; // blank padding
					}
				}
				int[] actual_order = new int[m];
				for(int i=0; i<m; ++i) actual_order[(key.charAt(i) - '0')-1] = i;
				for(int i=0; i<n; ++i) {
					for(int j=0; j<m; ++j) {
						char temp_letter = matrix[actual_order[j]][i];
						if(Character.isLetter(temp_letter) || temp_letter == ' ')
						System.out.print(temp_letter);
					}
				}
			}
			System.out.println("\n");
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		System.out.println("Row Column Cipher\n=== ====== ======\n");
		System.out.println("1. Encrypt\n2. Decrypt\n");
		
		while(opt != -1) {
			System.out.print("Choose an option: ");
			opt = ip.nextInt();
			switch(opt) {
				case 1: encryptDecrypt(true); break;
				case 2: encryptDecrypt(false); break;
				default: opt = -1;
			}
		}
	}
}