package pt.technic.apps.minesfinder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class MultiGameWindow  extends javax.swing.JFrame{
	
//	private Minefield minefield;
    private ButtonMinefield[][] buttons;
    private MultiMinefield multiminefield;
    CountTimerGUI timer;
//    private RecordTable record;
    Random r = new Random();
   	public int random_1;
   	public int random_2;
   	JTextField user1 = new JTextField(5);
   	JTextField user2 = new JTextField(5);
//   	public static boolean winFlag = false;
   
    public int result_user ;
   
    public int winner_flag = 0;
    public Boolean isCovered=false; 
    
    public int playingUser;
   
    
    
    

public MultiGameWindow(MultiMinefield minefield) {
	initComponents();
	
	JPanel match = new JPanel();
	match.add(new JLabel("user1"));
	match.add(user1);
	match.add(Box.createHorizontalStrut(20));
	match.add(new JLabel("user2"));
	match.add(user2);
	match.add(Box.createHorizontalStrut(20));
	
	
	
//  this.record = record;
	result_user = JOptionPane.showConfirmDialog(null, match, "Input user name [Within 5 letters]", JOptionPane.OK_CANCEL_OPTION);
	
	
	
	random_1=r.nextInt(10)%2;
	
	if (random_1==0) {
		random_2=1;
	}
	else if(random_1==1) {
		random_2=0;
	}
	if(random_1 > random_2) {
    	JOptionPane.showMessageDialog(null, user1.getText() + "is first turn. [W:↑] [A:←] [S:↓] [D:→] [E:change button states] [Space:click button] ", "순서 정하기", JOptionPane.INFORMATION_MESSAGE);
    	
    }
    else 					 {
    	JOptionPane.showMessageDialog(null, user2.getText() + "is first turn. [방향키] [M:change button states] [Space:click button]", "순서 정하기", JOptionPane.INFORMATION_MESSAGE);
    }
	
	
	  
	  
	
	buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];
	
      getContentPane().setLayout(new GridLayout(minefield.getWidth(), minefield.getHeight()));
      
      ActionListener action = new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              ButtonMinefield button = (ButtonMinefield) e.getSource();
              int x = button.getCol();
              int y = button.getLine();
              
        		winner_flag++;
              
              minefield.revealGrid(x, y);
              multiupdateButtonsStates(minefield);
              if (minefield.isGameFinished()) {
            	      
                  if (minefield.isPlayerDefeated()) {
                	  
                	  if(random_1 > random_2 && winner_flag %2 == 0)
                      JOptionPane.showMessageDialog(null, user1.getText() + "is Victory!", "Congratulantion", JOptionPane.INFORMATION_MESSAGE);
                	  else if(random_1 > random_2 && winner_flag %2 == 1)
                	  JOptionPane.showMessageDialog(null, user2.getText() + "is Victory!", "Congratulantion", JOptionPane.INFORMATION_MESSAGE);	  
                	  else if(random_1 < random_2 && winner_flag %2 ==0)
                	  JOptionPane.showMessageDialog(null, user2.getText() + "is Victory!", "Congratulantion", JOptionPane.INFORMATION_MESSAGE);  
                	  else if(random_1 < random_2 && winner_flag %2 ==1)
                      JOptionPane.showMessageDialog(null, user1.getText() + "is Victory!", "Congratulantion", JOptionPane.INFORMATION_MESSAGE);
                  } else {
                      JOptionPane.showMessageDialog(null, "Tied", "Tied", JOptionPane.INFORMATION_MESSAGE);
//                      long a = minefield.getGameDuration();
//                      long b = record.getScore();
//                      boolean newRecord = minefield.getGameDuration() < record.getScore();

//                      if (newRecord) {
//                          String name = JOptionPane.showInputDialog("Enter your name");
//                          if(name != "")
//                              record.setRecord(name, minefield.getGameDuration());
//                      }
                  }
                  setVisible(false);
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
                  multiupdateButtonsStates(minefield);
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
              int x = botao.getCol();
              int y = botao.getLine();
              if (e.getKeyCode() == KeyEvent.VK_W && x > 0) {
                  buttons[x - 1][y].requestFocus();
              } else if (e.getKeyCode() == KeyEvent.VK_A && y > 0) {
                  buttons[x ][y - 1].requestFocus();
              } else if (e.getKeyCode() == KeyEvent.VK_S && x < minefield.getWidth() - 1) {
            	  buttons[x + 1][y].requestFocus();
              } else if (e.getKeyCode() == KeyEvent.VK_D && y < minefield.getHeight() - 1 ) {
                  buttons[x][y + 1].requestFocus();
              }
                else if(e.getKeyCode() == KeyEvent.VK_E) {
                	 if (minefield.getGridState(x, y) == minefield.COVERED) {
                         minefield.setMineMarked(x, y);
                     } 
                     else if (minefield.getGridState(x, y) == minefield.MARKED) {
                         minefield.setMineQuestion(x, y);
                     } else if (minefield.getGridState(x, y) == minefield.QUESTION) {
                         minefield.setMineCovered(x, y);
                     }
          
                	 multiupdateButtonsStates(minefield);
                }
              
                else if (e.getKeyCode() == KeyEvent.VK_UP && x > 0) {
                  buttons[x-1][y ].requestFocus();
              } else if (e.getKeyCode() == KeyEvent.VK_LEFT && y > 0) {
                  buttons[x ][y-1].requestFocus();
              } else if (e.getKeyCode() == KeyEvent.VK_DOWN && x
                      < minefield.getWidth() - 1) {
                  buttons[x+1][y ].requestFocus();
              } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && y
                      < minefield.getHeight() - 1) {
                  buttons[x ][y+1].requestFocus();
              }
              	
              	else if (e.getKeyCode() == KeyEvent.VK_M) {
                  if (minefield.getGridState(x, y) == minefield.COVERED) {
                      minefield.setMineMarked(x, y);
                  } 
                  else if (minefield.getGridState(x, y) == minefield.MARKED) {
                      minefield.setMineQuestion(x, y);
                  } else if (minefield.getGridState(x, y) == minefield.QUESTION) {
                      minefield.setMineCovered(x, y);
                  }
                  multiupdateButtonsStates(minefield);
              }
              	else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//              		winner_flag++;
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
              getContentPane().add(buttons[x][y]);
          }
      }
}

      


      private void multiupdateButtonsStates(MultiMinefield minefield) {
          for (int x = 0; x < minefield.getWidth(); x++) {
              for (int y = 0; y < minefield.getHeight(); y++) {
                  buttons[x][y].setEstado(minefield.getGridState(x, y));
              }
          }
          if(isCovered=true) {
              SoundEffect.effectplay("src/music/Beat.wav");
          }
          if(Minefield.isShowAllMines==true) {
         	 SoundEffect.effectplay("src/music/DeadMarine.wav");
         }
      }
      
     
      
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
      
      

      
      
      static void main(String args[]) {
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
}