import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {
    public enum RPS {
        ROCK,
        PAPER,
        SCISSOR;
    }

    private static RPS makeEnum(String s) {
        switch (s) {
            case "A":
            case "X":
                return RPS.ROCK;
            case "B":
            case "Y":
                return RPS.PAPER;
            case "C":
            case "Z":
                return RPS.SCISSOR;
        }
        return null;
    }

    private static int getPoints(RPS p1, RPS p2) {
        if (p1 == p2)
            return 3;
        if (p2.ordinal() == (p1.ordinal() + 1) % 3)
            return 6;
        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        int score = 0;
        while (s.hasNextLine()) {
            RPS opponent = makeEnum(s.next());
            RPS me = makeEnum(s.next());
            score += getPoints(opponent, me) + me.ordinal() + 1;

        }
        System.out.println(score);
    }
}
