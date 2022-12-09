import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        List<Integer> topThree = new ArrayList<>();
        int sum = 0;
        int max = 0;

        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty()) {
                topThree.add(sum);
                sum = 0;
                line = s.nextLine();
            }
            sum += Integer.parseInt(line);
        }
        s.close();
        topThree.sort((x, y) -> y - x);
        for (int i : topThree.subList(0, 3))
            max += i;
        System.out.println(max);
    }
}
