import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        String in = Files.readString(Path.of("input.txt"));
        Scanner s = new Scanner(in);
        String wind = s.nextLine();

        List<Figure> l = new ArrayList<>();

        int stringIndex = 0;
        long tallestPoint = 0;
        for (int i = 0; i < 2022; i++) {
             if (l.size() == 100)
                 l = l.subList(50, l.size());
             
            Figure f = new Figure(i % 5, tallestPoint);
            l.add(f);
            //print(l, '@');
            boolean grounded = false;
            while (!grounded) {
                int offset = (wind.charAt(stringIndex++ % wind.length()) == '>') ? 1 : -1;
                f.moveHorizontal(offset);
                for (Figure figure : l)
                    if (f.collides(figure))
                        f.moveHorizontal(-offset);

                f.moveVertical(-1);
                for (Figure figure : l) {
                    if (f.collides(figure)) {
                        f.moveVertical(+1);
                        grounded = true;
                        tallestPoint = (f.tallestPoint() > tallestPoint) ? f.tallestPoint() : tallestPoint;
                        break;
                    }
                }
            }
        }
       // print(l, '#');
        System.out.println(tallestPoint);
    }

    public static int getPatternID(List<Figure> l, int stringIndex){
        StringBuilder sb = new StringBuilder();
        for (Figure f : l) 
            sb.append(f.toString());
        sb.append(stringIndex);
        return sb.toString().hashCode();
         
    }

    public static void print(List<Figure> l, char c) {
        for (int i = 10; i >=0; i--) {
            System.out.println();
            for (int j = 0; j < 9; j++) {
                if (j == 0 || j == 8)
                    System.out.print("|");
                else {
                    boolean drawn = false;
                    for (Figure f : l) {
                        if (f.occupies(j, i)) {
                            System.out.print(c);
                            drawn = true;
                        }
                    }
                    if (!drawn)
                        System.out.print(".");
                }
            }
        }
        System.out.println();
    }
}