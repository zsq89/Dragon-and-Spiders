package Menu;

import Collision.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;

public class GameMenu{
	public String title;
	public ArrayList<String> buttons;
	public ArrayList<int[]> bounds;
	public final Color titleColor=Color.YELLOW;

	public GameMenu(String ttl, String[] btns){
		title=ttl;
		buttons=new ArrayList<String>();
		bounds=new ArrayList<int[]>();
		Collections.addAll(buttons, btns);
	}

	public void render(Graphics g, ImageObserver ob){
//		draw background
		Image craftImg,alienImg;
		ImageIcon craftII=new ImageIcon(getClass().getResource(Craft.craft));
		ImageIcon alienII=new ImageIcon(getClass().getResource(Alien.alien));
		craftImg=craftII.getImage();
		alienImg=alienII.getImage();
		g.drawImage(craftImg,(Board.B_WIDTH-32)/2, Board.B_HEIGHT-50, ob);
		for (int x=10;x< Board.B_WIDTH;x+=60) g.drawImage(alienImg,x,10,ob);
	}

	public void addTitle(Graphics g, Color color){
		Font titleFnt=new Font("arial", Font.BOLD, 40);
		FontMetrics metr=g.getFontMetrics(titleFnt);
		g.setFont(titleFnt);
		g.setColor(color);
		g.drawString(title,(Board.B_WIDTH-metr.stringWidth(title))/2, Board.B_HEIGHT/4);
	}

	public int[] addButton(Graphics g, String btnName, int y){
		Font buttonFnt=new Font("arial", Font.BOLD, 20);
		FontMetrics metr=g.getFontMetrics(buttonFnt);
		g.setFont(buttonFnt);
		g.setColor(Color.GREEN);
		int boundLx=(Board.B_WIDTH-metr.stringWidth(btnName))/2;
		int boundRx=(Board.B_WIDTH+metr.stringWidth(btnName))/2;
		int boundBy=y;
		int boundUy=y-metr.getHeight();
		g.drawString(btnName,boundLx,y);
		return new int[]{boundLx,boundRx,boundUy,boundBy};
	}

}