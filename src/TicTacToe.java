import java.util.Scanner;

public class TicTacToe {
    public static int HEIGHT = 3;
    public static int WIDTH = 3;
    public static String[][] gameField = new String[HEIGHT][WIDTH];
    public static String signThatUsed = "";
    public static boolean isGameStopped = false;
    public static Player[] players = new Player[2];
    public static Player currentPlayer;
    public static Player winner;
    public static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        createPlayers();
        createGameField();
        drawGameField();
        currentPlayer = whoIsFirst();
        System.out.printf("%s your sign is \"O\", so it's your turn ;)\n", currentPlayer.getName());
        while (!isGameStopped) {
            move(currentPlayer);
            drawGameField();
            winnerExist();
        }
        System.out.printf("\n%s, congratulations! You won!\nGame is over", winner.getName());
        scanner.close();
    }

    public static void createGameField() { // draw void scene
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                gameField[i][j] = "_";
            }
        }
    }

    public static void drawGameField() {
        System.out.println();
        for (String[] strings : gameField) {
            for (int i = 0; i < WIDTH; i++) {
                System.out.print(strings[i] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void createPlayers() {
        System.out.println("Hello! What is your name?");
        String nameOfFirstPlayer = scanner.nextLine();
        Player player1 = new Player(nameOfFirstPlayer);
        player1.setSign();
        signThatUsed = player1.getSign();
        System.out.println("OK. Here we have an another player. How is your name, dear");
        String nameOfSecondPlayer = scanner.nextLine();
        Player player2 = new Player(nameOfSecondPlayer);
        player2.setSign();
        System.out.printf("\nWell, %s and %s, let's start!\nWe have an empty field, look at this:", player1.getName(), player2.getName());

        players[0] = player1;
        players[1] = player2;
    }
    public static Player whoIsFirst() {
        Player isFirstPlayer = null;
        for (Player player : players) {
            if (player.isCurrentPlayer()) {
                isFirstPlayer = player;
                break;
            }
        }
        return isFirstPlayer;
    }

    public static void move(Player player) {
        int nextLine = chooseLine(player);
        int nexColumn = chooseColumn(player);
        if (nexColumn >= HEIGHT || nextLine >= WIDTH) {
            System.out.printf("%s, our game field is smaller that you think. So choose new coordinates\n", currentPlayer.getName());
            move(player);
        } else if (!(gameField[nextLine][nexColumn].equalsIgnoreCase("_"))) {
            System.out.printf("%s, oops! It's not an empty ceil. Try it again\n", currentPlayer.getName());
            move(player);
        } else {
                changeGameField(nextLine, nexColumn);
                changeCurrentPlayer();
        }

    }

    public static int chooseLine(Player player) {
        int line = 0;
        System.out.printf("%s, choose the line of your next step (Coordinate X)\n", player.getName());
        try {
            line = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("You should pick a number. Try it again");
            chooseLine(player);
        }
        return line;

    }

    public static int chooseColumn(Player player) {
        int column = 0;
        System.out.printf("%s, choose the column of your next step (Coordinate Y)\n", player.getName());
        try {
            column = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.printf("%s you should pick a number. Try it again\n", player.getName());
            chooseColumn(player);
        }
        return column;
    }

    public static void changeGameField(int line, int column) {
        for (Player player : players) {
            if(player.isCurrentPlayer()) {
                gameField[line][column] = player.getSign();
            }
        }
    }

    public static void changeCurrentPlayer() {
        for (Player player : players) {
            if (player.isCurrentPlayer()) {
                player.setCurrentPlayer(false);
            } else {
                player.setCurrentPlayer(true);
            }
        }
    }

    public static void winnerExist() {
        String winnersSign = "";
        boolean isFirstDiagonalFull = false;
        boolean isSecondDiagonalFull = false;
        boolean isAnyHorizontalFull = false;
        boolean isAnyVerticalFull = false;

        if (gameField[0][0].equalsIgnoreCase(gameField[1][1]) &&
                gameField[1][1].equalsIgnoreCase(gameField[2][2]) && !gameField[0][0].equals("_")) {
            isFirstDiagonalFull = true;
            winnersSign = gameField[0][0];
        }

        if (gameField[0][2].equalsIgnoreCase(gameField[1][1]) &&
                gameField[2][0].equalsIgnoreCase(gameField[1][1]) && !gameField[0][2].equals("_")) {
            isSecondDiagonalFull = true;
            winnersSign = gameField[0][2];
        }

        for (int i = 0; i < HEIGHT; i++) {
            int j = 0;
                if (gameField[i][j].equalsIgnoreCase(gameField[i][j + 1]) &&
                gameField[i][j].equalsIgnoreCase(gameField[i][j + 2]) && !gameField[i][j].equals("_")) {
                   isAnyVerticalFull = true;
                   winnersSign = gameField[i][j];
                } else if (gameField[j][i].equalsIgnoreCase(gameField[j + 1][i]) &&
                        gameField[j][i].equalsIgnoreCase(gameField[j + 2][i]) && !gameField[j][i].equals("_")) {
                    isAnyHorizontalFull = true;
                    winnersSign = gameField[j][i];
            }
        }

        isGameStopped = isAnyHorizontalFull || isAnyVerticalFull || isFirstDiagonalFull || isSecondDiagonalFull;
        if (isGameStopped) {
            for (Player player : players) {
                if(player.getSign().equalsIgnoreCase(winnersSign)) {
                    winner = player;
                }
            }
        }

    }
}
