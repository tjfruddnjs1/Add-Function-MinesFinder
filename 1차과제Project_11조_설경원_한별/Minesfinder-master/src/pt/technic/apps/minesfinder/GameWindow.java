package pt.technic.apps.minesfinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.*;
import java.util.*;
import java.awt.BorderLayout;


/**
 *
 * @author Gabriel Massadas
 */
public class GameWindow extends javax.swing.JFrame {

    
	private Minefield minefield;
    private ButtonMinefield[][] buttons;
    private RecordTable record;
    
    
    public ArrayList<Integer> recClickX = new ArrayList<>();
    public ArrayList<Integer> recClickY = new ArrayList<>();
    public  static ArrayList<Integer> recMineX = new ArrayList<>();
	public  static ArrayList<Integer> recMineY = new ArrayList<>();

    
    public static int isShow=0;
    public int recSizeX;
    public int recSizeY;
    public static int numOfMines=0;
    
    public int tmpCoordiX;
    public int tmpCoordiY;
    public int recNum=0;
    
    public boolean isCovered= false; 
    
    private JButton shieldBtn;
    private JLabel shieldLabel;     
    private JPanel btnPanel;
   
    /**
     * Creates new form GameWindow
     */
//    public boolean tmpRecClick[][]; 
    
    
    public GameWindow() {
        initComponents();
    }
    
    
    public void setSize(int x, int y) {
		recSizeX = x;
		recSizeY = y;
	}
    
    
    public void recClick(int x, int y) {
		tmpCoordiX = x;
		tmpCoordiY = y;
		recClickX.add(tmpCoordiX);
		recClickY.add(tmpCoordiY);
		recNum++;
	}
    
    public void reviewUpdateButtonsStates() {
        for (int x = 0; x < recSizeX; x++) {
            for (int y = 0; y < recSizeY; y++) {
            	int rvstate = minefield.getGridState(x, y);
                buttons[x][y].setEstado(rvstate);
            }
        }
    }
  
    
	public void showRec1() {
			isShow=1;
			for (int i=0;i<recSizeX;i++) {
				for (int j=0;j<recSizeY;j++) {
  					minefield.setMineCovered(i, j);
				}
			}
			
			reviewUpdateButtonsStates();
			
            JOptionPane.showMessageDialog(null, "지금부터 복기를 시작합니다. 새 창이 뜨면 space바를 눌러 진행해 주세요",
                    "복기", JOptionPane.INFORMATION_MESSAGE);
			recNum=recClickX.size();

			ShowRec2(recSizeX,recSizeY,recNum,recClickX,recClickY);

		}
    
    
	   public void ShowRec2(int recSizeX,int recSizeY,int recNum, ArrayList<Integer> reclickX, ArrayList <Integer> reclickY ){
		   	
		   for (int i=0;i<recNum;i++) {
				
				int x=recClickX.get(i);
				int y=recClickY.get(i);
				
				minefield.revealGrid(x, y);
				

				reviewUpdateButtonsStates();
                
				JOptionPane.showMessageDialog(null,i+"번째\n \"새 창이 뜨면\" space바를 눌러 진행해주세요",
		                   "복기", JOptionPane.INFORMATION_MESSAGE);
			}
				JOptionPane.showMessageDialog(null,"복기가 완료되었습니다",
	                   "복기", JOptionPane.INFORMATION_MESSAGE);
		  
			 setVisible(false);
	    }
	    
	   
    
    
    
    
    
    /**
     * Creates new form GameWindow
     */

    public GameWindow(Minefield minefield, RecordTable record) {
    	
    	minefield.numShield = 3;
    	 shieldBtn = new JButton("방패");   
         shieldLabel = new JLabel("3");
         btnPanel = new JPanel(new FlowLayout());
         btnPanel.add(shieldBtn);
         btnPanel.add(shieldLabel);
    	JPanel panel = new JPanel(new GridLayout(minefield.getWidth(), minefield.getHeight()));
        initComponents();
        
       
        this.minefield = minefield;
        this.record = record;

        buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];
        
        getContentPane().setLayout(new GridLayout(minefield.getWidth(),
                minefield.getHeight()));

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMinefield button = (ButtonMinefield) e.getSource();
                int x = button.getCol();
                int y = button.getLine();
                
