package pt.technic.apps.minesfinder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class CountTimerGUI implements ActionListener {

    
	private JFrame frame;
    private JPanel panel;
    JPanel timer = new JPanel();

    private JLabel timeLabel = new JLabel();

    private JButton startBtn = new JButton("Start");
    private JButton pauseBtn = new JButton("Pause");
    private int result_time ;
    private int reply_timerover;
    private int reply_timerover_message;
    JTextField time = new JTextField(10);
   
    public CountTimer cntd;

    public CountTimerGUI() {
        setTimerText("         ");
        GUI();
    }

    public void GUI() {
        frame = new JFrame();
        panel = new JPanel();

        panel.setLayout(new BorderLayout());
        timeLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.add(timeLabel, BorderLayout.NORTH);

        startBtn.addActionListener(this);
        pauseBtn.addActionListener(this);

        JPanel cmdPanel = new JPanel();
        cmdPanel.setLayout(new GridLayout());

        cmdPanel.add(startBtn);
        cmdPanel.add(pauseBtn);

        panel.add(cmdPanel, BorderLayout.SOUTH);

        JPanel clrPanel = new JPanel();
        clrPanel.setLayout(new GridLayout(0,1));

        panel.add(clrPanel, BorderLayout.EAST);
        
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        cntd = new CountTimer();                    	
    }

    private void setTimerText(String sTime) {
        timeLabel.setText(sTime);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        JButton btn = (JButton) e.getSource();
        
        if (btn.equals(startBtn))   { cntd.start(); }
        else if (btn.equals(pauseBtn))   { cntd.pause(); }
    }


    public class CountTimer implements ActionListener {

        private static final int ONE_SECOND = 1000;
        public int count = 0;
        private boolean isTimerActive = false;
        private Timer tmr = new Timer(ONE_SECOND, this);

        public CountTimer() {
        	timer.add(new JLabel("Time"));
        	timer.add(time);
        	
        	result_time = JOptionPane.showConfirmDialog(null, time, "Input Setting Time (Seconds) [Within 10 letters]" , JOptionPane.OK_CANCEL_OPTION);
            count = Integer.parseInt(time.getText());
            setTimerText(TimeFormat(count));
            
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	if (isTimerActive) {
                count--;
                setTimerText(TimeFormat(count));
                if(count == 0 ) {
                	reply_timerover = JOptionPane.showConfirmDialog(null, " Are you playing continuously ", "Continuously?", JOptionPane.YES_NO_OPTION  );
                	if(reply_timerover == JOptionPane.YES_OPTION) {
             
                	frame.setVisible(false); 
                }
                	else if(reply_timerover == JOptionPane.NO_OPTION) {
                		MinesFinder.gameWindow.setVisible(false);
                		MinesFinder.multigameWindow.setVisible(false);
                	}
                	}
            }
        }

        public void start() {
            isTimerActive = true;
            tmr.start();
        }

        public void pause() {
            isTimerActive = false;
        }


   }
        


    private String TimeFormat(int count) {

        int hours = count / 3600;
        int minutes = (count-hours*3600)/60;
        int seconds = count-minutes*60;

        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
}
