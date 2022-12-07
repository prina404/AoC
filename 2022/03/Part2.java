import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part2 {
    private static List<Integer> toIntList(String lst) {
        char[] arr = lst.toCharArray();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 'a' && arr[i] <= 'z')
                res.add((int) arr[i] - 'a' + 1);
            else 
                res.add((int) arr[i] - 'A' + 27);
        }
        return res;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        int score = 0;

        while (s.hasNextLine()) {
            List<Integer> line1 = toIntList(s.nextLine());
            List<Integer> line2 = toIntList(s.nextLine());
            List<Integer> line3 = toIntList(s.nextLine());
            for (Integer i : line1) {
                if (line2.contains(i) && line3.contains(i)) {
                    score += i;
                    break;
                }
            }
        }
        System.out.println(score);
    }
}
