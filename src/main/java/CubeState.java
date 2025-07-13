import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CubeState {
    Cube current;
    String path;
    int totalCost;
    int[] faceCost;

    int depth; 

    //Calcula o custo de cada face individualmente, o custo é o numero de adesivos que nao correspondem a cor do centro
    // retorna um array de 6 inteiros onde cada posicao é o custa da face correspondente
     public int[] calculateFaceCost() {
        int[] costs = new int[6];
        byte[] currentState = this.current.state;

        for(int faceIndex = 0; faceIndex < 6; faceIndex++){
            int centerIndex = (faceIndex * 9) + 4; 
            byte centerColor = currentState[centerIndex];
            int misplacedCount = 0;
            for(int i = 0; i < 9; i++){
                int stickerIndex = (faceIndex * 9) + i;
                if(currentState[stickerIndex]!= centerColor){
                    misplacedCount++;
                }
            }
            costs[faceIndex] = misplacedCount;
        }
        return costs;
    }

    //Calcula a soma total de todos os custos de todas as faces, um cubo resolvido tem custo = 0
    public int calculateCost() {
        int[] faceCosts = (this.faceCost != null && this.faceCost.length == 6) ? this.faceCost : calculateFaceCost();
        int total = 0;
        for(int cost : faceCosts){
            total += cost;
        }
        return total;
    }


    private static final HashSet<Character> modifiers = new HashSet<>(Set.of('\'','2'));

    public CubeState(Cube current, String path, int depth) {
        this.current = current;
        this.path = path;
        this.depth = depth;
        this.faceCost = this.calculateFaceCost();
        this.totalCost = this.calculateCost();
    }
    public CubeState(Cube current) {
        this(current, "", 0);
    }

    public int getDepth() {
        return this.depth;
    }

    public CubeState applyMove(String move) {
        Cube cube = this.current.applyMove(move);
        String newPath = this.path + move;
        int newDepth = this.depth + 1;
        return new CubeState(cube, newPath, newDepth);
    }


    public String getBackwardsPath() {
        if (path == null || path.isEmpty()) {
            return "";
        }
        List<String> moves = new ArrayList<>();
        String tempPath = this.path;
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