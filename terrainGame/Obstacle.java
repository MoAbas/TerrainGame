package terrainGame;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Obstacle extends JFrame implements Cloneable{
    int x,y;
    JLabel panel = new JLabel();
    Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
    
    public Obstacle(int x,int y) {
            this.x = x;
            this.y = y;
            panel.setName("obstacle");
            panel.setBorder(border);
            panel.setBackground(Color.MAGENTA);
            panel.setOpaque(true);
    }
    
     public Object clone() throws CloneNotSupportedException {
        return (Obstacle)super.clone();
    }
}