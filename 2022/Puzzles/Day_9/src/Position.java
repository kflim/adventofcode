import java.util.Objects;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean tooFarFrom(Position other) {
        int otherX = other.getX();
        int otherY = other.getY();
        return Util.absInt(this.x - otherX) >= 2 || Util.absInt(this.y - otherY) >= 2;
    }

    public void changePos(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getDiff(Position other) {
        return new int[]{getX() - other.getX(), getY() - other.getY()};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{" + getX() + ", " + getY() + "}";
    }

    @Override
    public int compareTo(Position other) {
        if (getX() == other.getX()) {
            return Integer.compare(getY(), other.getY());
        }
        return Integer.compare(getX(), other.getX());
    }
}
