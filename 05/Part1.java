import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Part1 {
    static String input = """
            ZJNWPS
            GST
            VQRLH
            VSTD
            QZTDBMJ
            MWTJDCZL
            LPMWGTJ
            NGMTBFQH
            RDGCPBQW
            """;

    private static Deque<Character> makeDeque(String s) {
        Deque<Character> d = new LinkedList<>();
        for (char c : s.toCharArray())
            d.push(c);
        return d;

    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        List<Deque<Character>> cargo = new ArrayList<>();
        for (String stack : input.split("\n"))
            cargo.add(makeDeque(stack));

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] tokens = line.split(" ");
            for (int i = 0; i < Integer.parseInt(tokens[1]); i++) {
                char toMove = cargo.get(Integer.parseInt(tokens[3]) - 1).pop();
                cargo.get(Integer.parseInt(tokens[5]) - 1).push(toMove);
            }
        }
        String res = "";
        for (Deque<Character> d : cargo) {
            res += d.peek();
        }
        System.out.println(res);
    }
}
