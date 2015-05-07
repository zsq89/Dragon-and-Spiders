package Collision;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import java.util.ArrayList;

public class Craft{
	private final int SPEED=3;
	private final int INIT_X=275;
	private final int INIT_Y=500;

	public static String craft="../image/dragon.png";
	private int x,y;
	private int dx,dy;
	private int width,height;
	private Image image;

	private boolean visible;
	private ArrayList<Missile> missiles;

	public Craft(){
		ImageIcon ii=new ImageIcon(getClass().getResource(craft));
		image=ii.getImage();
		x=INIT_X;
		y=INIT_Y;
		width=image.getWidth(null);
		height=image.getHeight(null);
		missiles=new ArrayList<Missile>();
		visible=true;
	}


	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Image getImage(){
		return image;
	}

	public ArrayList getMissiles(){
		return missiles;
	}

	public boolean isVisible(){
		return visible;
	}

	public void setVisible(boolean visible){
		this.visible=visible;
	}

	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);

	}

	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		// if (key==KeyEvent.VK_SPACE) {
		// 	fire();
		// }
		if (key==KeyEvent.VK_LEFT) {
			dx=-SPEED;
		}
		if (key==KeyEvent.VK_RIGHT) {
			dx=SPEED;
		}
		if (key==KeyEvent.VK_UP) {
			dy=-SPEED;
		}
		if (key==KeyEvent.VK_DOWN) {
			dy=SPEED;
		}

	}

	public void keyReleased(KeyEvent e){
		int key=e.getKeyCode();
		if (key==KeyEvent.VK_LEFT) {
			dx=0;
		}
		if (key==KeyEvent.VK_RIGHT) {
			dx=0;
		}
		if (key==KeyEvent.VK_UP) {
			dy=0;
		}
		if (key==KeyEvent.VK_DOWN) {
			dy=0;
		}

	}

	public void move(){
		int tempx, tempy;
		tempx=x+dx;
		tempy=y+dy;
		if (tempx>1 && (tempx+width)<Board.B_WIDTH) {
			x=tempx;
		}
		if ((tempy-height)>1 && tempy<(Board.B_HEIGHT-30)) {
			y=tempy;
		}
	}

	// fire missiles
	public void fire(){
		//put missile in middle and front of the craft
		missiles.add(new Missile(x+width/2,y-height));

	}

}