package Collision;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Collision extends JFrame{
	public Collision(){
		Board board=new Board();
		add(board);
		setResizable(false);
		pack();

		setTitle("Collision");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon ii=new ImageIcon(getClass().getResource(Craft.craft));
		setIconImage(ii.getImage());
		setVisible(true);

	}

	public void addButton(Container c, String title, ActionListener listener){
		JButton button=new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

	public static void main(String[] args) {
		new Collision();

	}
}