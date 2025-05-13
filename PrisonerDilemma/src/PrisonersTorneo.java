import java.util.Random;

public class PrisonersTorneo {

    static final int TURNI = 200;
    static final Random r = new Random();

    public static void main(String[] args) {
        // 0==C, 1==T, 2==random
        String[] strategies = {"alwaysC", "AlwaysT", "Random"};
        int len = strategies.length;

        int[][] risultati = new int[len][len];
        int[] totali = new int[len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                //far giocare le strategie
                int[] punteggi = playGame(i, j);
                //Salvare risultatai nella matrice
                risultati[i][j] = punteggi[0];
                totali[i] += punteggi[0];
            }
        }

        System.out.println("Risultati torneo:");
        for (int i = 0; i < len; i++) {
            System.out.print(strategies[i] + ": ");
            for (int j = 0; j < len; j++) System.out.print(risultati[i][j] + " ");
            System.out.println("| Totale: " + totali[i]);
        }

    }

    public static int[] playGame(int stratA, int stratB) {

        char[] movesA = new char[TURNI];
        char[] movesB = new char[TURNI];

        for (int i = 0; i < TURNI; i++) {
            movesA[i] = computeMove(stratA, movesB);
            movesB[i] = computeMove(stratB, movesA);
        }

        //Calcolo punteggi
        //Come facciamo?
        int punteggioA = 0;
        int punteggioB = 0;
        for (int j = 0; j < TURNI; j++) {
            //HERE
            int[] points = calcolaPunteggi(movesA[j], movesB[j]);
            punteggioA += points[0];
            punteggioB += points[1];
        }

        return new int[]{punteggioA, punteggioB};
    }

    private static int[] calcolaPunteggi(char moveA, char moveB) {
        if (moveA == 'C' && moveB == 'C') {
            //restituisco array lungo due
            // dove la prima posizione è il punteggio di stratA, la seconda di stratB
            return new int[]{3, 3};
        } else if (moveA == 'C' && moveB == 'T') {
            return new int[]{0, 5};
        } else if (moveA == 'T' && moveB == 'C') {
            return new int[]{5, 0};
        } else return new int[]{1, 1};
    }

    private static char computeMove(int strat, char[] stratB) {
        if (strat == 0) {
            return playAlwaysC();
        } else if (strat == 1) {
            return playAlwaysT();
        }
        return playRandom(50);
    }

    private static char playRandom(int prob) {
        r.setSeed(System.nanoTime());
        return r.nextInt(0, 100) < prob ? 'C' : 'T';
    }

    static char playAlwaysC() {
        return 'C';
    }

    static char playAlwaysT() {
        return 'T';
    }
}
