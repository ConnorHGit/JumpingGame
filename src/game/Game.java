package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import menu.AbstractMenu;
import entities.Entity;
import entities.Player;
import environment.BaseEnvironment;
import environment.Coin;
import environment.Ground;
import environment.MovingPlatform;
import environment.Platform;
import environment.WaitingPlatform;
import graphics.Images;

public class Game extends JFrame {
	/**
	 * 
	 */
	public static Game g;
	private static final long serialVersionUID = 7059646278559620203L;
	public ArrayList<BaseEnvironment> environment = new ArrayList<BaseEnvironment>();
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	private Dimension levelSize;
	public boolean aPressed;
	public boolean sPressed;
	public boolean dPressed;
	public double xOffset;
	public boolean gameStarted;
	private Image background;
	private Image dBuffer;
	private Graphics gBuffer;
	public AbstractMenu activeMenu;

	public Game() {
		super("Jumping Game");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(800, 600);
		super.setVisible(true);
		super.setResizable(false);
		gameStarted = false;
	}

	public void startGame(String levelName) {
		gameStarted = true;
		Thread render = new Thread(new Render());
		render.start();
		Thread tick = new Thread(new Tick());
		tick.start();
		entities.add(new Player(0, 0));
		loadLevel(levelName);
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					aPressed = false;
					break;
				case KeyEvent.VK_S:
					sPressed = false;
					break;
				case KeyEvent.VK_D:
					dPressed = false;
					break;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					aPressed = true;
					break;
				case KeyEvent.VK_S:
					sPressed = true;
					break;
				case KeyEvent.VK_D:
					dPressed = true;
					break;
				case KeyEvent.VK_W:
					((Player) entities.get(0)).jump();
					break;
				case KeyEvent.VK_ESCAPE:
					AbstractMenu.main.openMenu();
					gameStarted = false;
				}
			}
		});
	}

	public Dimension getLevelSize() {
		return levelSize;
	}

	public void setLevelSize(Dimension levelSize) {
		this.levelSize = levelSize;
	}

	public static void main(String[] args) {
		Game.g = new Game();
		AbstractMenu.main.openMenu();
	}

	public void paint(Graphics g) {
		if (gameStarted) {
			if (dBuffer == null) {
				dBuffer = createImage(getWidth(), getHeight());
				gBuffer = dBuffer.getGraphics();
			}
			gBuffer.drawImage(background, 0, 0, null);
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).render(gBuffer);
			}
			for (int i = 0; i < environment.size(); i++) {
				environment.get(i).render(gBuffer);
			}
			if (entities.get(0).getGround() != null)
				gBuffer.drawString(entities.get(0).getGround().toString() + "("
						+ entities.get(0).getGround().getX() + ","
						+ entities.get(0).getGround().getY() + ")", 450, 50);
			if (entities.get(0) != null) {
				gBuffer.drawString(
						String.valueOf(((Player) entities.get(0)).coins), 50,
						50);
				gBuffer.drawString(String.valueOf((int)entities.get(0).getX()), 25, 100);
				gBuffer.drawString(String.valueOf((int)entities.get(0).getY()),75,100);
			}
			g.drawImage(dBuffer, 0, 0, null);
		} else {
			super.paint(g);
		}
	}

	public void loadLevel(String levelName) {
		File level = null;
		try {
			level = new File(getClass().getResource("saves/" + levelName)
					.toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		System.out.println(level.getAbsolutePath());
		if (!level.exists()) {
			System.out.println("Level does not exist");
			return;
		}
		BufferedReader levelReader = null;
		try {
			levelReader = new BufferedReader(new FileReader(level));
			String levelLength = levelReader.readLine();
			levelSize = new Dimension(Integer.parseInt(levelLength),
					getHeight());
			environment.add(new Ground());
			String[] playerSpawn = levelReader.readLine().split(" ");
			entities.get(0).setX(Double.parseDouble(playerSpawn[0]));
			entities.get(0).setY(Double.parseDouble(playerSpawn[1]));
			background = ImageIO.read(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(levelReader.readLine()));
			while (true) {
				String[] entry = levelReader.readLine().split(" ");
				if (entry[0].equals("END")) {
					levelReader.close();
					return;
				}
				System.out.println(Arrays.toString(entry));
				// Entity
				if (entry[0].equals("0")) {
					switch (Integer.parseInt(entry[1])) {
					case 0: // Platform
						environment.add(new Platform(Double
								.parseDouble(entry[2]), Double
								.parseDouble(entry[3]), Integer
								.parseInt(entry[4])));
						break;
					case 1: // Moving Platform
						environment.add(new MovingPlatform(Double
								.parseDouble(entry[2]), Double
								.parseDouble(entry[3]), Double
								.parseDouble(entry[4]), Double
								.parseDouble(entry[5]), Integer
								.parseInt(entry[6]),
								Integer.parseInt(entry[7]), Integer
										.parseInt(entry[8]), Integer
										.parseInt(entry[9]), Integer
										.parseInt(entry[10])));
						break;
					case 2:
						environment.add(new WaitingPlatform(Double
								.parseDouble(entry[2]), Double
								.parseDouble(entry[3]), Double
								.parseDouble(entry[4]), Double
								.parseDouble(entry[5]), Integer
								.parseInt(entry[6]),
								Integer.parseInt(entry[7]), Integer
										.parseInt(entry[8]), Integer
										.parseInt(entry[9]), Integer
										.parseInt(entry[10])));
						break;
					case 3:
						environment.add(new Coin(Double.parseDouble(entry[2]),
								Double.parseDouble(entry[3])));
					}
				} else if (entry[0].equals("1")) {

				}
				System.out.println(environment.get(environment.size() - 1));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static InputStream getResource(String path){
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		
	}
}
