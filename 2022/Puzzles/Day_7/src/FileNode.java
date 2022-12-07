import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FileNode implements Comparable<FileNode> {
    private final String name;
    private final boolean isDir;
    private int size;
    private TreeSet<FileNode> children;
    private FileNode parent;

    public FileNode(String name) {
        this.name = name;
        this.isDir = true;
        this.size = 0;
        this.children = new TreeSet<>();
        this.parent = null;
    }

    public FileNode(String name, int size, FileNode parent) {
        this.name = name;
        this.isDir = false;
        this.size = size;
        this.children = new TreeSet<>();
        this.parent = parent;
    }

    public FileNode(String name, FileNode parent) {
        this.name = name;
        this.isDir = true;
        this.size = 0;
        this.children = new TreeSet<>();
        this.parent = parent;
    }

    public void addChildNode(FileNode childNode) {
        this.children.add(childNode);
    }

    public boolean isDir() {
        return this.isDir;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TreeSet<FileNode> getChildren() {
        return this.children;
    }

    public FileNode getParent() {
        return this.parent;
    }

    public FileNode findChildWithName(String name) {
        List<FileNode> childrenList = new ArrayList<>(getChildren());
        return childrenList.stream()
                           .filter(c -> c.getName().equals(name))
                           .collect(Collectors.toList())
                           .get(0);
    }

    @Override
    public int compareTo(FileNode o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        String parentName = getParent() == null ? null : getParent().getName();
        return "Name: " + getName() + ", isDir: " + isDir()
            + ", Size: " + getSize() + ", Parent: " + parentName
            + System.lineSeparator();
    }
}
