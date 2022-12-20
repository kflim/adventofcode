import java.util.ArrayList;
import java.util.List;

public class Scanner {

    private List<Coordinate> beacons;
    private Coordinate relativePos;

    public Scanner(List<Coordinate> beacons) {
        this.beacons = beacons;
        this.relativePos = new Coordinate(0, 0, 0);
    }

    Scanner(Scanner obj, String order) {
        beacons = new ArrayList<>();
        for (Coordinate c : obj.beacons) {
            beacons.add(new Coordinate(c, order));
        }
        relativePos = new Coordinate(0, 0, 0);
    }

    public Scanner(Scanner original, Coordinate directionVector) {
        this.beacons = new ArrayList<>();
        for (Coordinate c : original.getBeacons()) {
            this.beacons.add(new Coordinate(c, directionVector));
        }
        this.relativePos = new Coordinate(0, 0, 0);
    }

    public List<Coordinate> getBeacons() {
        return new ArrayList<>(beacons);
    }

    public void setRelativePos(Coordinate c) {
        this.relativePos = c;
    }

    public Scanner compareBeacons(Scanner other) {
        List<String> orders = Util.orientations;

        for (String order : orders) {
            Scanner rotatedOther = new Scanner(other, order);
            for (Coordinate beacon : beacons) {
                Scanner possibleScanner = findPossibleScanner(other, rotatedOther, beacon);
                if (possibleScanner != null) {
                    return possibleScanner;
                }
            }
        }

        return null;
    }

    private Scanner findPossibleScanner(Scanner other, Scanner rotatedOther, Coordinate beacon) {
        List<Coordinate> otherBeacons = other.getBeacons();
        List<Coordinate> rotatedOtherBeacons = rotatedOther.getBeacons();

        for (int i = 0; i < otherBeacons.size(); i++) {
            Coordinate rotatedOtherBeacon = rotatedOtherBeacons.get(i);
            Coordinate directionVector = beacon.getDirectionVector(rotatedOtherBeacon);
            Scanner possibleScanner = new Scanner(rotatedOther, directionVector);
            if (matchesMinBeaconsNeeded(possibleScanner)) {
                possibleScanner.setRelativePos(directionVector);
                return possibleScanner;
            }
        }

        return null;
    }

    private boolean matchesMinBeaconsNeeded(Scanner obj) {
        List<Coordinate> intersection = new ArrayList<>();

        for (int i = 0; i < obj.beacons.size(); i++) {
            if (beacons.contains(obj.beacons.get(i))) {
                intersection.add(obj.beacons.get(i));
            }
        }

        return intersection.size() >= Util.MIN_BEACONS_NEEDED;
    }

    public int getManhattanDist(Scanner other) {
        int xDiff = Util.abs(this.relativePos.getX() - other.relativePos.getX());
        int yDiff = Util.abs(this.relativePos.getY() - other.relativePos.getY());
        int zDiff = Util.abs(this.relativePos.getZ() - other.relativePos.getZ());
        return xDiff + yDiff + zDiff;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Coordinate c : this.beacons) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
