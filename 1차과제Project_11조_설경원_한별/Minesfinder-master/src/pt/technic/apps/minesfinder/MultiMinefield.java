package pt.technic.apps.minesfinder;

import java.util.Random;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiMinefield {
	
    public static final int EMPTY = 0;
    // from 1 to 8 is the number of mines around
    public static final int COVERED = 9;
    public static final int QUESTION = 10;
    public static final int MARKED = 11;
    public static final int BUSTED = 12;

    private boolean[][] mines;
    private int[][] states;
    private int width;
    private int height;
    private int numMines;
    private Random random;

    private boolean firstPlay;
    private boolean playerDefeated;
    private boolean gameFinished;
//    private boolean is1playerwin;
//    private boolean is2playerwin;
    
    private long timeGameStarted;
    private long timeGameDuration;
    
    JTextField multiwidth = new JTextField(2);
   	JTextField multiheight = new JTextField(2);
   	JTextField multiMines = new JTextField(2);
    
    
    public MultiMinefield() {
    	
    	JPanel userchoosemap = new JPanel();
    	userchoosemap.add(new JLabel("width"));
    	userchoosemap.add(multiwidth);
    	userchoosemap.add(Box.createHorizontalStrut(20));
    	userchoosemap.add(new JLabel("height"));
    	userchoosemap.add(multiheight);
    	userchoosemap.add(Box.createHorizontalStrut(20));
    	userchoosemap.add(new JLabel("numMines"));
    	userchoosemap.add(multiMines);
    	userchoosemap.add(Box.createHorizontalStrut(20));
    	
    //  this.record = record;
    	JOptionPane.showConfirmDialog(null, userchoosemap, "Input mine's width, height, numMines [within two digits]", JOptionPane.OK_CANCEL_OPTION);
        
    	width = Integer.parseInt(multiwidth.getText());
    	height = Integer.parseInt(multiheight.getText());
    	numMines = Integer.parseInt(multiMines.getText());
    	
    	if(numMines<=0){
            throw new IllegalArgumentException("Mines nuumber must be bigger than 0");
        }
               
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        mines = new boolean[width][height];
        states = new int[width][height];

        random = new Random();

        firstPlay = true;
        playerDefeated = false;
        gameFinished = false;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                states[x][y] = COVERED;
            }
        }
    }

    public void revealGrid(int x, int y) {
        if (states[x][y] == COVERED && !gameFinished) {
            if (firstPlay) {
                firstPlay = false;
                placeMines(x, y);
                timeGameStarted=System.currentTimeMillis();
            }

            if (mines[x][y]) {
                states[x][y] = BUSTED;
                
                for(int i=0;i<width;i++) {
                	
                	for(int j=0;j<height;j++)
                		
                		if(states[i][j] == COVERED)  
                			if(mines[i][j]) states[i][j] = BUSTED;
                
                }
             
                playerDefeated = true;
                gameFinished = true;
                timeGameDuration=System.currentTimeMillis()-timeGameStarted;
                return;
            }

            int minesAround = countMinesAround(x, y);
            states[x][y] = minesAround;

            if (minesAround == 0) {
                revealGridNeighbors(x, y);
            }
            
            if(checkVictory()) {
                gameFinished=true;
                playerDefeated=false;
                timeGameDuration=System.currentTimeMillis()-timeGameStarted;
                return;
            }
        }
    }
    
    public long getGameDuration(){
        if(firstPlay){
            return 0;
        }
        if(!gameFinished){
            return System.currentTimeMillis()-timeGameStarted; 
        }
        return timeGameDuration;
    }

    private void revealGridNeighbors(int x, int y) {
        for (int col = Math.max(0, x - 1); col < Math.min(width, x + 2); col++) {
            for (int line = Math.max(0, y - 1); line < Math.min(height, y + 2); line++) {
            	revealGrid(col, line);
            }
        }
    }

    public void setMineMarked(int x, int y) {
        if (states[x][y] == COVERED || states[x][y] == QUESTION) {
            states[x][y] = MARKED;
        }
    }

    public void setMineQuestion(int x, int y) {
        if (states[x][y] == COVERED || states[x][y] == MARKED) {
            states[x][y] = QUESTION;
        }
    }

    public void setMineCovered(int x, int y) {
        if (states[x][y] == MARKED || states[x][y] == QUESTION) {
            states[x][y] = COVERED;
        }
    }

    private boolean checkVictory() {
        boolean victory = true;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!mines[x][y]) {
                    victory = victory && states[x][y] >= 0 && states[x][y] < 9;
                }
            }
        }
        return victory;
    }

    private int countMinesAround(int x, int y) {
        int result = 0;
        for (int col = Math.max(0, x - 1); col < Math.min(width, x + 2); col++) {
            for (int line = Math.max(0, y - 1); line < Math.min(height, y + 2); line++) {
                if (mines[col][line]) {
                    result++;
                }
            }
        }
        return result - (mines[x][y] ? 1 : 0);
    }

    public boolean isPlayerDefeated() {
        return playerDefeated;
    }
    
//    public boolean is1playerwin() {
//    	return is1playerwin;
//    }
//    
//    public boolean is2playerwin() {
//    	return is2playerwin;
//    }
    
    public boolean isGameFinished() {
        return gameFinished;
    }

    private void placeMines(int plX, int plY) {
        // the plX and plY is the player's first play
        for (int i = 0; i < numMines; i++) {
            int x = 0;
            int y = 0;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (mines[x][y] || (x == plX && y == plY));
            mines[x][y] = true;
        }
    }

    public int getGridState(int x, int y) {
        return states[x][y];
    }

    public boolean hasMine(int x, int y) {
        return mines[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumMines() {
        return numMines;
    }
    
}
