package Collision;

import Menu.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import java.util.ArrayList;

public class Board extends JPanel implements Runnable{
	public static final int B_WIDTH=550;
	public static final int B_HEIGHT=650;
	private final int DELAY=10;

	private Craft craft;
	private ArrayList<Alien> aliens;
	private Thread animator;
	private GameMenu startMenu,pauseMenu,winMenu,loseMenu;

	public static enum GSTATE{
		GAME,
		MENU
	};
	public static GSTATE gameState;
	public static boolean loseGame;
	public static boolean winGame;
	public static boolean isPaused;
	private int[][] pos={
		{29,2380},{59,2500},{89,1380},
		{109,780},{139,800},{239,880},
		{259,790},{50,760},{150,790},
		{209,980},{45,860},{70,910},
		{400,930},{80,790},{60,930},
		{59,940},{30,990},{450,920},
		{500,900},{50,760},{90,840},
		{220,810},{20,860},{180,840},
		{128,820},{170,990},{30,770}
	};

	private int count;

	// set up Panel property and KeyListener
	// initialize craft and aliens
	public Board(){
		initBoard();

	}

	public void initBoard(){
//		Add key and mouse listeners
		addKeyListener(new GameKeyAdapter());
		addMouseListener(new GameMouseAdapter());

//		Initialize panel
		setFocusable(true);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

//		Initialize objects
		craft=new Craft();
		initAlien();
		initMenu();

//		Initialize status
		loseGame=false;
		winGame=false;
		isPaused=false;
		gameState=GSTATE.MENU;

		count=0; //times count for auto fire

	}

	public void initMenu(){
		startMenu=new StartMenu("Dragon and Spiders",new String[]{"New Game","Help","Exit Game"});//		Start menu
		pauseMenu=new PauseMenu("Press P to resume",new String[]{"New Game","Exit Game"});//		Pause menu
		winMenu=new EndMenu("You win",new String[]{"Play again","Exit Game"});//		Win menu
		loseMenu=new EndMenu("Game over",new String[]{"Try again","Exit Game"});//		Game over
	}

	public void initAlien(){
		aliens=new ArrayList<Alien>();
		for (int[] po : pos) {
			aliens.add(new Alien(po[0], 750 - po[1]));

		}
	}

	@Override
	// paint game components: craft, missiles, aliens, messages
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		if (gameState==GSTATE.MENU) {
			startMenu.render(g,this);

		}else if(!loseGame && !winGame) {
			// if craft is visible, paint craft

			if (craft.isVisible()) {
				g2.drawImage(craft.getImage(),craft.getX(),craft.getY(),this);
			}

			// paint missiles
			ArrayList<Missile> ms=craft.getMissiles();
			for (Missile m : ms) {
				g2.drawImage(m.getImage(), m.getX(), m.getY(), this);

			}
			// paint aliens
			for (Alien a : aliens) {
				if (a.isVisible())
					g2.drawImage(a.getImage(), a.getX(), a.getY(), this);

			}
			// paint alien number count
			g2.setColor(Color.GREEN);
			g2.drawString("Spiders left: "+aliens.size(),5,15);

			// if paused, show pause screen
			if (isPaused) {
				pauseMenu.render(g,this);
			}

		}else{
			if (loseGame) {
				loseMenu.render(g,this);
			}else{
				winMenu.render(g,this);
			}
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();

	}


	@Override
	public void addNotify(){
		super.addNotify();
		animator=new Thread(this);
		animator.start();
	}

	// warMovement check and change status of craft, missiles and aliens
	public void warMovement(){
		// if aliens are all eliminated, game end
		if (aliens.size()==0) {
			winGame=true;
		}

		// Check missiles status of craft: 
		// if a missile is out of bound, remove it from the game
		// else, move the missile
		// missile's visibility is set in Missile.move()
		ArrayList<Missile> ms=craft.getMissiles();
		for (int i=0;i<ms.size() ;i++ ) {
			Missile m=ms.get(i);
			if (m.isVisible()) {
				m.move();
			}else{
				ms.remove(i); 
			}
		}

		// do similar for aliens, alien's visibility is set in check Collision
		for (int i=0;i<aliens.size() ;i++ ) {
			Alien a=aliens.get(i);
			if (a.isVisible()) {
				a.move();
			}else{
				aliens.remove(i); 
			}
		}

		// move the craft
		craft.move();

		// check collision
		checkCollision();

//		// repaint
//		repaint();

	}

