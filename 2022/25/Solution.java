import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        List<String> entries = new ArrayList<>();
        while (s.hasNextLine()) {
            StringBuilder sb = new StringBuilder();
            entries.add(sb.append(s.nextLine()).reverse().toString());
        }

        long sum = 0;
        for (String n : entries)
            sum += decode(n);

        String res = new StringBuilder().append(encode(sum)).reverse().toString();
        System.out.println("Part1: " + sum + " -> " + res);

    }

    public static long decode(String num) {
        long res = 0;
        for (int i = 0; i < num.length(); i++) {
            switch (num.charAt(i)) {
                case '-' -> res += -1 * Math.pow(5, i);
                case '=' -> res += -2 * Math.pow(5, i);
                default -> res += Character.digit(num.charAt(i), 10) * Math.pow(5, i);
            }
        }
        return res;
    }

    public static String encode(long num) {
        String res = "";
        while (num > 0) {
            int rem = Math.floorMod(num + 2, 5) - 2;
            switch (rem) {
                case -2 -> res += "=";
                case -1 -> res += "-";
                default -> res += String.valueOf(rem);
            }
            num = Math.floorDiv(num + 2, 5);
        }
        return res;
    }
}