import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Searches {
    private static final int limits = 5;
    static ConcurrentHashMap<Integer, List<CubeState>> hashMap = new ConcurrentHashMap<>();
    static HashSet<Cube> visitedFront = new HashSet<>();
    static HashSet<Cube> visitedBack = new HashSet<>();
    static final HashSet<String> noResultStrings =  new HashSet<>(Set.of("SOLUTION NOT FOUND","NO MATCHES","NO SOLUTION FOR THIS PATH"));
    public static String solveCubeParallelBidirectional(CubeState scrambled) {
        CubeState solvedState = new CubeState(new Cube(Cube.SOLVED_STATE));
        generateHashMap(solvedState);
        return frontSearch(scrambled);
    }

    private static String frontSearch(CubeState cubeState) {
        ArrayDeque<CubeState> queueBFS = new ArrayDeque<>();
        queueBFS.push(cubeState);
        visitedFront.add(cubeState.current);
        LinkedList<CubeState> frontierNodesDLS = new LinkedList<>();
        while (!queueBFS.isEmpty()) {
            int currentDepth;
            CubeState currentState = queueBFS.pop();
            if (currentState.totalCost == 0) {
                return currentState.path;
            }
            currentDepth = currentState.getDepth();
            if (currentDepth == limits) {
                frontierNodesDLS.push(currentState);
                continue;
            }
            if (currentDepth > limits) {
                continue;
            }
            for (Moves moves : Moves.values()) {
                CubeState newState = currentState.applyMove(moves.toString());
                if (!visitedFront.contains(newState.current)) {
                    visitedFront.add(newState.current);
                    queueBFS.push(newState);
                }
            }
        }
        for (CubeState dlsNode : frontierNodesDLS) {
            String result = limitDFS(dlsNode,limits);
            if (!noResultStrings.contains(result)) return result;
        }
        return "SOLUTION NOT FOUND";
    }

    public static void generateHashMap(CubeState cubeState  ) {
        ArrayDeque<CubeState> queueBFS = new ArrayDeque<>();
        queueBFS.push(cubeState);
        visitedBack.add(cubeState.current);
        while (!queueBFS.isEmpty()) {
            int cost, currentDepth;
            CubeState currentCubeState = queueBFS.pop(); //remove o primeiro elemento da fila
            currentDepth = currentCubeState.getDepth();
            if (currentDepth > limits) continue; //supondo que e o continue de iteracao mesmo
            cost = cubeState.calculateCost();
            if(!hashMap.containsKey(cost)) hashMap.put(cost, new LinkedList<CubeState>());
            hashMap.get(cost).add(currentCubeState);
            for (Moves moves : Moves.values()) {
                CubeState newState = currentCubeState.applyMove(moves.toString());
                if (!visitedBack.contains(newState.current)) {
                    visitedBack.add(newState.current);
                    queueBFS.push(newState);
                }
            }
        }
    }


    public static String limitDFS(CubeState currentState, int remainingDepth) {
        int frontCost = currentState.totalCost;
        StringBuilder fullPath =  new StringBuilder();
        String result;
        if (remainingDepth == 0) {
            if(hashMap.containsKey(frontCost)) {
                for (CubeState backState : hashMap.get(frontCost)) {
                    if (currentState.faceCost == backState.faceCost) {
                        fullPath.append(currentState.path);
                        fullPath.append(backState.getBackwardsPath());
                        return fullPath.toString();
                    }
                }
            }
            else {
                return "NO MATCHES";
            }
        }
        for (Moves moves : Moves.values()) {
            CubeState newState = currentState.applyMove(moves.toString());
            result = limitDFS(newState,remainingDepth-1);
            if (!noResultStrings.contains(result)) {
                return result;
            }
        }
        return "NO SOLUTION FOR THIS PATH";
    }
}
