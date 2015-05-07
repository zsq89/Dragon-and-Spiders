package Collision;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Alien{
	private final int A_SPEED_X=2;
	private final int A_SPEED_Y=2;
	public static String alien="../image/spider2.png";

	private int x,y;
	private int dx,dy;
	private int width,height;
	private Image image;
	private boolean visible;

	public Alien(int x, int y){
		this.x=x;
		this.y=y;
		dx=A_SPEED_X;
		dy=A_SPEED_Y;
		ImageIcon ii=new ImageIcon(getClass().getResource(alien));
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
		if (y>750) {
			y=0;
		}
		if (x<0 || x>550) {
			dx=-dx;
		}
		y+=dy;
		x+=dx;
	}


}