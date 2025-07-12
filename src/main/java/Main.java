import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Cube c = new Cube(Cube.SOLVED_STATE);
        System.out.println(Arrays.toString(c.rotateFrontClockwise().state));
    }

}
