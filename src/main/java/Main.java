
public class Main {

    public static void main(String[] args) {
        // 1. Cria um cubo resolvido
        Cube solvedCube = new Cube(Cube.SOLVED_STATE);

        // 2. Imprime o estado inicial para referência
        System.out.println("--- ESTADO INICIAL (RESOLVIDO) ---");
        printCube(solvedCube);

        // 3. Aplique o movimento que você quer testar
        System.out.println("--- APÓS O MOVIMENTO: U ---"); // Mude "F" para "U", "R'", "B2", etc.
        Cube cubeAfterMove = solvedCube.applyMove("U"); // Mude "F" para "U", "R'", "B2", etc.
        printCube(cubeAfterMove);
    }

    public static void printCube(Cube cube) {
        byte[] s = cube.state;
        String space = "       "; 

        // Face de Cima (UP)
        System.out.println(space + s[0] + " " + s[1] + " " + s[2]);
        System.out.println(space + s[3] + " " + s[4] + " " + s[5]);
        System.out.println(space + s[6] + " " + s[7] + " " + s[8]);
        System.out.println("---------------------");

        // Faces do Meio (LEFT, FRONT, RIGHT, BACK)
        for (int i = 0; i < 3; i++) {
            // LEFT          FRONT           RIGHT           BACK
            System.out.println(
                s[9 + i*3] + " " + s[10 + i*3] + " " + s[11 + i*3] + " | " +
                s[18 + i*3] + " " + s[19 + i*3] + " " + s[20 + i*3] + " | " +
                s[27 + i*3] + " " + s[28 + i*3] + " " + s[29 + i*3] + " | " +
                s[36 + i*3] + " " + s[37 + i*3] + " " + s[38 + i*3]
            );
        }
        System.out.println("---------------------");

        // Face de Baixo (DOWN)
        System.out.println(space + s[45] + " " + s[46] + " " + s[47]);
        System.out.println(space + s[48] + " " + s[49] + " " + s[50]);
        System.out.println(space + s[51] + " " + s[52] + " " + s[53]);
        System.out.println();
    }
}