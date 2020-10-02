import java.util.*;

class RailFence {
	private static int opt = 0, railnum = 0;
	private static Scanner ip = new Scanner(System.in);
	private static String text = null;

	public static void checkText(String text) throws Exception {
    	text = text.replaceAll(" ", "");
    	for(int i=0; i < text.length(); ++i) {
    	  if(!Character.isLetter(text.charAt(i))) {
    	    throw new Exception(" Invalid text!\n");
    	  }
    	}
  	}

	public static void displayInRails(boolean mode) {
		ip.nextLine(); // take extra buffer
		try{
			System.out.print("\nEnter Text: ");
			text = ip.nextLine();
			checkText(text);
			System.out.print("Enter number of rails / levels: ");
			railnum = ip.nextInt();
			if(railnum < 1) throw new Exception("Invalid input!!\n");
			int n = text.length();
			System.out.print("Output Text: ");
			char[][] fence = new char[railnum][n];
			boolean dir_flag = false;
			if(mode) { // ENCRYPTION
				int i=0, j=0;
				while(j < n) {
					if(i == 0 || i == railnum-1) dir_flag = !dir_flag;
					fence[i][j] = text.charAt(j++);
					i = (dir_flag ? i+1 : i-1);
				}
				for(i=0; i<railnum; ++i) {
					for(j=0; j<n; ++j) {
						if(Character.isLetter(fence[i][j]) || fence[i][j] == ' ') System.out.print(fence[i][j]);
					}
				}
			}
			else { // DECRYPTION
				int i=0, j=0;
				while(j < n) {
					if(i == 0 || i == railnum-1) dir_flag = !dir_flag;
					fence[i][j++] = ' ';
					i = (dir_flag ? i+1 : i-1);
				}
				int k=0;
				for(i=0; i<railnum; ++i) {
					for(j=0; j<n; ++j) {
						if(fence[i][j] == ' ') fence[i][j] = text.charAt(k++);
					}
				}
				i=0; j=0; dir_flag = false;
				while(j < n) {
					if(i == 0 || i == railnum-1) dir_flag = !dir_flag;
					System.out.print(fence[i][j++]);
					i = (dir_flag ? i+1 : i-1);
				}
			}
			System.out.println("\n");
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		System.out.println("Rail Fence Cipher\n==== ===== ======\n");
		System.out.println("1. Encrypt\n2. Decrypt\n");
		
		while(opt != -1) {
			System.out.print("Choose an option: ");
			opt = ip.nextInt();
			switch(opt) {
				case 1: displayInRails(true); break;
				case 2: displayInRails(false); break;
				default: opt = -1;
			}
		}
	}
}
