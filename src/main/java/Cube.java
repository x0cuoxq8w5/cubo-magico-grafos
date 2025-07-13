import java.util.Arrays;

public class Cube {

 
    public final byte[] state;

  
    public static final byte[] SOLVED_STATE = {
        0,0,0, 0,0,0, 0,0,0,  // topo (UP)
        1,1,1, 1,1,1, 1,1,1,  // esquerda (LEFT)
        2,2,2, 2,2,2, 2,2,2, // frente (FRONT)
        3,3,3, 3,3,3, 3,3,3, // direita (RIGHT)
        4,4,4, 4,4,4, 4,4,4, // trás (BACK)
        5,5,5, 5,5,5, 5,5,5  // baixo (DOWN)
    };

    public Cube(byte[] state) {
        this.state = state;
    }


    public Cube applyMove(String move) {
        return switch (move) {
            case "F" -> rotateFrontClockwise();
            case "F'" -> rotateFrontAntiClockwise();
            case "F2" -> rotateFront180();
            case "U" -> rotateUpClockwise();
            case "U'" -> rotateUpAntiClockwise();
            case "U2" -> rotateUp180();
            case "R" -> rotateRightClockwise();
            case "R'" -> rotateRightAntiClockwise();
            case "R2" -> rotateRight180();
            case "L" -> rotateLeftClockwise();
            case "L'" -> rotateLeftAntiClockwise();
            case "L2" -> rotateLeft180();
            case "D" -> rotateDownClockwise();
            case "D'" -> rotateDownAntiClockwise();
            case "D2" -> rotateDown180();
            case "B" -> rotateBackClockwise();
            case "B'" -> rotateBackAntiClockwise();
            case "B2" -> rotateBack180();
            default -> this; // Se não reconhecido, retorna o mesmo estado
        };
    }

