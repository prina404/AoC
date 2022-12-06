import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {
    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
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
        long stop = System.nanoTime();
        System.out.println("time -> " + (stop - start) / 1000000.0);
        System.out.println(max);

    }
}
