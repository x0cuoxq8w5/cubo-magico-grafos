import java.util.Arrays;

public class Cube {
    //Note: Divisao inteira por 9 pra achar a face, quadrados indexados por ordem de leitura.
    public byte[] state = new byte[45];
    public final static byte[] solvedState = {
            0,0,0, //topo
            0,0,0,
            0,0,0,
            1,1,1, //esquerda
            1,1,1,
            1,1,1,
            2,2,2, //frente
            2,2,2,
            2,2,2,
            3,3,3, //baixo
            3,3,3,
            3,3,3,
            4,4,4, //direita
            4,4,4,
            4,4,4,
            5,5,5, //tras
            5,5,5,
            5,5,5};
    private final static int dimension = 3;
    private void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }
    private final static byte[][] adjacents = {{5,4,2,1},{0,2,3,5},{0,4,3,1},{5,1,2,4},{0,5,3,2},{0,1,3,4}};
    // 0 = 5,4,2,1
    // 1 = 0,2,3,5
    // 2 = 0,4,3,1
    // 3 = 5,1,2,4
    // 4 = 0,5,3,2
    // 5 = 0,1,3,4

    public Cube(byte[] state) {
        this.state = state;
    }
    public void rotateFace(int face, int times) {
        System.out.println(Arrays.toString(state));
        byte[] temp = Arrays.copyOfRange(state,face*9,face*9+9);
        System.out.println(Arrays.toString(temp));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[(j*dimension)+(dimension-1-i)] = temp[i*dimension+j];
            }
        }
        System.out.println(Arrays.toString(state));
    }

    public void rotate(int face, int times) {
        rotateFace(face, times);
        //essa desgraça de adjacencia me custou muito e eu nao consegui fazer ainda
    }
}
