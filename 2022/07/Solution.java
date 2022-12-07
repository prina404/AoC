import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    // Messy code, needs some SEVERE refactoring
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));

        Dir root = new Dir("/");
        Deque<String> pwd = new LinkedList<>();
        List<Dir> allDir = new ArrayList<>();

        while (s.hasNextLine()) {

            String line = s.nextLine();
            String[] tokens = line.split(" ");

            switch (tokens[1]) {

                case "cd":
                    if (tokens[2].equals(".."))
                        pwd.removeLast();
                    else if (tokens[2].equals("/"))
                        pwd.clear();
                    else
                        pwd.addLast(tokens[2]);
                    continue;

                case "ls":
                    continue;
                    
                default:
                    Dir current = root.findDir(pwd.toArray(new String[0]));
                    if (tokens[0].equals("dir")) {
                        Dir newFolder = new Dir(tokens[1]);
                        current.addElem(newFolder);
                        allDir.add(newFolder);
                    } else
                        current.addElem(new MyFile(tokens[1], Integer.parseInt(tokens[0])));
            }
        }

        System.out.println("Part 1 : " + part1(allDir));
        System.out.println("Part 2 : " + part2(allDir, root.getSize()));
    }

    private static int part1(List<Dir> lst) {
        // this is needed to remove all duplicates from the list
        // declaring allDir as a Set in the beginning breaks everything :)
        lst = new ArrayList<>(new HashSet<>(lst));
        int sum = 0;
        for (Dir d : lst) {
            if (d.getSize() <= 100000) {
                sum += d.getSize();
            }
        }
        return sum;
    }

    private static int part2(List<Dir> lst, int rootSize) {
        int min = rootSize;
        for (Dir d : lst) {
            int freeSpace = rootSize - d.getSize();
            if (freeSpace < 40000000 && d.getSize() < min)
                min = d.getSize();
        }
        return min;
    }
}