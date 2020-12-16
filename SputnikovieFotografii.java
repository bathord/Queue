import java.io.*;
import java.util.Scanner;

public class SputnikovieFotografii {
    static int n, m, queEnd, queBegin, countOfCows, countOfBoxes, x, y, xs, ys, xe;
    static String[] map = new String[75];
    static boolean[][] manage = new boolean[75][75];
    static int[][] trueMap = new int[75][75];
    static int[][] que = new int[10000][2];
    static boolean found;
    static int[][] check = new int[75][75];

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("SATEL.out"));
        startProcess();

        while (found) {
            while (queBegin <= queEnd) {
                get();
                putAll();
            }
            checkThis();
            clear();
            find();
        }

        out.println(countOfBoxes);
        out.println(countOfCows);

        out.flush();
    }

    static void startProcess() throws FileNotFoundException {
        Scanner in = new Scanner(new File("SATEL.in"));

        n = in.nextInt(); m = in.nextInt();

        in.nextLine();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                manage[i][j] = false;
            }
        }

        clear();

        for (int i = 0; i < n; i++) {
            map[i] = in.nextLine();
        }

        queBegin = 0;
        queEnd = -1;
        countOfCows = 0;
        countOfBoxes = 0;

        conversation(map);

        find();
    }

    static void conversation(String[] map) {
        for (int i = 0; i < n; i++) {
            String[] symbols = map[i].split("");
            for (int j = 0; j < m; j++) {
                if (symbols[j].equals(".")) {
                    trueMap[i][j] = 0;
                    manage[i][j] = true;
                } else trueMap[i][j] = 1;
            }
        }
    }

    static void put(int x, int y) {
        queEnd++;
        que[queEnd][0] = x;
        que[queEnd][1] = y;
        manage[y][x] = true;
        check[y][x] = 1;
    }

    static void get() {
        x = que[queBegin][0];
        y = que[queBegin][1];
        queBegin++;
    }

    static void find() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!manage[i][j]) {
                    put(j, i);
                    found = true;
                    return;
                }
            }
        }

        found = false;
    }

    static void putAll() {
        int[][] steps = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int curX, curY;

        for (int i = 0; i < 4; i++) {
            curX = x + steps[i][0];
            curY = y + steps[i][1];
            if (curX >= 0 && curX < m && curY >= 0 && curY < n && !manage[curY][curX]) {
                put(curX, curY);
            }
        }
    }

    static void checkThis() {
        findIn();

        int count = 0;

        for (int i = 0; i < m; i++) {
            if (xs + i >= m) {
                continue;
            }
            if (check[ys][xs + i] == 1) {
                count++;
            }
        }

        for (int i = 0; i < n; i++) {
            boolean flagS = false, flagE = false;
            int xS = 0, xE = 0;
            int countG = 0;
            for (int j = 0; j < m; j++) {
                if (check[i][j] == 1 && !flagS) {
                    flagS = true;
                    xS = j;
                    countG++;
                    if (xS == m-1) {
                        xE = xS;
                    }
                    continue;
                }
                if (check[i][j] == 1 && flagS) {
                    countG++;
                }
                if (check[i][j] == 0 && flagS && !flagE) {
                    xE = j-1;
                    flagE = true;
                }
            }
            if ((countG != count || xs != xS || xe != xE) && flagS) {
                countOfCows++;
                return;
            }
        }

        countOfBoxes++;
    }

    static void clear() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                check[i][j] = 0;
            }
        }
    }

    static void findIn() {
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (check[i][j] == 1 && !flag) {
                    xs = j;
                    ys = i;
                    flag = true;
                    if (xs == m-1) {
                        xe = xs;
                        return;
                    }
                }
                if (check[i][j] == 0 && flag) {
                    xe = j-1;
                    return;
                }
            }
        }
    }
}
