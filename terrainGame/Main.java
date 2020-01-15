package terrainGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class Main extends JFrame {

    private final JButton oneTurn = new JButton("One Turn");
    private final JButton automatic = new JButton("Automatic");
    private final JButton back = new JButton("Back");
    ArrayList<Player> playerList = new ArrayList();
    ArrayList<Player> deadPlayerList = new ArrayList();
    ArrayList<Titan> titanList = new ArrayList();
    ArrayList<Obstacle> obstacleList = new ArrayList();

    LinkedList<LinkedList> mainLinkedList = new LinkedList<>();

    private final int gridSize;
    JLabel counterl = new JLabel();
    JLabel roundl = new JLabel();
    int counter;
    int round;
    Player player;
    Titan titan;
    Obstacle obstacle;
    JPanel[][] cells;
    JPanel[][] cells2;
    private int playerCount, sTitan, mTitan, lTitan, obstacleCount, playerScore, sTitanScore, mTitanScore, lTitanScore;
    private final Random rand = new Random();
    JPanel tilesPanel = new JPanel();
    JPanel sidePanel = new JPanel();

    public Main() {
        gridSize = 10;
        playerCount = 12;
        playerScore = 10;
        sTitan = 5;
        sTitanScore = 12;
        mTitan = 3;
        mTitanScore = 14;
        lTitan = 2;
        lTitanScore = 16;
        obstacleCount = 5;
        counter=0;
        round=0;
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter Terrain Size: ");
//        gridSize = sc.nextInt();
//        System.out.println("Enter Player Count: ");
//        playerCount = sc.nextInt();
//        System.out.println("Enter 3m Titan Count: ");
//        threeTitan = sc.nextInt();
//        System.out.println("Enter 5m Titan Count: ");
//        fiveTitan = sc.nextInt();
//        System.out.println("Enter 15m Titan Count: ");
//        fifteenTitan = sc.nextInt();
//        System.out.println("Enter Obstacle Count: ");
//        obstacle = sc.nextInt();
//        System.out.println("Enter 3m Ttian Initial Score: ");
//        threeTitanScore = sc.nextInt();
//        System.out.println("Enter 5m Ttian Initial Score: ");
//        fiveTitanScore = sc.nextInt();
//        System.out.println("Enter 15m Ttian Initial Score: ");
//        fifteenTitanScore = sc.nextInt();
        creatView();
        populateGrid();
        
        oneTurn.addActionListener((ActionEvent e) -> {
            
            if(counter<round){
                counter++;
                counterl.setText("Counter: "+Integer.toString(counter));
                beforeTurn();
            }
            else{
                counter++;
                round++;
                counterl.setText("Counter: "+Integer.toString(counter));
                roundl.setText("Round: "+Integer.toString(round));
                playTurn();
            }
            checkGame();
        });
        
        automatic.addActionListener((ActionEvent e) -> {
            while (playerCount > 0 && (sTitan + mTitan + lTitan) > 0) {
                round++;
                counter++;
                counterl.setText("Counter: "+Integer.toString(counter));
                roundl.setText("Round: "+Integer.toString(round));
                playTurn();
            }
            checkGame();
        });
        
        back.addActionListener((ActionEvent e) -> {
            if(counter>0){
                counter--;
                counterl.setText("Counter: "+Integer.toString(counter));
                back();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    private void creatView() {
        setTitle("Game");
        setSize(new Dimension(1000, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new GridBagLayout());
        GridLayout grid = new GridLayout(0, 1);
        sidePanel.setLayout(grid);
        GridLayout grid2 = new GridLayout(gridSize, gridSize, 2, 2);
        tilesPanel.setLayout(grid2);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.weightx = 0;
        c.weighty = 1;
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.weightx = 1;
        c1.weighty = 1;
        c1.fill = GridBagConstraints.BOTH;
        getContentPane().add(sidePanel, c);
        getContentPane().add(tilesPanel, c1);
        tilesPanel.setBackground(Color.DARK_GRAY);
        JLabel playerLabel = new JLabel("Player", SwingConstants.CENTER);
        playerLabel.setBackground(Color.GREEN);
        playerLabel.setOpaque(true);
        JLabel sTitanLabel = new JLabel("3m Titan", SwingConstants.CENTER);
        sTitanLabel.setBackground(Color.ORANGE);
        sTitanLabel.setOpaque(true);
        JLabel mTitanLabel = new JLabel("5m Titan", SwingConstants.CENTER);
        mTitanLabel.setBackground(Color.RED);
        mTitanLabel.setOpaque(true);
        JLabel lTitanLabel = new JLabel("15m Titan", SwingConstants.CENTER);
        lTitanLabel.setBackground(Color.BLACK);
        lTitanLabel.setForeground(Color.LIGHT_GRAY);
        lTitanLabel.setOpaque(true);
        JLabel obstacleLabel = new JLabel("Obstacle", SwingConstants.CENTER);
        obstacleLabel.setBackground(Color.MAGENTA);
        obstacleLabel.setOpaque(true);
        counterl = new JLabel("Counter: "+Integer.toString(counter), SwingConstants.CENTER);
        roundl = new JLabel("Round: "+Integer.toString(round), SwingConstants.CENTER);
        sidePanel.add(playerLabel);
        sidePanel.add(sTitanLabel);
        sidePanel.add(mTitanLabel);
        sidePanel.add(lTitanLabel);
        sidePanel.add(obstacleLabel);
        sidePanel.add(counterl);
        sidePanel.add(roundl);
        sidePanel.add(oneTurn);
        sidePanel.add(automatic);
        sidePanel.add(back);
    }

    private void populateGrid() {
        tilesPanel.removeAll();
        LinkedList<ArrayList> groupLinkedList = new LinkedList<>();
        cells = new JPanel[gridSize][gridSize];
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                cells[y][x] = new JPanel();
                GridLayout grid = new GridLayout();
                cells[y][x].setLayout(grid);
                tilesPanel.add(cells[y][x]);
            }
        }
        for (int i = 0; i < playerCount; i++) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);
            player = new Player(x, y, playerScore);
            playerList.add(player);
            cells[y][x].add(player.panel);
        }
        for (int i = 0; i < sTitan; i++) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);
            titan = new Titan(x, y, sTitanScore, 3);
            titanList.add(titan);
            cells[y][x].add(titan.panel);
        }
        for (int i = sTitan; i < sTitan + mTitan; i++) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);
            titan = new Titan(x, y, mTitanScore, 5);
            titanList.add(titan);
            cells[y][x].add(titan.panel);
        }
        for (int i = sTitan + mTitan; i < sTitan + mTitan + lTitan; i++) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);
            titan = new Titan(x, y, lTitanScore, 15);
            titanList.add(titan);
            cells[y][x].add(titan.panel);
        }
        for (int i = 0; i < obstacleCount; i++) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);
            obstacle = new Obstacle(x, y);
            obstacleList.add(obstacle);
            cells[y][x].add(obstacle.panel);
        }

        try {
            ArrayList<Player> TempPlayer1 = new ArrayList<>();
            for (Player p : playerList) {
                TempPlayer1.add((Player) p.clone());
            }

            groupLinkedList.add(TempPlayer1);
        } catch (CloneNotSupportedException ex) {

        }
        
        try {
            ArrayList<Titan> TempTitan1 = new ArrayList<>();
            for (Titan t : titanList) {
                TempTitan1.add((Titan) t.clone());
            }
            groupLinkedList.add(TempTitan1);
        }
        catch (CloneNotSupportedException ex) {

        }
        
        try {
            ArrayList<Obstacle> TempObstacle1 = new ArrayList<>();
            for (Obstacle o : obstacleList) {
                TempObstacle1.add((Obstacle) o.clone());
            }
            groupLinkedList.add(TempObstacle1);
        }
        catch (CloneNotSupportedException ex) {

        }
        
        try {
            ArrayList<Player> TempDeadP1 = new ArrayList<>();
            for (Player p : deadPlayerList) {
                TempDeadP1.add((Player) p.clone());
            }
            groupLinkedList.add(TempDeadP1);
        }
        catch (CloneNotSupportedException ex) {

        }
        mainLinkedList.add(groupLinkedList);

        revalidate();
        repaint();
    }

    public void playTurn() {
        checkRules();
        LinkedList<ArrayList> groupLinkedList = new LinkedList<>();
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).inObstacleTimer == 0) {
                for (int j = 0; j < obstacleList.size(); j++) {
                    if (playerList.get(i).x == obstacleList.get(j).x
                            && playerList.get(i).y == obstacleList.get(j).y) {
                        playerList.get(i).inObstacleTimer = 1;

                        break;
                    }
                }
            }
            if (playerList.get(i).inObstacleTimer == 1) {
                playerList.get(i).inObstacleTimer = 2;

                continue;
            }
            if (playerList.get(i).inObstacleTimer == 0 || playerList.get(i).inObstacleTimer == 2) {
                int direction = rand.nextInt(5);
                switch (direction) {
                    case 0:
                        if (playerList.get(i).x < gridSize - 1) {
                            playerList.get(i).x++;
                        } else {
                            playerList.get(i).x = gridSize - 2;
                        }
                        break;
                    case 1:
                        if (playerList.get(i).y < gridSize - 1) {
                            playerList.get(i).y++;
                        } else {
                            playerList.get(i).y = gridSize - 2;
                        }
                        break;
                    case 2:
                        if (playerList.get(i).x > 0) {
                            playerList.get(i).x--;
                        } else {
                            playerList.get(i).x = 1;
                        }
                        break;
                    case 3:
                        if (playerList.get(i).y > 0) {
                            playerList.get(i).y--;
                        } else {
                            playerList.get(i).y = 1;
                        }
                        break;
                    case 4:
                        break;
                }
                cells[playerList.get(i).y][playerList.get(i).x].add(playerList.get(i).panel);
                playerList.get(i).inObstacleTimer = 0;
            }
        }
        for (int i = 0; i < titanList.size(); i++) {
            int direction = rand.nextInt(5);
            switch (direction) {
                case 0:
                    if (titanList.get(i).x < gridSize - 1) {
                        titanList.get(i).x++;
                    } else {
                        titanList.get(i).x = gridSize - 2;
                    }
                    break;
                case 1:
                    if (titanList.get(i).y < gridSize - 1) {
                        titanList.get(i).y++;
                    } else {
                        titanList.get(i).y = gridSize - 2;
                    }
                    break;
                case 2:
                    if (titanList.get(i).x > 0) {
                        titanList.get(i).x--;
                    } else {
                        titanList.get(i).x = 1;
                    }
                    break;
                case 3:
                    if (titanList.get(i).y > 0) {
                        titanList.get(i).y--;
                    } else {
                        titanList.get(i).y = 1;
                    }
                    break;
                case 4:
                    break;
            }
            cells[titanList.get(i).y][titanList.get(i).x].add(titanList.get(i).panel);
        }
        for (int i = 0; i < deadPlayerList.size(); i++) {
            deadPlayerList.get(i).deadTimer++;
            if (deadPlayerList.get(i).deadTimer % 5 == 0) {
                deadPlayerList.get(i).score--;
            }
            if (deadPlayerList.get(i).score == 0) {
                cells[deadPlayerList.get(i).y][deadPlayerList.get(i).x].removeAll();
                deadPlayerList.remove(i);
            }
        }
        
        
        try {
            ArrayList<Player> TempPlayer2 = new ArrayList<>();
            for (Player p : playerList) {
                TempPlayer2.add((Player) p.clone());
            }

            groupLinkedList.add(TempPlayer2);
        }
        catch (CloneNotSupportedException ex) {

        }
        
        try {
            ArrayList<Titan> TempTitan2 = new ArrayList<>();
            for (Titan t : titanList) {
                TempTitan2.add((Titan) t.clone());
            }

            groupLinkedList.add(TempTitan2);
        }
        catch (CloneNotSupportedException ex) {

        }
        
        try {
            ArrayList<Obstacle> TempObstacle2 = new ArrayList<>();
            for (Obstacle o : obstacleList) {
                TempObstacle2.add((Obstacle) o.clone());
            }

            groupLinkedList.add(TempObstacle2);
        }
        catch (CloneNotSupportedException ex) {

        }
        
        try {
            ArrayList<Player> TempDeadP2 = new ArrayList<>();
            for (Player p : deadPlayerList) {
                TempDeadP2.add((Player) p.clone());
            }

            groupLinkedList.add(TempDeadP2);
        }
        catch (CloneNotSupportedException ex) {

        }
        
        mainLinkedList.add(groupLinkedList);

        revalidate();
        repaint();
    }

    private void checkRules() {
        for (int i = 0; i < playerList.size(); i++) {
            for (int j = i + 1; j < playerList.size(); j++) {
                if (playerList.get(j).x == playerList.get(i).x && playerList.get(j).y == playerList.get(i).y) {
                    playerList.get(i).foundPlayer();
                    playerList.get(j).foundPlayer();
                }
            }
        }
        for (int i = 0; i < playerList.size(); i++) {
            for (int j = 0; j < deadPlayerList.size(); j++) {
                if (deadPlayerList.get(j).x == playerList.get(i).x && deadPlayerList.get(j).y == playerList.get(i).y) {
                    playerList.get(i).increaseScore(deadPlayerList.get(j).score);
                    deadPlayerList.remove(j);
                    cells[playerList.get(i).y][playerList.get(i).x].removeAll();

                }
            }
        }
        titanAttackloop:
        for (int i = 0; i < titanList.size(); i++) {
            for (int j = 0; j < obstacleList.size(); j++) {
                if (titanList.get(i).x == obstacleList.get(j).x && titanList.get(i).y == obstacleList.get(j).y) {
                    cells[obstacleList.get(j).y][obstacleList.get(j).x].removeAll();
                    obstacleList.remove(j);
                    break;
                }
            }
            for (int j = 0; j < playerList.size(); j++) {
                if (playerList.size() > 0 && titanList.size() > 0 && i < titanList.size()) {
                    if (playerList.get(j).x == titanList.get(i).x && playerList.get(j).y == titanList.get(i).y) {
                        switch (titanList.get(i).height) {
                            case 3:
                                if (playerList.get(j).score < titanList.get(i).score) {
                                    if (playerList.get(j).score == 1) {
                                        cells[playerList.get(j).y][playerList.get(j).x].removeAll();
                                        playerList.get(j).dead();
//                                        JLabel label = new JLabel("Loot");
//                                        label.setFont(label.getFont().deriveFont(32.0f));
                                        cells[playerList.get(j).y][playerList.get(j).x].add(playerList.get(j).panel);
                                        deadPlayerList.add(playerList.get(j));
                                        playerList.remove(j);
                                        playerCount--;
                                    } else {
                                        playerList.get(j).decreaseOne();
                                    }
                                }
                                break;
                            case 5:
                                if (playerList.get(j).score < titanList.get(i).score) {
                                    if (playerList.get(j).score == 1) {
                                        cells[playerList.get(j).y][playerList.get(j).x].removeAll();
                                        playerList.get(j).dead();
//                                        JLabel label = new JLabel("Loot");
//                                        label.setFont(label.getFont().deriveFont(32.0f));
                                        cells[playerList.get(j).y][playerList.get(j).x].add(playerList.get(j).panel);
                                        deadPlayerList.add(playerList.get(j));
                                        playerList.remove(j);
                                        playerCount--;

                                    } else {
                                        playerList.get(j).halfDead();

                                    }
                                }
                                break;
                            case 15:
                                if (playerList.get(j).score < titanList.get(i).score) {
                                    cells[playerList.get(j).y][playerList.get(j).x].removeAll();
                                    playerList.get(j).dead();
//                                    JLabel label = new JLabel("Loot");
//                                    label.setFont(label.getFont().deriveFont(32.0f));
                                    cells[playerList.get(j).y][playerList.get(j).x].add(playerList.get(j).panel);
                                    deadPlayerList.add(playerList.get(j));
                                    playerList.remove(j);
                                    playerCount--;

                                }
                                break;
                        }
                    }
                } else {
                    break titanAttackloop;
                }

            }
        }

        playerAttackloop:
        for (int i = 0; i < playerList.size(); i++) {
            for (int j = 0; j < titanList.size(); j++) {
                if (playerList.size() > 0 && titanList.size() > 0 && i < playerList.size()) {
                    if (playerList.get(i).x == titanList.get(j).x && playerList.get(i).y == titanList.get(j).y) {
                        switch (titanList.get(j).height) {
                            case 3:
                                if (playerList.get(i).score > titanList.get(j).score) {
                                    titanList.get(j).dead();
                                    cells[titanList.get(j).y][titanList.get(j).x].removeAll();
                                    titanList.remove(j);
                                    sTitan--;
                                }
                                break;
                            case 5:
                                if (playerList.get(i).score > titanList.get(j).score) {
                                    titanList.get(j).dead();
                                    cells[titanList.get(j).y][titanList.get(j).x].removeAll();
                                    titanList.remove(j);
                                    mTitan--;
                                }
                                break;
                            case 15:
                                if (playerList.get(i).score > titanList.get(j).score) {
                                    if (titanList.get(j).score == 1) {
                                        titanList.get(j).dead();
                                        cells[titanList.get(j).y][titanList.get(j).x].removeAll();
                                        titanList.remove(j);
                                        lTitan--;
                                    } else {
                                        titanList.get(j).halfDead();
                                    }
                                }
                                break;
                        }
                    }
                } else {
                    break playerAttackloop;
                }

            }
        }
        revalidate();
        repaint();

    }

    public void back() {
        
            playerList = (ArrayList<Player>) mainLinkedList.get(counter).get(0);
            for (int i = 0; i < playerList.size(); i++) {
                playerList.get(i).panel.setText(Integer.toString(playerList.get(i).score));
                cells[playerList.get(i).y][playerList.get(i).x].remove(playerList.get(i));
                cells[playerList.get(i).y][playerList.get(i).x].add(playerList.get(i).panel);
            }
            
            titanList = (ArrayList<Titan>) mainLinkedList.get(counter).get(1);
            for (int i = 0; i < titanList.size(); i++) {
                titanList.get(i).panel.setText(Integer.toString(titanList.get(i).score));
                cells[titanList.get(i).y][titanList.get(i).x].remove(titanList.get(i));
                cells[titanList.get(i).y][titanList.get(i).x].add(titanList.get(i).panel);
            }
            
            obstacleList = (ArrayList<Obstacle>) mainLinkedList.get(counter).get(2);
            for (int i = 0; i < obstacleList.size(); i++) {
                cells[obstacleList.get(i).y][obstacleList.get(i).x].remove(obstacleList.get(i));
                cells[obstacleList.get(i).y][obstacleList.get(i).x].add(obstacleList.get(i).panel);
            }
            
            deadPlayerList = (ArrayList<Player>) mainLinkedList.get(counter).get(3);
            for (int i = 0; i < deadPlayerList.size(); i++) {
                deadPlayerList.get(i).panel.setText(Integer.toString(deadPlayerList.get(i).score));
                cells[deadPlayerList.get(i).y][deadPlayerList.get(i).x].remove(deadPlayerList.get(i));
                cells[deadPlayerList.get(i).y][deadPlayerList.get(i).x].add(deadPlayerList.get(i).panel);
                playerList.add(deadPlayerList.get(i));
                deadPlayerList.remove(i);
            }
         
        revalidate();
        repaint();

    }
    
   public void beforeTurn() {
           checkRules();
            playerList = (ArrayList<Player>) mainLinkedList.get(counter).get(0);
            for (int i = 0; i < playerList.size(); i++) {
                playerList.get(i).panel.setText(Integer.toString(playerList.get(i).score));
                cells[playerList.get(i).y][playerList.get(i).x].add(playerList.get(i).panel);
            }
            
            titanList = (ArrayList<Titan>) mainLinkedList.get(counter).get(1);
            for (int i = 0; i < titanList.size(); i++) {
                titanList.get(i).panel.setText(Integer.toString(titanList.get(i).score));
                cells[titanList.get(i).y][titanList.get(i).x].add(titanList.get(i).panel);
            }
            
            obstacleList = (ArrayList<Obstacle>) mainLinkedList.get(counter).get(2);
            for (int i = 0; i < obstacleList.size(); i++) {
                cells[obstacleList.get(i).y][obstacleList.get(i).x].add(obstacleList.get(i).panel);
            }
            
            deadPlayerList = (ArrayList<Player>) mainLinkedList.get(counter).get(3);
            for (int i = 0; i < deadPlayerList.size(); i++) {
                cells[deadPlayerList.get(i).y][deadPlayerList.get(i).x].remove(deadPlayerList.get(i));
                cells[deadPlayerList.get(i).y][deadPlayerList.get(i).x].add(deadPlayerList.get(i).panel);
            }
       
        revalidate();
        repaint();
       
    }

    public void checkGame() {
        if (playerList.isEmpty() || titanList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Game Over!");
            revalidate();
            repaint();
        }
    }

}
