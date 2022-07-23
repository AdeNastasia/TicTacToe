import java.util.Random;

public class Player {
    private String name;
    private String sign;
    private boolean currentPlayer;

    public String getSign() {
        return sign;
    }

    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
        if(currentPlayer) TicTacToe.currentPlayer = this;
    }

    public void setSign() {
        if(TicTacToe.signThatUsed.equalsIgnoreCase("")) {
            Random random = new Random();
            int randomInt = random.nextInt(2);
            if (randomInt == 1) {
                this.sign = "X";
            } else {
                this.sign = "O";
                currentPlayer = true;
            }
        } else {
            if (TicTacToe.signThatUsed.equalsIgnoreCase("X")) {
                this.sign = "O";
                currentPlayer = true;
            } else {
                this.sign = "X";
            }
        }
    }
}
