import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {
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

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        int score = 0;
        while (s.hasNextLine()) {
            RPS opponent = makeEnum(s.next());
            RPS me = makeEnum(s.next());
            switch (me) {
                // losing
                case ROCK -> score += Math.floorMod(opponent.ordinal() - 1, 3) + 1;
                // draw
                case PAPER -> score += opponent.ordinal() + 4;
                // win
                case SCISSOR -> score += ((opponent.ordinal() + 1) % 3) + 7;
            }

        }
        System.out.println(score);
    }
}