                isCovered = true;
                
                recClickX.add(x);
                recClickY.add(y);
                
                minefield.revealGrid(x, y);
                updateButtonsStates();
                if(minefield.hasMine(x,y)) {
                    if(minefield.numShield>0) {                      
                  	  minefield.numShield -=1;
                             JOptionPane.showMessageDialog(null, "방패 사용!!",
                                        "Lost!", JOptionPane.INFORMATION_MESSAGE);
                              shieldLabel.setText(Integer.toString(minefield.numShield));                     
                       }
                 }
                if (minefield.isGameFinished()) {
                    if (minefield.isPlayerDefeated()) {
                        JOptionPane.showMessageDialog(null, "Oh, a mine broke",
                                "Lost!", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Congratulations. You managed to discover all the mines in "
                                + (minefield.getGameDuration() / 1000) + " seconds",
                                "victory", JOptionPane.INFORMATION_MESSAGE
                        );
                        
                        
                        
                        long a = minefield.getGameDuration();
                        long b = record.getScore();
                        boolean newRecord = minefield.getGameDuration() < record.getScore();

                        if (newRecord) {
                            String name = JOptionPane.showInputDialog("Enter your name");
                            if(name != "")
                                record.setRecord(name, minefield.getGameDuration());
                        }
                        int reviewOption=JOptionPane.showConfirmDialog(null, "복기하시겠습니까?", "review the Last Game", JOptionPane.YES_NO_OPTION);
                        
                        if (reviewOption==JOptionPane.YES_OPTION) {
                        	showRec1();
                        }else if(reviewOption==JOptionPane.CLOSED_OPTION) {
                        	 setVisible(false);
                        }else if(reviewOption==JOptionPane.NO_OPTION) {
                        	 setVisible(false);
                        }
                        }
                    }
                }
            
        };

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ButtonMinefield botao = (ButtonMinefield) e.getSource();
                    int x = botao.getCol();
                    int y = botao.getLine();
                    if (minefield.getGridState(x, y) == minefield.COVERED) {
                        minefield.setMineMarked(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.MARKED) {
                        minefield.setMineQuestion(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.QUESTION) {
                        minefield.setMineCovered(x, y);
                    }
                    updateButtonsStates();
                }
            }

            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        };

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                ButtonMinefield botao = (ButtonMinefield) e.getSource();
                int x = botao.getCol();//();
                int y = botao.getLine();//();
                
                try {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && y > 0) {//수정사항 1. 키보드를 이용할 수 있도록 수정
                    buttons[x][y - 1].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_UP && x > 0) {//
                    buttons[x - 1][y].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && y//
                        < minefield.getHeight() - 1) {
                    buttons[x][y + 1].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && x//
                        < minefield.getWidth() - 1) {
                    buttons[x + 1][y].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_M) {
                    if (minefield.getGridState(x, y) == minefield.COVERED) {
                        minefield.setMineMarked(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.MARKED) {
                        minefield.setMineQuestion(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.QUESTION) {
                        minefield.setMineCovered(x, y);
                    }
                    updateButtonsStates();
                }
                }catch(ArrayIndexOutOfBoundsException e1){
                    buttons[0][0].requestFocus();
                 }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        };
        
        // Create buttons for the player
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y] = new ButtonMinefield(x, y);
                buttons[x][y].addActionListener(action);
                buttons[x][y].addMouseListener(mouseListener);
                buttons[x][y].addKeyListener(keyListener);
                
                panel.add(buttons[x][y]);
                getContentPane().add(panel);
            }
        }
        buttons[0][0].requestFocus();
        getContentPane().add(btnPanel,BorderLayout.NORTH);
    }
    
    

    private void updateButtonsStates() {
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y].setEstado(minefield.getGridState(x, y));
            }
        }
        if(isCovered==true) {
        	    SoundEffect.effectplay("src/music/Beat.wav");
        }
 
         if(Minefield.isShowAllMines==true) {
        	 SoundEffect.effectplay("src/music/DeadMarine.wav");
        }
     	isCovered=false;
    }
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Game");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1094, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
