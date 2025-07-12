import java.util.HashSet;
import java.util.Set;

public class CubeState {
    Cube current;
    String path;
    int totalCost;
    int[] faceCost = new int[6]; //forçando a ser limitado

    //Vou deixar essa pra voce implementar, usa pra calcular pro proprio objeto pra funcionar com o que eu ja fiz pra searches
    public static int[] calculateFaceCost() {
        return null;
    }
    //Esse daqui tambem
    public int calculateCost() {
        return 0;
    }

    private static final HashSet<Character> modifiers = new HashSet<>(Set.of('\'','2'));

    public CubeState(Cube current, String path, int totalCost, int[] faceCost) {
        this.current = current;
        this.path = path;
        this.totalCost = totalCost;
        this.faceCost = faceCost;
    }

    public CubeState(Cube current) {
        this.current = current;
        this.path = "";
        this.totalCost = 0;
        this.faceCost = calculateFaceCost();
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

}
