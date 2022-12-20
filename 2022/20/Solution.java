import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Solution {
    // this is needed to deal with duplicates :P
    public record Num(long value, int sequence) {
    }

    public static void main(String[] args) throws IOException {
        String in = Files.readString(Path.of("input.txt"));

        System.out.println("part1: " + solve(in, 1, 1));
        System.out.println("part1: " + solve(in, 811589153, 10));
    }

    public static long solve(String in, int key, int iterations) {
        Scanner s = new Scanner(in);

        List<Num> nums = new LinkedList<>();
        while (s.hasNextLine())
            nums.add(new Num(Long.parseLong(s.nextLine()) * key, nums.size()));
        s.close();

        List<Num> copy = new ArrayList<>(nums);
        for (int i = 0; i < iterations; i++)
            for (Num n : copy) {
                int index = nums.indexOf(n);
                nums.remove(index);
                int newPos = Math.floorMod(index + n.value, nums.size());
                if (newPos == 0)
                    nums.add(n);
                else
                    nums.add(newPos, n);
            }

        int indexOFZero = indexOFZero(nums);
        long sum = 0;
        for (Integer i : List.of(1000, 2000, 3000))
            sum += nums.get((indexOFZero + i) % nums.size()).value;

        return sum;
    }

    public static int indexOFZero(List<Num> l) {
        for (int i = 0; i < l.size(); i++)
            if (l.get(i).value == 0)
                return i;
        return -1;
    }
}
