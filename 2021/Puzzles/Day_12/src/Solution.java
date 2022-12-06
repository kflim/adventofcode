import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    private static Map<String, List<String>> caveConnections;
    private static boolean hasDuplicateSmallCave = false;

    public static void investigatePaths() {
        setupData();
        findAllPaths();
    }

    private static void setupData() {
        caveConnections = new HashMap<>();
        String currConnection;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currConnection = br.readLine()) != null) {
                String[] connectedCaves = currConnection.split("-");
                if (!caveConnections.containsKey(connectedCaves[0])) {
                    caveConnections.put(connectedCaves[0], new ArrayList<>());
                }
                if (!caveConnections.containsKey(connectedCaves[1])) {
                    caveConnections.put(connectedCaves[1], new ArrayList<>());
                }
                caveConnections.get(connectedCaves[0]).add(connectedCaves[1]);
                caveConnections.get(connectedCaves[1]).add(connectedCaves[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findAllPaths() {
        List<List<String>> allPaths = new ArrayList<>();
        List<String> currPath = new ArrayList<>();
        currPath.add("start");
        BFS(allPaths, currPath);
        System.out.println("Total possible paths: " + allPaths.size());
    }

    private static void BFS(List<List<String>> allPaths, List<String> currPath) {
        if (currPath.contains("end")) {
            allPaths.add(new ArrayList<>(currPath));
            return;
        }

        String lastCave = currPath.get(currPath.size() - 1);
        List<String> connectedCaves = caveConnections.get(lastCave);
        for (String cave : connectedCaves) {
            char firstChar = cave.charAt(0);
            if (Character.isLowerCase(firstChar)) {
                if (!currPath.contains(cave)) {
                    currPath.add(cave);
                    BFS(allPaths, currPath);
                    currPath.remove(currPath.size() - 1);
                }
            } else {
                currPath.add(cave);
                BFS(allPaths, currPath);
                currPath.remove(currPath.size() - 1);
            }
        }
    }

    public static void investigatePathsWithDuplicate() {
        setupData();
        findAllPathsWithDuplicateSmallCave();
    }

    private static void findAllPathsWithDuplicateSmallCave() {
        List<List<String>> allPaths = new ArrayList<>();
        List<String> currPath = new ArrayList<>();
        currPath.add("start");
        BFS2(allPaths, currPath);
        System.out.println("Total possible paths: " + allPaths.size());
    }

    private static void BFS2(List<List<String>> allPaths, List<String> currPath) {
        if (currPath.contains("end")) {
            allPaths.add(new ArrayList<>(currPath));
            return;
        }

        String lastCave = currPath.get(currPath.size() - 1);
        List<String> connectedCaves = caveConnections.get(lastCave);
        for (String cave : connectedCaves) {
            if (cave.equals("start")) {
                continue;
            }
            char firstChar = cave.charAt(0);
            if (Character.isLowerCase(firstChar)) {
                if (!currPath.contains(cave)) {
                    currPath.add(cave);
                    BFS2(allPaths, currPath);
                    currPath.remove(currPath.size() - 1);
                } else if (!hasDuplicateSmallCave) {
                    hasDuplicateSmallCave = true;
                    currPath.add(cave);
                    BFS2(allPaths, currPath);
                    currPath.remove(currPath.size() - 1);
                    hasDuplicateSmallCave = false;
                }
            } else {
                currPath.add(cave);
                BFS2(allPaths, currPath);
                currPath.remove(currPath.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        investigatePathsWithDuplicate();
    }
}
