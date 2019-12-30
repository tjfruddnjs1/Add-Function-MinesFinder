package pt.technic.apps.minesfinder;

import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author Gabriel Massadas
 */
public class ButtonMinefield extends JButton {
	
    private int state, col, line;
    
    GameWindow sound = new GameWindow();//수정사항 2 효과음 넣기

    public ButtonMinefield(int col, int line) {
        this.col = col;
        this.line = line;
        state=Minefield.COVERED;
    }
    
    public void setEstado(int state) {
        this.state=state;
        switch (state) {
            case Minefield.EMPTY:
                setText("");
                setBackground(Color.gray);           
                break;
            case Minefield.COVERED:
                setText("");
                setBackground(null);
                break;
            case Minefield.QUESTION:
                setText("?");
                setBackground(Color.yellow);
                break;
            case Minefield.MARKED:
                setText("!");
                setBackground(Color.red);
                break;
            case Minefield.BUSTED:
                setText("*");
                setBackground(Color.orange);
                if(Minefield.isShowAllMines==false) {
                SoundEffect.effectplay("src/music/DeadMarine.wav");//수정사핟2. 효과음 넣기
                }
                break;
            default:
                setText(String.valueOf(state));
                setBackground(Color.gray);
                break;
        }
    }

    public int getState() {
        return state;
    }

    public int getCol() {
        return col;
    }

    public int getLine() {
        return line;
    }
    
    
}
