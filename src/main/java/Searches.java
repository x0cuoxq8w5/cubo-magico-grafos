import java.util.*;

public class Searches {
    private static final int limits = 5;
    static HashMap<Integer, List<CubeState>> hashMap = new HashMap<>();
    static HashSet<Cube> visitedFront = new HashSet<>();
    static HashSet<Cube> visitedBack = new HashSet<>();
    public static CubeState solveCubeParallelBidirectional(CubeState scrambled) {
        CubeState solvedState = new CubeState(new Cube(Cube.SOLVED_STATE));
        generateHashMap(solvedState);
        return frontSearch(scrambled);
    }

    private static CubeState frontSearch(CubeState cubeState) {
        return null;
    }

    public static CubeState generateHashMap(CubeState cubeState  ) {
        ArrayDeque<CubeState> queueBFS = new ArrayDeque<>();
        queueBFS.push(cubeState);
        visitedFront.add(cubeState.current);
        LinkedList<CubeState> frontierNodesDLS = new LinkedList<>();
        while (!queueBFS.isEmpty()) {
            int cost, currentDepth;
            CubeState currentCubeState = queueBFS.pop(); //remove o primeiro elemento da fila
            currentDepth = currentCubeState.getDepth();
            if (currentDepth == limits) {
                frontierNodesDLS.add(currentCubeState);
                continue;
            }
            if (currentDepth > limits) continue; //supondo que e o continue de iteracao mesmo
            cost = cubeState.calculateCost();
            if(!hashMap.containsKey(cost)) hashMap.put(cost, new LinkedList<CubeState>());
            hashMap.get(cost).add(currentCubeState);
            for (Moves moves : Moves.values()) {
                CubeState newState = currentCubeState.applyMove(moves.toString());
                if (!visitedFront.contains(newState.current)) {
                    visitedFront.add(newState.current);
                    queueBFS.push(newState);
                }
            }
        }
        //supondo que isso e fora do while
        for (CubeState dlsNode : frontierNodesDLS) {
            CubeState result = limitDFS(dlsNode,limits);
            if (result.current.state == Cube.SOLVED_STATE) return result;
        }
        return null;
    }


    public static CubeState limitDFS(CubeState currentState, int remainingDepth) {
        /*if (remainingDepth == 0) {
            currentState.faceCost[Faces.FRONT.value]
        }*/
        return null;
    }
}
