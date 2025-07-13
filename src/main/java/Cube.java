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

    public Cube rotateFrontClockwise() {   // correto
        byte[] newState = this.state.clone();

        // Rotação da própria face (Índices 18-26)
        /*newState[18] = state[24]; newState[19] = state[21]; newState[20] = state[18];
        newState[21] = state[25]; newState[22] = state[22]; newState[23] = state[19];
        newState[24] = state[26]; newState[25] = state[23]; newState[26] = state[20];*/
        rotateFace(newState,Faces.FRONT.value);

        // Guardamos a face do Topo para não sobrescrevê-la
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
        newState[33] = tempUp3;

        return new Cube(newState);
    }
    public Cube rotateFrontAntiClockwise() {
        return rotateFrontClockwise().rotateFrontClockwise().rotateFrontClockwise();
}
    public Cube rotateFront180() {
        return rotateFrontClockwise().rotateFrontClockwise();
    }
    public Cube rotateUpClockwise() {
    byte[] newState = this.state.clone();
    rotateFace(newState, Faces.UP.value);

    // Salva a linha superior da face da Frente
    byte[] temp = {state[18], state[19], state[20]};

    // Direita -> Frente
    newState[18] = state[27];
    newState[19] = state[28];
    newState[20] = state[29];

    // Trás -> Direita
    newState[27] = state[36];
    newState[28] = state[37];
    newState[29] = state[38];

    // Esquerda -> Trás
    newState[36] = state[9];
    newState[37] = state[10];
    newState[38] = state[11];

    // Frente (temp) -> Esquerda
    newState[9] = temp[0];
    newState[10] = temp[1];
    newState[11] = temp[2];

    return new Cube(newState);
}
    public Cube rotateUpAntiClockwise() {
        return rotateUpClockwise().rotateUpClockwise().rotateUpClockwise();
    }
    public Cube rotateUp180() {
        return rotateUpClockwise().rotateUpClockwise();
    }
public Cube rotateRightClockwise() {
    byte[] newState = this.state.clone();
    rotateFace(newState, Faces.RIGHT.value);

    // Salva a coluna da direita da face de Cima
    byte[] temp = {state[2], state[5], state[8]};

    // Frente -> Cima
    newState[2] = state[20];
    newState[5] = state[23];
    newState[8] = state[26];

    // Baixo -> Frente
    newState[20] = state[47];
    newState[23] = state[50];
    newState[26] = state[53];

    // Trás -> Baixo 
    newState[47] = state[44];
    newState[50] = state[41];
    newState[53] = state[38];

    // Cima (temp) -> Trás 
    newState[44] = temp[0];
    newState[41] = temp[1];
    newState[38] = temp[2];

    return new Cube(newState);
}
 public Cube rotateLeftClockwise() {
    byte[] newState = this.state.clone();
    rotateFace(newState, Faces.LEFT.value);

    // Salva a coluna da esquerda da face de Cima
    byte[] temp = {state[0], state[3], state[6]};
    
    // Trás -> Cima 
    newState[0] = state[42];
    newState[3] = state[39];
    newState[6] = state[36];

    // Baixo -> Trás 
    newState[42] = state[51];
    newState[39] = state[48];
    newState[36] = state[45];

    // Frente -> Baixo
    newState[51] = state[24];
    newState[48] = state[21];
    newState[45] = state[18];

    // Cima (temp) -> Frente
    newState[18] = temp[0];
    newState[21] = temp[1];
    newState[24] = temp[2];

    return new Cube(newState);
}
    public Cube rotateLeftAntiClockwise() {
        return this.rotateLeftClockwise().rotateLeftClockwise().rotateLeftClockwise();
    }
    public Cube rotateLeft180() {
        return this.rotateLeftClockwise().rotateLeftClockwise();
    }
    public Cube rotateRightAntiClockwise() {
        return this.rotateRightClockwise().rotateRightClockwise().rotateRightClockwise();
    }
    public Cube rotateRight180() {
        return this.rotateRightClockwise().rotateRightClockwise();
    }
public Cube rotateDownClockwise() {
    byte[] newState = this.state.clone();
    rotateFace(newState, Faces.DOWN.value);

    // Salva a linha de baixo da face da Frente
    byte[] temp = {state[24], state[25], state[26]};

    // Esquerda -> Frente
    newState[24] = state[15];
    newState[25] = state[16];
    newState[26] = state[17];

    // Trás -> Esquerda
    newState[15] = state[42];
    newState[16] = state[43];
    newState[17] = state[44];

    // Direita -> Trás
    newState[42] = state[33];
    newState[43] = state[34];
    newState[44] = state[35];

    // Frente (temp) -> Direita
    newState[33] = temp[0];
    newState[34] = temp[1];
    newState[35] = temp[2];

    return new Cube(newState);
}
    public Cube rotateDown180() {
        return this.rotateDownClockwise().rotateDownClockwise();
    }
    public Cube rotateDownAntiClockwise() {
        return this.rotateDownClockwise().rotateDownClockwise().rotateDownClockwise();
    }
public Cube rotateBackClockwise() {
    byte[] newState = this.state.clone();
    rotateFace(newState, Faces.BACK.value);

    // Salva a linha de cima da face de Cima
    byte[] temp = {state[0], state[1], state[2]};

    // Direita -> Cima
    newState[0] = state[29];
    newState[1] = state[32];
    newState[2] = state[35];

    // Baixo -> Direita (note a inversão dos índices)
    newState[29] = state[53];
    newState[32] = state[52];
    newState[35] = state[51];

    // Esquerda -> Baixo
    newState[53] = state[9];
    newState[52] = state[12];
    newState[51] = state[15];
    
    // Cima (temp) -> Esquerda (note a inversão dos índices)
    newState[9] = temp[2];
    newState[12] = temp[1];
    newState[15] = temp[0];

    return new Cube(newState);
}
    public Cube rotateBack180() {
        return this.rotateBackClockwise().rotateBackClockwise();
    }
    public Cube rotateBackAntiClockwise() {
        return this.rotateBackClockwise().rotateBackClockwise().rotateBackClockwise();
    }
  

@Override
public boolean equals(Object o) {

    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Cube otherCube = (Cube) o;

    return Arrays.equals(this.state, otherCube.state);
}

@Override
public int hashCode() {

    return Arrays.hashCode(this.state);
}
}
