

import java.util.*;

public class Searches {
  
    private static final int HASH_LIMIT = 3;
    private static final int BFS_DLS_LIMIT = 5;

  
    private static Map<Integer, List<CubeState>> hashMap;
    private static Set<Cube> visitedFront;
    private static Set<Cube> visitedBack;

    private static final Set<String> NO_RESULT_STRINGS = Set.of("SOLUTION NOT FOUND", "NO MATCHES", "NO SOLUTION FOR THIS PATH");

    /**
     * Ponto de entrada principal. Executa o algoritmo de forma sequencial
     */
    public static String solveCube(CubeState scrambled) {
        System.out.println("Iniciando busca sequencial...");

      
        hashMap = new HashMap<>();
        visitedFront = new HashSet<>();
        visitedBack = new HashSet<>();

        // --- FASE 1: Gerar o HashMap ---
        CubeState solvedState = new CubeState(new Cube(Cube.SOLVED_STATE));
        generateHashMap(solvedState);
        System.out.println("HashMap gerado com " + hashMap.size() + " chaves de custo.");

        // --- FASE 2: Busca Frontal e DLS ---
        String result = frontSearch(scrambled);
        
        System.out.println("Busca finalizada.");
        return result;
    }

    /**
     * FASE 1: Gera o mapa de estados a partir do cubo resolvido usando BFS.
     */
    private static void generateHashMap(CubeState startNode) {
        ArrayDeque<CubeState> queue = new ArrayDeque<>();
        queue.add(startNode);
        visitedBack.add(startNode.current);

        while (!queue.isEmpty()) {
            CubeState current = queue.remove(); 
            System.out.println("Processando Profundidade: " + current.getDepth());
           

            // Adiciona o estado atual ao HashMap antes de verificar a profundidade
            int cost = current.calculateCost(); 
            hashMap.computeIfAbsent(cost, k -> new LinkedList<>()).add(current);

            if (current.getDepth() >= HASH_LIMIT) {
                continue;
            }

            for (Moves move : Moves.values()) {
                if (current.isLastMoveSameType(move)) continue;

                CubeState newState = current.applyMove(move.value);
                if (visitedBack.add(newState.current)) { 
                    queue.add(newState);
                }
            }
        }
    }

    /**
     * FASE 2: Executa a BFS frontal e, em seguida, a DLS a partir da fronteira.
     */
    private static String frontSearch(CubeState startNode) {
        ArrayDeque<CubeState> queue = new ArrayDeque<>();
        queue.add(startNode);
        visitedFront.add(startNode.current);
        List<CubeState> frontierNodes = new LinkedList<>();

        while (!queue.isEmpty()) {
            CubeState current = queue.remove(); 

            if (current.totalCost == 0) return current.path;

            if (current.getDepth() == BFS_DLS_LIMIT) {
                frontierNodes.add(current);
                continue;
            }
            if (current.getDepth() > BFS_DLS_LIMIT) continue;

            for (Moves move : Moves.values()) {
                if (current.isLastMoveSameType(move)) continue;
                CubeState newState = current.applyMove(move.value);
                if (visitedFront.add(newState.current)) {
                    queue.add(newState);
                }
            }
        }

        System.out.println(frontierNodes.size() + " nós na fronteira para DLS sequencial...");
        // FASE 3: DLS sequencial
        for (CubeState dlsNode : frontierNodes) {
            String result = limitDFS(dlsNode, BFS_DLS_LIMIT);
            if (!NO_RESULT_STRINGS.contains(result)) {
                return result;
            }
        }
        return "SOLUTION NOT FOUND";
    }

    /**
     * FASE 3: Busca em Profundidade Limitada recursiva.
     */
    private static String limitDFS(CubeState currentNode, int remainingDepth) {
        if (remainingDepth == 0) {
            int frontCost = currentNode.totalCost;
            if (hashMap.containsKey(frontCost)) {
                for (CubeState backState : hashMap.get(frontCost)) {
                    
                    if (Arrays.equals(currentNode.faceCost, backState.faceCost)) {
                        
                        return currentNode.path + getBackwardsPath(backState.path);
                    }
                }
            }
            return "NO MATCHES";
        }

        for (Moves move : Moves.values()) {
            if (currentNode.isLastMoveSameType(move)) continue;
            CubeState newState = currentNode.applyMove(move.value);
  
            String result = limitDFS(newState, remainingDepth - 1);
            if (!NO_RESULT_STRINGS.contains(result)) {
                return result;
            }
        }
        return "NO SOLUTION FOR THIS PATH";
    }


    public static String getBackwardsPath(String path) {
        if (path == null || path.isEmpty()) return "";
        
        List<String> moves = new ArrayList<>();
        String tempPath = path;
        while (!tempPath.isEmpty()) {
            String move = tempPath.substring(0, 1);
            tempPath = tempPath.substring(1);
            if (!tempPath.isEmpty() && (tempPath.charAt(0) == '\'' || tempPath.charAt(0) == '2')) {
                move += tempPath.charAt(0);
                tempPath = tempPath.substring(1);
            }
            moves.add(move);
        }

        StringBuilder invertedPath = new StringBuilder();
        for (int i = moves.size() - 1; i >= 0; i--) {
            String move = moves.get(i);
            switch (move) {
                case "F": invertedPath.append("F'"); break;
                case "F'": invertedPath.append("F"); break;
                case "U": invertedPath.append("U'"); break;
                case "U'": invertedPath.append("U"); break;
                case "L": invertedPath.append("L'"); break;
                case "L'": invertedPath.append("L"); break;
                case "R": invertedPath.append("R'"); break;
                case "R'": invertedPath.append("R"); break;
                case "D": invertedPath.append("D'"); break;
                case "D'": invertedPath.append("D"); break;
                case "B": invertedPath.append("B'"); break;
                case "B'": invertedPath.append("B"); break;
                default: invertedPath.append(move); break; 
            }
        }
        return invertedPath.toString();
    }
}