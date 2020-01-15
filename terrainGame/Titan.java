package terrainGame;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Titan extends JFrame implements Cloneable{
    int x,y,score,height;
    JLabel panel = new JLabel("",SwingConstants.CENTER);
    Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
    
    public Titan(int x,int y,int score,int height) {
            this.x = x;
            this.y = y;
            this.score = score;
            this.height = height;
        switch (height) {
            case 3:
                panel.setName("sTitan");
                panel.setText(Integer.toString(score));
                panel.setBorder(border);
                panel.setBackground(Color.ORANGE);
                panel.setOpaque(true);
                break;
            case 5:
                panel.setName("mTitan");
                panel.setText(Integer.toString(score));
                panel.setBorder(border);
                panel.setBackground(Color.RED);
                panel.setOpaque(true);
                break;
            case 15:
                panel.setName("lTitan");
                panel.setText(Integer.toString(score));
                panel.setBorder(border);
                panel.setBackground(Color.BLACK);
                panel.setForeground(Color.LIGHT_GRAY);
                panel.setOpaque(true);
                break;
            default:
                break;
        }
    }

    public void dead() {
            score = 0;
            panel.setText(Integer.toString(score));
    }
    
    public void halfDead(){
        score /= 2;
        panel.setText(Integer.toString(score));
    }
    
    public Object clone() throws CloneNotSupportedException {
        return (Titan)super.clone();
    }
}