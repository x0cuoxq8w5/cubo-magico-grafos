import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws Exception {
        Cube c = new Cube(Cube.SOLVED_STATE);
        CubeState cs = new CubeState(c.rotateFrontClockwise().rotateUpAntiClockwise().rotateFrontAntiClockwise().rotateDownAntiClockwise().rotateFrontAntiClockwise().rotateUpAntiClockwise().rotateFrontAntiClockwise());
        //FU'F'U'F'U'F'
        //FUFUFUF'
        String result = Searches.solveCubeParallelBidirectional(cs);
        System.out.println("RESULTADO: " + result);
    }

}
