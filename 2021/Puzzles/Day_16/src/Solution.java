import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solution {
    private static String binPacket;
    private static final String[] hexToBinLookup = new String[]{
        "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
        "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
    };

    private static class Packet {
        int versionSum;
        long literalValue;
        int endIndex;

        Packet(int versionSum, long literalValue, int endIndex) {
            this.versionSum = versionSum;
            this.literalValue = literalValue;
            this.endIndex = endIndex;
        }
    }

    public static void solve() {
        setupData();
        decodePacket();
    }

    private static void setupData() {
        StringBuilder sb = new StringBuilder();
        String hexString;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            hexString = br.readLine();

            for (int i = 0; i < hexString.length(); i++) {
                char currChar = hexString.charAt(i);
                String binString = hexToBin(currChar);
                sb.append(binString);
            }

            binPacket = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String hexToBin(char c) {
        int decimalForm = Integer.parseInt(Character.toString(c), 16);
        return hexToBinLookup[decimalForm];
    }

    private static void decodePacket() {
        Packet packet = parsePacket(0);
        System.out.println("Total version sum: " + packet.versionSum);
        System.out.println("Value of outermost packet: " + packet.literalValue);
    }

    private static Packet parsePacket(int startingIndex) {
        int packetTypeId = getTypeId(startingIndex);

        if (packetTypeId == 4) {
            return parseLiteralValuePacket(startingIndex);
        } else {
            return parseOperatorPacket(startingIndex, packetTypeId);
        }
    }

    private static Packet parseLiteralValuePacket(int startingIndex) {
        StringBuilder sb = new StringBuilder();
        int endingIndex = 0;

        for (int i = 6; i < binPacket.length(); i += 5) {
            char prefixBit = binPacket.charAt(startingIndex + i);
            String literalValue = binPacket.substring(startingIndex + i + 1, startingIndex + i + 5);
            sb.append(literalValue);
            if (prefixBit == '0') {
                endingIndex = startingIndex + i + 4;
                break;
            }
        }

        int version = getVersion(startingIndex);
        long totalLiteralValue = Long.parseLong(sb.toString(), 2);
        return new Packet(version, totalLiteralValue, endingIndex);
    }

    private static Packet parseOperatorPacket(int startingIndex, int packetTypeId) {
        int lengthTypeId = getLengthTypeId(startingIndex);
        int version = getVersion(startingIndex);
        if (lengthTypeId == 0) {
            return parseSubPacketsByLength(startingIndex, packetTypeId, version);
        } else {
            return parseSubPacketsByCount(startingIndex, packetTypeId, version);
        }
    }

    private static int getTypeId(int startingIndex) {
        String binTypeId = binPacket.substring(startingIndex + 3, startingIndex + 6);
        return Integer.parseInt(binTypeId, 2);
    }

    private static int getVersion(int startingIndex) {
        String binTypeId = binPacket.substring(startingIndex, startingIndex + 3);
        return Integer.parseInt(binTypeId, 2);
    }

    private static int getLengthTypeId(int startingIndex) {
        char lengthTypeId = binPacket.charAt(startingIndex + 6);
        return Integer.parseInt(Character.toString(lengthTypeId));
    }

    private static Packet parseSubPacketsByLength(int startingIndex, int packetTypeId, int version) {
        String lengthBits = binPacket.substring(startingIndex + 8, startingIndex + 22);
        int lengthOfPackets = Integer.parseInt(lengthBits, 2);
        int currLength = 0;
        int versionSum = version;
        int endIndex = 0;
        List<Long> packetValues = new ArrayList<>();

        while (currLength < lengthOfPackets) {
            int currOffset = startingIndex + 22 + currLength;
            Packet currPacket = parsePacket(currOffset);
            int currPacketLength = currPacket.endIndex - currOffset + 1;
            versionSum += currPacket.versionSum;
            endIndex = currPacket.endIndex;
            currLength += currPacketLength;
            packetValues.add(currPacket.literalValue);
        }

        long packetValue = calculateValue(packetTypeId, packetValues);
        return new Packet(versionSum, packetValue, endIndex);
    }

    private static Packet parseSubPacketsByCount(int startingIndex, int packetTypeId, int version) {
        String lengthBits = binPacket.substring(startingIndex + 8, startingIndex + 18);
        int packetCount = Integer.parseInt(lengthBits, 2);
        int currLength = 0;
        int versionSum = version;
        int endIndex = 0;
        List<Long> packetValues = new ArrayList<>();

        while (packetCount > 0) {
            int currOffset = startingIndex + 18 + currLength;
            Packet currPacket = parsePacket(currOffset);
            int currPacketLength = currPacket.endIndex - currOffset + 1;
            versionSum += currPacket.versionSum;
            endIndex = currPacket.endIndex;
            currLength += currPacketLength;
            packetValues.add(currPacket.literalValue);

            packetCount--;
        }

        long packetValue = calculateValue(packetTypeId, packetValues);
        return new Packet(versionSum, packetValue, endIndex);
    }

    private static long calculateValue(int packetTypeId, List<Long> packetValues) {
        long totalValue = 0;
        switch (packetTypeId) {
        case 0:
            totalValue = packetValues.stream().reduce(0L, Long::sum);
            break;
        case 1:
            totalValue = packetValues.stream().reduce(1L, (a, b) -> a * b);
            break;
        case 2:
            totalValue = packetValues.stream().reduce(Long.MAX_VALUE, Math::min);
            break;
        case 3:
            totalValue = packetValues.stream().reduce(Long.MIN_VALUE, Math::max);
            break;
        case 5:
            totalValue = packetValues.get(0) > packetValues.get(1) ? 1 : 0;
            break;
        case 6:
            totalValue = packetValues.get(0) < packetValues.get(1) ? 1 : 0;
            break;
        case 7:
            totalValue = Objects.equals(packetValues.get(0), packetValues.get(1)) ? 1 : 0;
            break;
        default:
            break;
        }

        return totalValue;
    }

    public static void main(String[] args) {
        solve();
    }
}
