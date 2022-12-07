import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Part2 {
    private static boolean overlaps(int[] e1, int[] e2) {
        return  (e1[0] <= e2[0] || e1[0] <= e2[1]) &&
                (e1[1] >= e2[0] || e1[1] >= e2[1]);
    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        int overLapping = 0;
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split("[-|,]");
            int[] e1 = { Integer.parseInt(line[0]), Integer.parseInt(line[1]) };
            int[] e2 = { Integer.parseInt(line[2]), Integer.parseInt(line[3]) };
            if (overlaps(e1, e2) || overlaps(e2, e1))
                overLapping++;
        }
        System.out.println(overLapping);
    }
}
