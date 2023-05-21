import java.util.Arrays;

public class question {
    public static String convert(String s, int numRows) {
        String[][] converted = new String[numRows][s.length()];
        for(int i = 0; i < numRows; i++){
            int col = 0;
            for(int pos = i; pos < s.length(); pos += (numRows - 1) * 2){
                converted[i][col] = s.charAt(pos) + "";
                col += numRows/2 + 1;
            }
        }
        for(int j = 0; j < numRows - 1; j++){
            int col = 0;
            for(int i = numRows + j; i < s.length() ; i += (numRows - 1) * 2){
                converted[numRows - 2 - j][col] = s.charAt(i) + "";
                col += numRows/2 + 1;
            }
        }

        String ans = "";
        for(int row = 0; row < numRows; row++){
            for(int coll = 0; coll < s.length(); coll++){
                if(converted[row][coll] != null) ans += converted[row][coll];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 4));
    }
}
