import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        List<Character> lst = new ArrayList<>();
        char[] input = s.nextLine().toCharArray();
        for (int i = 14; i < input.length; i++) {
            lst.add(input[i]);
            if (diff(lst, i)) {
                System.out.println(i + 1);
                return;
            }
        }
    }

    private static boolean diff(List<Character> ls, int i) {
        Set<Character> s = new TreeSet<>();
        for (int j = i; j > i - 14; j--)
            s.add(ls.get(j));
        return s.size() == 14;
    }
}
