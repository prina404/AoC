import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {

    static List<Elf> elves = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        int y = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == '#')
                    elves.add(new Elf(x, y));
            y++;
        }
        int i = 1;
        while (solve()) 
            if (i++ == 10)
                System.out.println("Part1: " + score());
        
        System.out.println("Part2: " + i);
    }

    static Set<Coord> makeSet() {
        Set<Coord> res = new HashSet<>();
        for (Elf elf : elves)
            res.add(elf.pos);
        return res;
    }

    static boolean solve() {
        Map<Coord, Integer> map = new HashMap<>();
        var occupation = makeSet();
        boolean hasMoved = false;
        for (Elf e : elves) {
            Coord proposal = e.makeProposal(occupation);
            if (map.containsKey(proposal))
                map.replace(proposal, map.get(proposal) + 1);
            else
                map.put(proposal, 1);
        }
        for (Elf e : elves) {
            if (map.get(e.next) > 1)
                e.next = e.pos;
            if (e.makeMove())
                hasMoved = true;
        }
        Elf.updateOrder();
        return hasMoved;
    }

    static int score() {
        int topX = 0, bottomX = 0, topY = 0, bottomY = 0;
        for (Elf elf : elves) {
            topX = (elf.pos.x() > topX) ? elf.pos.x() : topX;
            topY = (elf.pos.y() > topY) ? elf.pos.y() : topY;
            bottomX = (elf.pos.x() < bottomX) ? elf.pos.x() : bottomX;
            bottomY = (elf.pos.y() < bottomY) ? elf.pos.y() : bottomY;
        }
        int score = 0;
        for (int i = bottomY; i <= topY; i++) {
            for (int j = bottomX; j <= topX; j++) {
                Elf test = new Elf(j, i);
                if (!elves.contains(test))
                    score++;
            }
        }
        return score;
    }

}