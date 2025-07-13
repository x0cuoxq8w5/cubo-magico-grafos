import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws Exception {
        Cube c = new Cube(Cube.SOLVED_STATE);
        CubeState cs = new CubeState(c.rotateBackClockwise().rotateFrontClockwise().rotateRight180().rotateLeft180().rotateUpClockwise().rotateDownClockwise());
        System.out.println(Arrays.toString(cs.current.state));
        String result = Searches.solveCubeParallelBidirectional(cs);
        System.out.println("RESULTADO: " + result);
    }

}
