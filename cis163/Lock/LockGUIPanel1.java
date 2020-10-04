package viewPackage;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import modelPackage.*;

public class LockGUIPanel1 extends JPanel {

    private JButton[] buttons;

    //	private JButton button0;
    //	private JButton button1;
    //	private JButton button2;
    //	private JButton button3;
    //	private JButton button4;
    //	private JButton button5;
    //	private JButton button6;
    //	private JButton button7;
    //	private JButton button8;
    //	private JButton button9;

    private JLabel label;

    private Lock lock;
    private JButton reset;

    private String s;
    private final static int SIZE = 10;

    public LockGUIPanel1 () {
        reset = new JButton ("reset");
        lock = new Lock ("12");
        s = "";
        label = new JLabel ("Locked!!!");

        buttons = new JButton[SIZE];
        add (label);
        ButtonListener buttonListen = new ButtonListener();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("button" + i);
            buttons[i].addActionListener(buttonListen);
            add(buttons[i]);
        }
        //		button0 = new JButton("button0");
        //		button1 = new JButton("button1");
        //		button2 = new JButton("button2");
        //		button3 = new JButton("button3");
        //		button4 = new JButton("button4");
        //		button5 = new JButton("button5");
        //		button6 = new JButton("button6");
        //		button7 = new JButton("button7");
        //		button8 = new JButton("button8");
        //		button9 = new JButton("button9");

        setLayout(new GridLayout(12,1));
        add (reset);

        //		button0.addActionListener(buttonListen);
        //		button1.addActionListener(buttonListen);
        //		button2.addActionListener(buttonListen);
        //		button3.addActionListener(buttonListen);
        //		button4.addActionListener(buttonListen);
        //		button5.addActionListener(buttonListen);
        //		button6.addActionListener(buttonListen);
        //		button7.addActionListener(buttonListen);
        //		button8.addActionListener(buttonListen);
        //		button9.addActionListener(buttonListen);
        reset.addActionListener(buttonListen);

        //		add (button0);
        //		add (button1);
        //		add (button2);
        //		add (button3);
        //		add (button4);
        //		add (button5);
        //		add (button6);
        //		add (button7);
        //		add (button8);
        //		add (button9);
    }

    private void clearScreen()
    {
System.out.print('\u000C'); 
    }
    
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {			

            for (int i = 0; i < buttons.length; i++)
                if (arg0.getSource() == buttons[i])
                    s = s + i;

            //			if (arg0.getSource() == button0)
            //				s = s + "0";
            //			if (arg0.getSource() == button1)
            //				s = s + "1";
            //			if (arg0.getSource() == button2)
            //				s = s + "2";
            //			if (arg0.getSource() == button3)
            //				s = s + "3";
            //			if (arg0.getSource() == button4)
            //				s = s + "4";
            //			if (arg0.getSource() == button5)
            //				s = s + "5";
            //			if (arg0.getSource() == button6)
            //				s = s + "6";
            //			if (arg0.getSource() == button7)
            //				s = s + "7";
            //			if (arg0.getSource() == button8)
            //				s = s + "8";
            //			if (arg0.getSource() == button9)
            //				s = s + "9";
            //			
            System.out.println (s);

            if (arg0.getSource() == reset) {
                s = "";
                label.setText("Locked!!!!");
            }

            lock.enterCode(s);
            if (lock.isUnlock()) {
                label.setText("UnLocked!!!");
                clearScreen( );
            }
        }
    }
}

