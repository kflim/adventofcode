public class Node implements Comparable<Node> {
    private final int x;
    private final int y;
    private final int risk;

    public Node(int x, int y, int risk) {
        this.x = x;
        this.y = y;
        this.risk = risk;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRisk() {
        return risk;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(risk, o.getRisk());
    }
}
