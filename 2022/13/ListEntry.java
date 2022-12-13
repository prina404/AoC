
import java.util.List;

/**
 * ListEntry
 */
public class ListEntry extends Entry {

    public ListEntry(List<Entry> l) {
        super.elems = l;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Entry entry : elems) {
            sb.append(entry.toString()).append(",");
        }
        if (sb.length() >1)
            sb.setCharAt(sb.length()-1, ']');
        else
            sb.append("]");
        return sb.toString();
    }

}