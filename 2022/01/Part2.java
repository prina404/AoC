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
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty()) {
                topThree.add(sum);
                topThree.sort(null);
                if (topThree.size() > 3)
                    topThree.remove(0);
                sum = 0;
                continue;
            }
            sum += Integer.parseInt(line);
        }
        s.close();
        int max = 0;
        for (int i : topThree)
            max += i;
        System.out.println(max);
    }
}
