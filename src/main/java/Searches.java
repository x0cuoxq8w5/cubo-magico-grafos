import java.util.*;
import java.util.concurrent.*;

public class Searches {
    private static final int hashLimit = 3;
    private static final int limits = 5;
    static ConcurrentHashMap<Integer, List<CubeState>> hashMap = new ConcurrentHashMap<>();
    static HashSet<Cube> visitedFront = new HashSet<>();
    static HashSet<Cube> visitedBack = new HashSet<>();
    static final HashSet<String> noResultStrings =  new HashSet<>(Set.of("SOLUTION NOT FOUND","NO MATCHES","NO SOLUTION FOR THIS PATH"));
    static Semaphore semaphore = new Semaphore(1);
    static ExecutorService executorService = Executors.newFixedThreadPool(8); //mudar caso necessario

    public static String solveCubeParallelBidirectional(CubeState scrambled) throws Exception {
        System.out.println("Comecou...");
        CubeState solvedState = new CubeState(new Cube(Cube.SOLVED_STATE));
        GenerateHashMapThread generateHashMapThread = new GenerateHashMapThread(solvedState);
        generateHashMapThread.start();
        FrontSearchThread frontSearchThread = new FrontSearchThread(scrambled);
        String result = frontSearchThread.call();
        if(result != null) {
            System.out.println("RETURN!");
            generateHashMapThread.interrupt();;
        }
        return result;
    }


    static class GenerateHashMapThread extends Thread {
        CubeState cubeState;
        public GenerateHashMapThread(CubeState solvedState) {
            this.cubeState = solvedState;
        }

        @Override
        public void run() {
            generateHashMap();
        }

        public void generateHashMap() {
            System.out.println("Gerando hashmap...");
            semaphore.acquireUninterruptibly();
            System.out.println("Hashmap entrou no semaforo");
            ArrayDeque<CubeState> queueBFS = new ArrayDeque<>();
            queueBFS.add(cubeState);
            visitedBack.add(cubeState.current);
            while (!queueBFS.isEmpty() && !Thread.currentThread().isInterrupted()) {
                int cost, currentDepth;
                CubeState currentCubeState = queueBFS.pop(); //remove o primeiro elemento da fila
                currentDepth = currentCubeState.getDepth();
                if (currentDepth > hashLimit) continue; //supondo que e o continue de iteracao mesmo
                cost = cubeState.calculateCost();
                if(!hashMap.containsKey(cost)) hashMap.put(cost, new LinkedList<CubeState>());
                hashMap.get(cost).add(currentCubeState);
                for (Moves moves : Moves.values()) {
                    if (currentCubeState.isLastMoveSameType(moves)) continue;
                    CubeState newState = currentCubeState.applyMove(moves.value);
                    if (!visitedBack.contains(newState.current)) {
                        visitedBack.add(newState.current);
                        queueBFS.add(newState);
                    }
                }
            }
            semaphore.release();
        }
    }

    static class FrontSearchThread implements Callable<String> {
        CubeState cubeState;

        public FrontSearchThread(CubeState cubeState) {
            this.cubeState = cubeState;
        }

        @Override
        public String call() throws Exception {
            return frontSearch();
        }

        private String frontSearch() throws ExecutionException, InterruptedException {
            System.out.println("Busca frontal...");
            ArrayDeque<CubeState> queueBFS = new ArrayDeque<>();
            queueBFS.add(cubeState);
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
                    frontierNodesDLS.add(currentState);
                    continue;
                }
                if (currentDepth > limits) {
                    continue;
                }
                for (Moves moves : Moves.values()) {
                    if (currentState.isLastMoveSameType(moves)) continue;
                    CubeState newState = currentState.applyMove(moves.value);
                    if (!visitedFront.contains(newState.current)) {
                        visitedFront.add(newState.current);
                        queueBFS.add(newState);
                    }
                }
            }
            semaphore.acquireUninterruptibly();
            System.out.println(frontierNodesDLS.size() + " frontiers da busca frontal");
            for (CubeState dlsNode : frontierNodesDLS) {
                Future<String> result = executorService.submit(new LimitDfsThread(dlsNode, limits));
                //String result = limitDFS(dlsNode,limits);
                if (!noResultStrings.contains(result.get())) return result.get();
            }
            return "SOLUTION NOT FOUND";
        }
    }

    static class LimitDfsThread implements Callable<String> {

        private CubeState currentState = null;
        private final int remainingDepth;

        public LimitDfsThread(CubeState currentState, int remainingDepth) {
            this.currentState = currentState;
            this.remainingDepth = remainingDepth;
        }

        @Override
        public String call() throws Exception {
            return limitDFS();
        }

        public String limitDFS() throws ExecutionException, InterruptedException {
            int frontCost = currentState.totalCost;
            //System.out.println("Busca DFS... Profundidade: " + remainingDepth);
            StringBuilder fullPath =  new StringBuilder();
            if (remainingDepth == 0) {
                if(hashMap.containsKey(frontCost)) {
                    for (CubeState backState : hashMap.get(frontCost)) {
                        if (currentState.faceCost == backState.faceCost) {
                            fullPath.append(currentState.path);
                            fullPath.append(backState.getBackwardsPath());
                            System.out.println("RETURN!!!!!!!");
                            executorService.shutdown();
                            return fullPath.toString();
                        }
                    }
                }
                else {
                    return "NO MATCHES";
                }
            }
            for (Moves moves : Moves.values()) {
                if (currentState.isLastMoveSameType(moves)) continue;
                CubeState newState = currentState.applyMove(moves.value);
                String result = limitDFS(newState,remainingDepth-1);
                if (!noResultStrings.contains(result)) {
                    executorService.shutdown();
                    return result;
                }
            }
            return "NO SOLUTION FOR THIS PATH";
        }

        public String limitDFS(CubeState currentState, int remainingDepth) throws ExecutionException, InterruptedException {
            int frontCost = currentState.totalCost;
            //System.out.println("Busca DFS... Profundidade: " + remainingDepth);
            StringBuilder fullPath =  new StringBuilder();
            if (remainingDepth == 0) {
                if(hashMap.containsKey(frontCost)) {
                    for (CubeState backState : hashMap.get(frontCost)) {
                        if (currentState.faceCost == backState.faceCost) {
                            fullPath.append(currentState.path);
                            fullPath.append(backState.getBackwardsPath());
                            System.out.println("RETURN!!!!!!!");
                            executorService.shutdown();
                            return fullPath.toString();
                        }
                    }
                }
                else {
                    return "NO MATCHES";
                }
            }
            for (Moves moves : Moves.values()) {
                if (currentState.isLastMoveSameType(moves)) continue;
                CubeState newState = currentState.applyMove(moves.value);
                String result = limitDFS(newState,remainingDepth-1);
                //result = limitDFS(newState,remainingDepth-1);
                if (!noResultStrings.contains(result)) {
                    executorService.shutdown();
                    return result;
                }
            }
            return "NO SOLUTION FOR THIS PATH";
        }
    }

}

