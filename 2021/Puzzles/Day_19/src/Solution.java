import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    public static void solvePartOne() {
        List<Scanner> scanners = Util.readScanners("input.txt");
        Set<Coordinate> beaconCoordinates = new HashSet<>();
        findAllBeaconCoordinates(scanners, beaconCoordinates);
        System.out.println("Total number of beacons is: " + beaconCoordinates.size());
    }

    public static void solvePartTwo() {
        List<Scanner> scanners = Util.readScanners("input.txt");
        int ans = getLargestManhattanDist(scanners);
        System.out.println("Largest Manhattan distance is: " + ans);
    }

    private static void findAllBeaconCoordinates(List<Scanner> scanners,
                                                    Set<Coordinate> beaconCoordinates) {
        ArrayDeque<Scanner> toCompareScanners = new ArrayDeque<>();
        List<Scanner> comparedToScanners = new ArrayList<>(scanners);
        Scanner scanner0 = scanners.get(0);
        toCompareScanners.offer(scanner0);
        comparedToScanners.removeAll(toCompareScanners);

        while (!toCompareScanners.isEmpty()) {
            Scanner toCompareScanner = toCompareScanners.poll();
            for (int i = 0; i < comparedToScanners.size(); i++) {
                Scanner currScanner = comparedToScanners.get(i);
                Scanner compareRes = toCompareScanner.compareBeacons(currScanner);
                if (compareRes != null) {
                    scanners.remove(currScanner);
                    scanners.add(compareRes);
                    toCompareScanners.offer(compareRes);
                    comparedToScanners.set(i, compareRes);
                }
            }
            comparedToScanners.removeAll(toCompareScanners);
        }

        scanners.forEach(s -> beaconCoordinates.addAll(s.getBeacons()));
    }

    private static int getLargestManhattanDist(List<Scanner> scanners) {
        ArrayDeque<Scanner> toCompareScanners = new ArrayDeque<>();
        List<Scanner> comparedToScanners = new ArrayList<>(scanners);
        Scanner scanner0 = scanners.get(0);
        toCompareScanners.offer(scanner0);
        comparedToScanners.removeAll(toCompareScanners);

        while (!toCompareScanners.isEmpty()) {
            Scanner toCompareScanner = toCompareScanners.poll();
            for (int i = 0; i < comparedToScanners.size(); i++) {
                Scanner currScanner = comparedToScanners.get(i);
                Scanner compareRes = toCompareScanner.compareBeacons(currScanner);
                if (compareRes != null) {
                    scanners.remove(currScanner);
                    scanners.add(compareRes);
                    toCompareScanners.offer(compareRes);
                    comparedToScanners.set(i, compareRes);
                }
            }
            comparedToScanners.removeAll(toCompareScanners);
        }

        int largestDist = Integer.MIN_VALUE;
        for (int i = 0; i < scanners.size(); i++) {
            Scanner first = scanners.get(i);
            for (int j = i + 1; j < scanners.size(); j++) {
                Scanner second = scanners.get(j);
                largestDist = Math.max(largestDist, first.getManhattanDist(second));
            }
        }

        return largestDist;
    }

    public static void main(String[] args) {
        // solvePartOne();
        solvePartTwo();
    }
}
