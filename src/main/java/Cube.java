
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
        switch (move) {
            case "F": return rotateFrontClockwise();
            case "F'": return rotateFrontAntiClockwise(); 
            case "F2": return rotateFront180();
            case "U": return rotateUpClockwise();
            case "U'": return rotateUpAntiClockwise();
            case "U2": return rotateUp180();
            case "R": return rotateRightClockwise();
            case "R'": return rotateRightAntiClockwise();
            case "R2": return rotateRight180();
            case "L": return rotateLeftClockwise();
            case "L'": return rotateLeftAntiClockwise();
            case "L2": return rotateLeft180();
            case "D": return rotateDownClockwise();
            case "D'": return  rotateDownAntiClockwise();
            case "D2": return rotateDown180();
            case "B": return rotateBackClockwise();
            case "B'":return rotateBackAntiClockwise();
            case "B2": return rotateBack180();

            default: return this; // Se não reconhecido, retorna o mesmo estado
        }
    }


 public Cube rotateFrontClockwise() {
    byte[] newState = this.state.clone();

    // Rotação da própria face (Índices 18-26)
    newState[18] = state[24]; newState[19] = state[21]; newState[20] = state[18];
    newState[21] = state[25]; newState[22] = state[22]; newState[23] = state[19];
    newState[24] = state[26]; newState[25] = state[23]; newState[26] = state[20];


    // Guardamos a face do Topo para não sobrescrevê-la
    byte tempUp1 = state[6];
    byte tempUp2 = state[7];
    byte tempUp3 = state[8];

    // Esquerda (coluna dir) -> Topo (linha baixo)
    newState[6] = state[15];
    newState[7] = state[12];
    newState[8] = state[9];

    // Baixo (linha cima) -> Esquerda (coluna dir)
    newState[15] = state[47]; 
    newState[12] = state[46];
    newState[9] = state[45];

    // Direita (coluna esq) -> Baixo (linha cima)
    newState[47] = state[33];
    newState[46] = state[30];
    newState[45] = state[27];

    // Topo (temp) -> Direita (coluna esq)
    newState[33] = tempUp1;
    newState[30] = tempUp2;
    newState[27] = tempUp3;

    return new Cube(newState);
}
  public Cube rotateFrontAntiClockwise() {
    byte[] newState = this.state.clone();

    // Rotação da própria face (Índices 18-26)
    newState[18] = state[20]; newState[19] = state[23]; newState[20] = state[26];
    newState[21] = state[19]; newState[22] = state[22]; newState[23] = state[25];
    newState[24] = state[18]; newState[25] = state[21]; newState[26] = state[24];

    byte tempUp1 = state[6];
    byte tempUp2 = state[7];
    byte tempUp3 = state[8];
    
    // Direita (coluna esq) -> Topo (linha baixo)
    newState[6] = state[27];
    newState[7] = state[30];
    newState[8] = state[33];

    // Baixo (linha cima) -> Direita (coluna esq)
    newState[27] = state[45];
    newState[30] = state[46];
    newState[33] = state[47];

    // Esquerda (coluna dir) -> Baixo (linha cima)
    newState[45] = state[9];
    newState[46] = state[12];
    newState[47] = state[15];

    // Topo (temp) -> Esquerda (coluna dir)
    newState[9]  = tempUp3;
    newState[12] = tempUp2;
    newState[15] = tempUp1;

    return new Cube(newState);
}


    public Cube rotateFront180() {
 return this.rotateFrontClockwise().rotateFrontClockwise(); 
}

public Cube rotateUpClockwise() {
    byte[] newState = this.state.clone();


    newState[0] = state[6]; newState[1] = state[3]; newState[2] = state[0];
    newState[3] = state[7]; newState[4] = state[4]; newState[5] = state[1];
    newState[6] = state[8]; newState[7] = state[5]; newState[8] = state[2];


    byte tempF1 = state[18];
    byte tempF2 = state[19];
    byte tempF3 = state[20];

    // Esquerda -> Frente
    newState[18] = state[9];
    newState[19] = state[10];
    newState[20] = state[11];

    // Trás -> Esquerda
    newState[9]  = state[36];
    newState[10] = state[37];
    newState[11] = state[38];

    // Direita -> Trás
    newState[36] = state[27];
    newState[37] = state[28];
    newState[38] = state[29];

    // Frente (temp) -> Direita
    newState[27] = tempF1;
    newState[28] = tempF2;
    newState[29] = tempF3;

    return new Cube(newState);
}


