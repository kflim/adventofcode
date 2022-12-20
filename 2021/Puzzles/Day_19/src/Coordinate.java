import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

    private int x;
    private int y;
    private int z;

    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate(Coordinate other) {
        this.x = other.getX();
        this.y = other.getY();
        this.z = other.getZ();
    }

    public Coordinate(Coordinate other, String orientation) {
        this(other);
        Pattern coordinatePattern = Pattern.compile("(-?\\w),(-?\\w),(-?\\w)");
        Matcher matcher = coordinatePattern.matcher(orientation);

        if (matcher.matches()) {
            String x = matcher.group(1);
            String y = matcher.group(2);
            String z = matcher.group(3);

            setX(other, x);
            setY(other, y);
            setZ(other, z);
        }
    }

    private void setX(Coordinate other, String x) {
        if (x.charAt(0) == '-') {
            this.x = -other.getCoordinateValue(x.charAt(1));
        } else {
            this.x = other.getCoordinateValue(x.charAt(0));
        }
    }

    private void setY(Coordinate other, String y) {
        if (y.charAt(0) == '-') {
            this.y = -other.getCoordinateValue(y.charAt(1));
        } else {
            this.y = other.getCoordinateValue(y.charAt(0));
        }
    }

    private void setZ(Coordinate other, String z) {
        if (z.charAt(0) == '-') {
            this.z = -other.getCoordinateValue(z.charAt(1));
        } else {
            this.z = other.getCoordinateValue(z.charAt(0));
        }
    }

    private int getCoordinateValue(char c) {
        switch (c) {
        case 'y':
            return y;
        case 'z':
            return z;
        default:
            return x;
        }
    }

    public Coordinate(Coordinate other, Coordinate directionVector) {
        this(other);
        this.x += directionVector.getX();
        this.y += directionVector.getY();
        this.z += directionVector.getZ();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Coordinate getDirectionVector(Coordinate other) {
        int xDiff = this.x - other.getX();
        int yDiff = this.y - other.getY();
        int zDiff = this.z - other.getZ();
        return new Coordinate(xDiff, yDiff, zDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        final Coordinate other = (Coordinate) o;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public String toString() {
        return this.x + "," + this.y + "," + this.z;
    }
}
