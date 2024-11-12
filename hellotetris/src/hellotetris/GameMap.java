package hellotetris;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GameMap extends JPanel implements KeyListener {
	private static final long serialVersionUID = -6839529341001166798L;
	public static int WIDTH = 600; //размер окна
	public static int HEIGHT = 500;
	public static int H = 20; //размер самой клетки
	public static int M = 20; //высота поля в клетках Y
	public static int N = 20; //ширина поля в клетках X
//	protected ArrayList<Apple> items;
	protected ArrayList<Snake> snake;
	public Apple apple;
//	protected Tank tank;
	private int SnakeDirectionX = 0, SnakeDirectionY = -1; //Начальное направление змеи
	public int AppleCounter = 0; //счетчик очков
	protected Timer timer;
	protected HashMap<Integer, Boolean> pressedKeys;

	public GameMap() {
		System.out.println("GameMap instance created...");
		//начальное положение танка
		this.snake = new ArrayList<Snake>();
		this.snake.add(new Snake(10, 10));
		this.snake.add(new Snake(10, 11));
		this.snake.add(new Snake(10, 12));
		for (Snake snake : snake) {
			snake.setColor(Color.BLUE);
		}
		
		//начальное положение яблок
//		this.apple = new ArrayList<Apple>();
//		this.items.add(new Tank(Randomizer.getInt(1, N), Randomizer.getInt(1, M)));
		this.apple = new Apple(10, 8);
		
		
		this.pressedKeys = new HashMap<Integer, Boolean>();
		this.pressedKeys.put(KeyEvent.VK_W, false);
		this.pressedKeys.put(KeyEvent.VK_A, false);
		this.pressedKeys.put(KeyEvent.VK_S, false);
		this.pressedKeys.put(KeyEvent.VK_D, false);

		timer = new Timer(1000 / 10, e -> {
            update();
            repaint();
        });
        timer.start();
	}

	protected void update() {	
		//движение змейки по заданному направлению		
		if (pressedKeys.get(KeyEvent.VK_W)) { // up
			if (SnakeDirectionY != 1) {
				SnakeDirectionX = 0;
				SnakeDirectionY = -1;
			}
		}
		if (pressedKeys.get(KeyEvent.VK_A)) { // left
			if (SnakeDirectionX != 1) {
				SnakeDirectionX = -1;
				SnakeDirectionY = 0;
			}
		}
		if (pressedKeys.get(KeyEvent.VK_S)) { // down
			if (SnakeDirectionY != -1) {
				SnakeDirectionX = 0;
				SnakeDirectionY = 1;
			}
		}
		if (pressedKeys.get(KeyEvent.VK_D)) { // right
			if (SnakeDirectionX != -1) {
				SnakeDirectionX = 1;
				SnakeDirectionY = 0;
			}
		}
		
		Snake head = new Snake(snake.get(0).x, snake.get(0).y);
//		System.out.println("("+head.x+", "+head.y+")");
		
		
		if (snake.get(0).x > 1 && snake.get(0).x < M && snake.get(0).y > 1 && snake.get(0).y < H) {
				for (int i = 0; i < snake.size() - 1; i++) {
					snake.get(i + 1).x = snake.get(i).x;
					snake.get(i + 1).y = snake.get(i).y;
				}
			head.x += SnakeDirectionX;
			head.y += SnakeDirectionY;
		}
		
		int a =0;
		for (Snake snake: snake) {
			
			System.out.println(a + " (" + snake.x + ", " + snake.y + ")");
			a++;
		}
		
		if (apple.y == snake.get(0).y && apple.x == snake.get(0).x) {
			apple.y = Randomizer.getInt(1, M);
			apple.x = Randomizer.getInt(1, N);
			snake.add(new Snake(apple.x + SnakeDirectionX, apple.y + SnakeDirectionY));

//			int a =0;
//			for (Snake snake: snake) {
//				
//				System.out.println(a + " (" + snake.x + ", " + snake.y + ")");
//				a++;
//			}
			
			AppleCounter += 1;
//			System.out.println("Счет:" + snake.size());	
		}
	}
	

	protected void render(Graphics g) {
		int y = 0;
		for (int j = 1; j <= M; j++) { // up->down

			y = j;
			for (int i = 1; i <= N; i++) { // left->right
				int x = i;
				drawSquare(g, x, y, Color.YELLOW, Color.BLACK);
			}
		}
		
		apple.render(g);
		
		for (Snake snake : snake) {
			snake.render(g);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.render(g);
	}

	protected void drawSquare(Graphics g, int x, int y, Color lineColor, Color fillColor) {
		g.setColor(lineColor);
		g.fillRect(x * H, y * H, H, H);
		g.setColor(fillColor);
		g.drawRect(x * H, y * H, H, H);
	}

	public void keyPressed(KeyEvent e) {
		// char c = e.getKeyChar();
		// int code = e.getKeyCode();
		// String s = "keyPressed: [" + c + "] (" + String.valueOf(code) + ")";
		// System.out.println(s);
		
		int code = e.getKeyCode();
//		System.out.println("keyCode: " + code);

		switch (code) {
		case KeyEvent.VK_W:
			this.pressedKeys.put(KeyEvent.VK_W, true);
//			System.out.println(pressedKeys + "ПРОВЕРКА");
			break;
		case KeyEvent.VK_A:
			this.pressedKeys.put(KeyEvent.VK_A, true);
			break;
		case KeyEvent.VK_S:
			this.pressedKeys.put(KeyEvent.VK_S, true);
			break;
		case KeyEvent.VK_D:
			this.pressedKeys.put(KeyEvent.VK_D, true);
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_W:
			this.pressedKeys.put(KeyEvent.VK_W, false);
			break;
		case KeyEvent.VK_A:
			this.pressedKeys.put(KeyEvent.VK_A, false);
			break;
		case KeyEvent.VK_S:
			this.pressedKeys.put(KeyEvent.VK_S, false);
			break;
		case KeyEvent.VK_D:
			this.pressedKeys.put(KeyEvent.VK_D, false);
			break;
		}
	}

	public void keyTyped(KeyEvent e) {

	}
}
