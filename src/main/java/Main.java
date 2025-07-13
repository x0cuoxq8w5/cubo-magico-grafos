import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws Exception {
        Cube c = new Cube(Cube.SOLVED_STATE);
        CubeState cs = new CubeState(c.rotateUpAntiClockwise().rotateFrontAntiClockwise().rotateUpAntiClockwise().rotateFrontAntiClockwise().rotateUpAntiClockwise().rotateFrontAntiClockwise());
        String result = Searches.solveCubeParallelBidirectional(cs);
        System.out.println("RESULTADO: " + result);
    }

}
