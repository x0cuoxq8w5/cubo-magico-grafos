import java.util.HashSet;
import java.util.Set;

public class CubeState {
    Cube current;
    String path;
    int totalCost;
    int[] faceCost = new int[6]; //forçando a ser limitado

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
                int stickerIndex = (faceIndex * 9) + i;  // Pula para o início do bloco da face (faceIndex * 9) e pega o adesivo central (+ 4).
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

    public CubeState(Cube current, String path, int totalCost, int[] faceCost) {
        this.current = current;
        this.path = path;
        this.totalCost = this.calculateCost();
        this.faceCost = this.calculateFaceCost();
    }

    public CubeState(Cube current) {
        this.current = current;
        this.path = "";
        this.totalCost = this.calculateCost();
        this.faceCost = this.calculateFaceCost();
    }

    public int getDepth() {
        int counter = 0;
        String workingPath = this.path;
        while (!workingPath.isEmpty()) {
            if(modifiers.contains(workingPath.charAt(1))) {
                workingPath = workingPath.substring(2);
            }
            else {
                workingPath = workingPath.substring(1);
            }
            counter++;
        }
        return counter;
    }

    public CubeState applyMove(String move) {
        Cube cube = this.current.applyMove(move);
        return new CubeState(cube);
    }


    public String getBackwardsPath() {
        StringBuilder backwardsPath =  new StringBuilder();
        String workingPath = this.path;
        while (!workingPath.isEmpty()) {
            if (modifiers.contains(workingPath.charAt(workingPath.length()-1))) {
                backwardsPath.append(workingPath.substring(workingPath.length()-2));
                workingPath = workingPath.substring(0, workingPath.length()-2);
            }
            else {
                backwardsPath.append(workingPath.substring(workingPath.length()-1));
                workingPath = workingPath.substring(0, workingPath.length()-1);
            }
        }
        return backwardsPath.toString();
    }
}
