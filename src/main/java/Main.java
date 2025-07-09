import java.util.Arrays;

public class Main {
    private final int limits = 5;

    public static void main(String[] args) {
        Cube c = new Cube(Cube.SOLVED_STATE);
        System.out.println(Arrays.toString(c.rotateBackClockwise().state));
    }

}