	// check Collisions by detecting startMenu.bounds
	// if collided, check the property "visible" of craft, missiles or aliens
	// when collision, related items will be invisible at next repaint()
	public void checkCollision(){
		// get startMenu.bounds of craft
		Rectangle r3=craft.getBounds();

		// check collision of craft and aliens
		for (int i=0;i<aliens.size() ;i++ ) {
			Alien a=aliens.get(i);
			Rectangle r2=a.getBounds();
			if (r3.intersects(r2)) {
				craft.setVisible(false);
				a.setVisible(false);
				loseGame=true;
			}
		}

		// check collision of missiles and aliens
		ArrayList<Missile> ms=craft.getMissiles();
		for (int i=0;i<ms.size() ;i++ ) {
			Missile m=ms.get(i);
			Rectangle r1=m.getBounds();

			// for each missile, check collision for every alien
			// if collision, remove alien from list, this will save time for next round check
			for (Alien a : aliens) {
				Rectangle r2 = a.getBounds();
				if (a.isVisible() && r1.intersects(r2)) {
					a.setVisible(false);
					m.setVisible(false);
				}
			}
		}


	}

	@Override
	// Run the warMovement() with certain FPS as a Thread
	public void run(){
		long beforeTime, timeDiff, sleep;
		beforeTime=System.currentTimeMillis();
		int count=0;
		while(true){
			if (!isPaused && gameState==GSTATE.GAME) {
				warMovement();

				// auto fire
				if (count==20) {
					count=0;
					craft.fire();
				}
				count++;
			}
			repaint();

			timeDiff=System.currentTimeMillis()-beforeTime;
			sleep=DELAY-timeDiff;
			if (sleep<0) {
				sleep=2;
			}
			try{
				animator.sleep(sleep);
			}catch(InterruptedException e){
				System.out.println(e);
			}
			beforeTime=System.currentTimeMillis();
		}
	}

	// Handle KeyEvent
	private class GameKeyAdapter extends KeyAdapter{
		public void keyReleased(KeyEvent e){
			craft.keyReleased(e);

		}

		public void keyPressed(KeyEvent e){
			pressKey(e);
		}
	}

	public void pressKey(KeyEvent e){
		craft.keyPressed(e);
		if (e.getKeyCode()==KeyEvent.VK_P) {
			togglePause();
		}
	}

	public static void togglePause(){
		isPaused=!isPaused;
	};

	private class GameMouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			// get mouse coordinate
			int mx=e.getX();
			int my=e.getY();

			// check if the mouse is on a menu option
			// new game
			if (gameState==GSTATE.MENU){
				if (mx>startMenu.bounds.get(0)[0] && mx<startMenu.bounds.get(0)[1]) {
					if (my>startMenu.bounds.get(0)[2] && my<startMenu.bounds.get(0)[3]) {
						Board.gameState=Board.GSTATE.GAME;
					}
				}

				if (mx>startMenu.bounds.get(2)[0] && mx<startMenu.bounds.get(2)[1]) {
					if (my>startMenu.bounds.get(2)[2] && my<startMenu.bounds.get(2)[3]) {
						System.exit(1);
					}
				}
			}else{
				if (mx>pauseMenu.bounds.get(0)[0] && mx<pauseMenu.bounds.get(0)[1]) {
					if (my>pauseMenu.bounds.get(0)[2] && my<pauseMenu.bounds.get(0)[3]) {
						newGame();
					}
				}

				if (mx>pauseMenu.bounds.get(1)[0] && mx<pauseMenu.bounds.get(1)[1]) {
					if (my>pauseMenu.bounds.get(1)[2] && my<pauseMenu.bounds.get(1)[3]) {
						System.exit(1);
					}
				}
			}

		}
	}

	public void newGame(){
		initBoard();
		gameState=GSTATE.GAME;
	}

}