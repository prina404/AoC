

public class MyFile implements Entry{
    private final int size;
    private final String name;    
    public MyFile(String name, int size){
        this.name = name;
        this.size = size;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getSize() {
        return size;
    }

    
}
