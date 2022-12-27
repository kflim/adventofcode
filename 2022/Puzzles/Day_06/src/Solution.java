public class Solution {

    public static void solvePartOne() {
        String signal = Util.readLine("input.txt");
        int firstMarker = Util.findFirstSectionMarker(signal, 4);
        System.out.println("The first start-of-packet marker is : " + firstMarker);
    }

    public static void solvePartTwo() {
        String signal = Util.readLine("input.txt");
        int firstMarker = Util.findFirstSectionMarker(signal, 14);
        System.out.println("The first start-of-message marker is : " + firstMarker);
    }

    public static void main(String[] args) {
        solvePartOne();
        solvePartTwo();
    }
}
