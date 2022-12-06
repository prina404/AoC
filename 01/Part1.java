import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader s = new BufferedReader(new FileReader("input.txt"));
        int sum = 0;
        int max = 0;
        while (s.ready()) {
            String line = s.readLine();
            if (line.isEmpty()) {
                max = (max > sum) ? max : sum;
                sum = 0;
                continue;
            }
            sum += Integer.parseInt(line);
        }
        System.out.println(max);
    }
}
