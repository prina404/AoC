
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Part2 {

    // Messy code, needs some SEVERE refactoring
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));

        Dir root = new Dir("/");
        Deque<String> pwd = new LinkedList<>();
        Set<Dir> allDir = new HashSet<>();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty())
                break;

            String[] tokens = line.split(" ");
            switch (tokens[1]) {
                case "cd":
                    if (tokens[2].equals(".."))
                        pwd.removeLast();
                    else
                        pwd.addLast(tokens[2]);
                    continue;
                case "ls":
                    continue;
                default:
                    Dir current = root.findDir(pwd.toArray(new String[0]));
                    if (tokens[0].equals("dir")) {
                        Dir nuovaFolder = new Dir(tokens[1]);
                        current.addElem(nuovaFolder);
                        allDir.add(nuovaFolder);
                    } else
                        current.addElem(new MyFile(tokens[1], Integer.parseInt(tokens[0])));
            }
        }

        int min = root.getSize();
        for (Dir d : allDir) {
            int freeSpace = root.getSize() - d.getSize();
            if (freeSpace < 40000000 && d.getSize() < min)
                min = d.getSize();
        }
        System.out.println(min);
    }
}