public Cube rotateUpAntiClockwise() {
    byte[] newState = this.state.clone();


    newState[0] = state[2]; newState[1] = state[5]; newState[2] = state[8];
    newState[3] = state[1]; newState[4] = state[4]; newState[5] = state[7];
    newState[6] = state[0]; newState[7] = state[3]; newState[8] = state[6];


    byte tempF1 = state[18];
    byte tempF2 = state[19];
    byte tempF3 = state[20];

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
    newState[9]  = tempF1;
    newState[10] = tempF2;
    newState[11] = tempF3;

    return new Cube(newState);
}


public Cube rotateUp180() {

    return this.rotateUpClockwise().rotateUpClockwise();
}

public Cube rotateRightClockwise() {
    byte[] newState = this.state.clone();

  
    newState[27] = state[33];
    newState[28] = state[30];
    newState[29] = state[27];
    newState[30] = state[34];
    newState[31] = state[31];
    newState[32] = state[28];
    newState[33] = state[35];
    newState[34] = state[32];
    newState[35] = state[29];


    byte u0 = state[2], u1 = state[5], u2 = state[8];

    byte f0 = state[20], f1 = state[23], f2 = state[26];
    
    byte d0 = state[47], d1 = state[50], d2 = state[53];
  
    byte b0 = state[36], b1 = state[39], b2 = state[42];


    newState[20] = u0;
    newState[23] = u1;
    newState[26] = u2;

    newState[47] = f0;
    newState[50] = f1;
    newState[53] = f2;

    newState[42] = d0;
    newState[39] = d1;
    newState[36] = d2;

  
    newState[2] = b2;
    newState[5] = b1;
    newState[8] = b0;

    return new Cube(newState);
}

public Cube rotateLeftClockwise() {
    byte[] newState = this.state.clone();


    newState[9]  = state[15];
    newState[10] = state[12];
    newState[11] = state[9];
    newState[12] = state[16];
    newState[13] = state[13];
    newState[14] = state[10];
    newState[15] = state[17];
    newState[16] = state[14];
    newState[17] = state[11];



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
    byte[] newState = this.state.clone();

 
    newState[9]  = state[11];
    newState[10] = state[14];
    newState[11] = state[17];
    newState[12] = state[10];
    newState[13] = state[13];
    newState[14] = state[16];
    newState[15] = state[9];
    newState[16] = state[12];
    newState[17] = state[15];



    byte u0 = state[0], u1 = state[3], u2 = state[6];
    byte f0 = state[18], f1 = state[21], f2 = state[24];
    byte d0 = state[45], d1 = state[48], d2 = state[51];
    byte b0 = state[38], b1 = state[41], b2 = state[44];


    newState[38] = u2;
    newState[41] = u1;
    newState[44] = u0;

   
    newState[51] = b0;
    newState[48] = b1;
    newState[45] = b2;


    newState[18] = d0;
    newState[21] = d1;
    newState[24] = d2;


    newState[0] = f0;
    newState[3] = f1;
    newState[6] = f2;

    return new Cube(newState);
}

public Cube rotateLeft180() {
    byte[] newState = this.state.clone();

  
    newState[9]  = state[17];
    newState[10] = state[16];
    newState[11] = state[15];
    newState[12] = state[14];
    newState[13] = state[13]; 
    newState[14] = state[12];
    newState[15] = state[11];
    newState[16] = state[10];
    newState[17] = state[9];


    newState[0] = state[51];
    newState[3] = state[48];
    newState[6] = state[45];

    newState[45] = state[6];
    newState[48] = state[3];
    newState[51] = state[0];


    newState[18] = state[44];
    newState[21] = state[41];
    newState[24] = state[38];

    newState[38] = state[24];
    newState[41] = state[21];
    newState[44] = state[18];

    return new Cube(newState);
}





}
