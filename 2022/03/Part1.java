import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part1 {
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
            List<Integer> line = toIntList(s.nextLine());
            for (Integer i : line.subList(0, line.size() / 2)) {
                if (line.subList(line.size() / 2, line.size()).contains(i)) {
                    score += i;
                    break;
                }
            }
        }
        System.out.println(score);
    }
}