    public void rotateFace(byte[] rotate, int face) {
        byte[] temp = Arrays.copyOfRange(rotate,face*9,face*9+9);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotate[(face*9)+(j*3)+(3-1-i)] = temp[i*3+j];
            }
        }
    }

    public void rotateColumns(int[] face, int[] step, int[] init, byte[] newstate) {
        for (int i = 0; i < 4; i++) {
            int rightinit;
            int switchstep;
            if (step[i] > 0 || step[(i+1)%4] > 0) {
                rightinit = init[(i+1) % 4];
                switchstep = 1;
            }
            else {
                rightinit = init[(i+1)%4]+step[(i+1)%4]*2;
                switchstep = -1;
            }
            for (int j = 0; j < 3; j++) {
                newstate[face[i] * 9 + step[i] * j + init[i]] = state[face[(i + 1) % 4] * 9 + step[(i + 1) % 4] * j * switchstep + rightinit];
            }
        }
    }

    public Cube rotateFrontClockwise() {
        byte[] newState = this.state.clone();

        // Rotação da própria face (Índices 18-26)
        /*newState[18] = state[24]; newState[19] = state[21]; newState[20] = state[18];
        newState[21] = state[25]; newState[22] = state[22]; newState[23] = state[19];
        newState[24] = state[26]; newState[25] = state[23]; newState[26] = state[20];*/
        rotateFace(newState,Faces.FRONT.value);
        int[] faces = {Faces.UP.value, Faces.LEFT.value, Faces.DOWN.value, Faces.RIGHT.value};
        int[] steps = {1,-3,-1,3};
        int[] inits = {6,8,2,0};
        rotateColumns(faces,steps,inits,newState);
        /*// Guardamos a face do Topo para não sobrescrevê-la
        byte tempUp1 = state[6];
        byte tempUp2 = state[7];
        byte tempUp3 = state[8];

        // Esquerda (coluna dir) -> Topo (linha baixo)
        newState[6] = state[17];
        newState[7] = state[14];
        newState[8] = state[11];

        // Baixo (linha cima) -> Esquerda (coluna dir)
        newState[17] = state[47];
        newState[14] = state[46];
        newState[11] = state[45];

        // Direita (coluna esq) -> Baixo (linha cima)
        newState[47] = state[33];
        newState[46] = state[30];
        newState[45] = state[27];

        // Topo (temp) -> Direita (coluna esq)
        newState[27] = tempUp1;
        newState[30] = tempUp2;
        newState[33] = tempUp3;*/

        return new Cube(newState);
    }
    public Cube rotateFrontAntiClockwise() {
        return rotateFrontClockwise().rotateFrontClockwise().rotateFrontClockwise();
    }
    public Cube rotateFront180() {
        return this.rotateFrontClockwise().rotateFrontClockwise();
    }
    public Cube rotateUpClockwise() {
        byte[] newState = this.state.clone();


        /*newState[0] = state[6]; newState[1] = state[3]; newState[2] = state[0];
        newState[3] = state[7]; newState[4] = state[4]; newState[5] = state[1];
        newState[6] = state[8]; newState[7] = state[5]; newState[8] = state[2];*/
        rotateFace(newState,Faces.UP.value);
        int[] faces = {Faces.LEFT.value, Faces.FRONT.value, Faces.RIGHT.value, Faces.BACK.value};
        int[] steps = {1,1,1,1};
        int[] inits = {0,0,0,0};

        rotateColumns(faces, steps, inits, newState);
        /*byte tempF1 = state[27];
        byte tempF2 = state[28];
        byte tempF3 = state[29];

        //  Frente -> Esquerda
        newState[9] = state[18]; //9 18
        newState[10] = state[19]; //10 19
        newState[11] = state[20]; // 11 20

        // Esquerda -> Trás
        newState[36]  = state[9]; //36 9
        newState[37] = state[10]; //37 10
        newState[38] = state[11]; //38 11

        // Trás -> Direita
        newState[27] = state[36]; //27 36
        newState[28] = state[37]; //28 37
        newState[29] = state[38]; //29 38

        // Direita (temp) -> Frente
        newState[18] = tempF1;
        newState[19] = tempF2;
        newState[20] = tempF3;*/

        return new Cube(newState);
    }
    public Cube rotateUpAntiClockwise() {
        return this.rotateUpClockwise().rotateUpClockwise().rotateUpClockwise();
    }
    public Cube rotateUp180() {
        return this.rotateUpClockwise().rotateUpClockwise();
    }
    public Cube rotateRightClockwise() {
        byte[] newState = this.state.clone();


        /*newState[27] = state[33];
        newState[28] = state[30];
        newState[29] = state[27];
        newState[30] = state[34];
        newState[31] = state[31];
        newState[32] = state[28];
        newState[33] = state[35];
        newState[34] = state[32];
        newState[35] = state[29];*/
        rotateFace(newState,Faces.RIGHT.value);
        int[] faces = {Faces.UP.value,Faces.FRONT.value,Faces.DOWN.value,Faces.BACK.value};
        int[] steps = {3,3,3,-3};
        int[] offsets = {2,2,2,6};
        rotateColumns(faces, steps, offsets, newState);

        /*byte u0 = state[2], u1 = state[5], u2 = state[8];

        byte f0 = state[20], f1 = state[23], f2 = state[26];

        byte d0 = state[47], d1 = state[50], d2 = state[53];

        byte b0 = state[36], b1 = state[39], b2 = state[42];


        newState[20] = d0;
        newState[23] = d1;
        newState[26] = d2;

        newState[53] = b0;
        newState[50] = b1;
        newState[47] = b2;

        newState[42] = u0;
        newState[39] = u1;
        newState[36] = u2;


        newState[2] = f0;
        newState[5] = f1;
        newState[8] = f2;*/

        return new Cube(newState);
    }
    public Cube rotateLeftClockwise() {
        byte[] newState = this.state.clone();

        rotateFace(newState,Faces.LEFT.value);
        /*newState[9]  = state[15];
        newState[10] = state[12];
        newState[11] = state[9];
        newState[12] = state[16];
        newState[13] = state[13];
        newState[14] = state[10];
        newState[15] = state[17];
        newState[16] = state[14];
        newState[17] = state[11];*/
        int[] faces = {Faces.FRONT.value,Faces.UP.value,Faces.BACK.value,Faces.DOWN.value};
        int[] steps = {3,3,-3,3};
        int[] offsets = {0,0,8,0};



        byte u0 = state[0], u1 = state[3], u2 = state[6];
        byte f0 = state[18], f1 = state[21], f2 = state[24];
        byte d0 = state[45], d1 = state[48], d2 = state[51];
        byte b0 = state[38], b1 = state[41], b2 = state[44];

        newState[18] = u0;
        newState[21] = u1;
        newState[24] = u2;


        newState[45] = f0;
        newState[48] = f1;
        newState[51] = f2;


        newState[44] = d0;
        newState[41] = d1;
        newState[38] = d2;


        newState[0] = b2;
        newState[3] = b1;
        newState[6] = b0;

        return new Cube(newState);
    }
    public Cube rotateLeftAntiClockwise() {
        return this.rotateLeft180().rotateLeftClockwise();
    }
    public Cube rotateLeft180() {
        return this.rotateLeftClockwise().rotateLeftClockwise();
    }
    public Cube rotateRightAntiClockwise() {
        return this.rotateRight180().rotateRightClockwise();
    }
    public Cube rotateRight180() {
        return this.rotateRightClockwise().rotateRightClockwise();
    }
    public Cube rotateDownClockwise() {
        byte[] newState = this.state.clone();
        rotateFace(newState,Faces.DOWN.value);
        int[] faces = {Faces.FRONT.value, Faces.LEFT.value, Faces.BACK.value, Faces.RIGHT.value};
        int[] steps = {1,1,1,1};
        int[] offsets = {6,6,6,6};
        rotateColumns(faces,steps, offsets, newState);
        /*byte[]  l={state[15],state[16],state[17]},
                f={state[24],state[25],state[26]},
                r={state[33],state[34],state[35]},
                b={state[42],state[43],state[44]};

        newState[15] = b[0];
        newState[16] = b[1];
        newState[17] = b[2];

        newState[24] = l[0];
        newState[25] = l[1];
        newState[26] = l[2];

        newState[33] = f[0];
        newState[34] = f[1];
        newState[35] = f[2];

        newState[42] = r[0];
        newState[43] = r[1];
        newState[44] = r[2];*/

        return new Cube(newState);
    }
    public Cube rotateDown180() {
        return this.rotateDownClockwise().rotateDownClockwise();
    }
    public Cube rotateDownAntiClockwise() {
        return this.rotateDown180().rotateDownClockwise();
    }
    public Cube rotateBackClockwise() {
        byte[] newState = this.state.clone();
        rotateFace(newState,Faces.BACK.value);
        int[] faces = {Faces.LEFT.value,Faces.UP.value,Faces.RIGHT.value,Faces.DOWN.value};
        int[] steps = {-3,1,3,-1};
        int[] offsets = {6,0,2,8};
        rotateColumns(faces,steps,offsets,newState);
        /*byte[]
                l ={state[9],state[12],state[15]},
                u ={state[0],state[1],state[2]},
                r ={state[29],state[32],state[35]},
                d ={state[51],state[52],state[53]};

        //isso tava trocado
        newState[15] = u[0];
        newState[12] = u[1];
        newState[9] = u[2];

        newState[0] = r[0];
        newState[1] = r[1];
        newState[2] = r[2];

        //isso tambem
        newState[35] = d[0];
        newState[32] = d[1];
        newState[29] = d[2];

        newState[51] = l[0];
        newState[52] = l[1];
        newState[53] = l[2];*/
        return new Cube(newState);
    }
    public Cube rotateBack180() {
        return this.rotateBackClockwise().rotateBackClockwise();
    }
    public Cube rotateBackAntiClockwise() {
        return this.rotateBack180().rotateBackClockwise();
    }

    public static int getFacePosition(int face, int position) {
        return face*9+position;
    }
}
