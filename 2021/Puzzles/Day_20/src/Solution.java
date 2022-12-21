import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void solvePartOne() {
        List<String> allLines = Util.readAllLines("input.txt");
        String algorithm = allLines.get(0);
        List<String> image = allLines.subList(2, allLines.size());

        image = enhanceImage(image, algorithm, 2);
        int litPixels = getLitPixelsCount(image);

        System.out.println("The number of lit pixels is: " + litPixels);
    }

    private static List<String> enhanceImage(List<String> image, String algorithm, int rounds) {
        for (int i = 0; i < rounds; i++) {
            extendRows(image);
            addBlankRows(image);
            image = applyAlgorithm(image, algorithm);
        }
        return image;
    }

    private static void extendRows(List<String> image) {
        for (int i = 0; i < image.size(); i++) {
            image.set(i, ".." + image.get(i) + "..");
        }
    }

    private static void addBlankRows(List<String> image) {
        int rowLength = image.get(0).length();

        for (int i = 0; i < 2; i++) {
            image.add(0, ".".repeat(rowLength));
            image.add(".".repeat(rowLength));
        }
    }

    /**
     * This method assumes that the image used has already been extended to fit extra rows.
     * It then applies the algorithm one time on the image to change the image.
     *
     * @param inputImage Extended input image to ease use of algorithm.
     * @param algorithm Algorithm to determine outcome of pixels.
     */
    private static List<String> applyAlgorithm(List<String> inputImage, String algorithm) {
        int imageLength = inputImage.size();
        int rowLength = inputImage.get(0).length();
        List<String> outputImage = new ArrayList<>();
        StringBuilder nextRow = new StringBuilder();
        StringBuilder binaryPixels = new StringBuilder();

        for (int i = 1; i < imageLength - 1; i++) {
            nextRow.setLength(0);
            for (int j = 1; j < rowLength - 1; j++) {
                binaryPixels.setLength(0);
                binaryPixels.append(inputImage.get(i - 1), j - 1, j + 2);
                binaryPixels.append(inputImage.get(i), j - 1, j + 2);
                binaryPixels.append(inputImage.get(i + 1), j - 1, j + 2);
                int parsedNumber = parsePixels(binaryPixels.toString());
                char pixel = algorithm.charAt(parsedNumber);
                nextRow.append(pixel);
            }
            outputImage.add(nextRow.toString());
        }

        return outputImage;
    }

    private static int parsePixels(String binaryPixels) {
        String binaryString = binaryPixels.replace('.', '0');
        binaryString = binaryString.replace('#', '1');
        return Integer.parseInt(binaryString, 2);
    }

    private static int getLitPixelsCount(List<String> image) {
        int count = 0;
        for (String row : image) {
            for (char pixel : row.toCharArray()) {
                if (pixel == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    public static void solvePartTwo() {
        List<String> allLines = Util.readAllLines("input.txt");
        String algorithm = allLines.get(0);
        List<String> image = allLines.subList(2, allLines.size());

        image = enhanceImage(image, algorithm, 50);
        int litPixels = getLitPixelsCount(image);

        System.out.println("The number of lit pixels is: " + litPixels);
    }

    public static void main(String[] args) {
        solvePartOne();
    }
}
