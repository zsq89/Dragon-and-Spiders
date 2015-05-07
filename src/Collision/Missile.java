package Collision;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Missile{
	private final int M_SPEED=5;
	private final int BOARD_HEIGHT=10;

	private int x,y;
	private int width,height;
	private Image image;
	private boolean visible;

	public Missile(int x, int y){
		this.x=x;
		this.y=y;
		ImageIcon ii=new ImageIcon(getClass().getResource("../image/fireball.png"));
		image=ii.getImage();
		width=image.getWidth(null);
		height=image.getHeight(null);
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

	public boolean isVisible(){
		return visible;
	}

	public void setVisible(boolean visible){
		this.visible=visible;
	}

	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);

	}

	public void move(){
		y-=M_SPEED;
		if (y<BOARD_HEIGHT) {
			visible=false;
		}
	}



}