
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Part2 {

    // Messy code, needs some SEVERE refactoring
    public static void main(String[] args) throws IOException {
        String in = Files.readString(Path.of("input.txt"));

        Scanner s = new Scanner(in);
        Dir root = new Dir("/");
        List<String> pwd = new ArrayList<>();
        Set<Dir> allDir = new HashSet<>();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty())
                break;

            String[] tokens = line.split(" ");
            switch (tokens[1]) {
                case "cd":
                    if (tokens[2].equals(".."))
                        pwd.remove(pwd.size() - 1);
                    else
                        pwd.add(tokens[2]);
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
        int sum = 0;
        List<Integer> allSize = new ArrayList<>();
        for (Entry d : root)
            sum += d.getSize();

        for (Dir d : allDir) {
            if (sum - d.getSize() < 40000000)
                allSize.add(d.getSize());
        }
        allSize.sort(null);
        System.out.println(allSize.get(0));
    }
}