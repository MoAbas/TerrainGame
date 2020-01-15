package terrainGame;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Player extends JFrame implements Cloneable {

    int x, y, score;
    int inObstacleTimer = 0;
    int deadTimer = 0;
    boolean isDead = false;
    JLabel panel = new JLabel("", SwingConstants.CENTER);
    Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);

    public Player(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
        panel.setName("player");
        panel.setText(Integer.toString(score));
        panel.setBorder(border);
        panel.setBackground(Color.green);
        panel.setOpaque(true);
    }

    public void foundPlayer() {
        score++;
        panel.setText(Integer.toString(score));
    }

    public void decreaseOne() {
        score--;
        panel.setText(Integer.toString(score));
    }

    public void dead() {
        isDead = true;
        panel.setText("Dead Player");
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);
    }

    public void setScoreZero() {
        score = 0;
        panel.setText(Integer.toString(score));
    }

    public void increaseScore(int addScore) {
        score += addScore;
        panel.setText(Integer.toString(score));
    }

    public void halfDead() {
        score /= 2;
        panel.setText(Integer.toString(score));
    }

    public Object clone() throws CloneNotSupportedException {
        return (Player)super.clone();
    }
}
