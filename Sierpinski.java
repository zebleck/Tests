import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sierpinski extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1000, HEIGHT = 1000;
	
	int radius = WIDTH/3+10;
	int originX = WIDTH/2, originY = HEIGHT/2;
	float angle = -90f, dDegrees = 120f, corners = 1f;
	boolean colorize = false;
	
	Random random = new Random();
	
	List<Integer> pointX = new ArrayList<Integer>(), pointY = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		JFrame fenster = new JFrame();
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setContentPane(new Sierpinski());
		fenster.setVisible(true);
		fenster.setTitle("Shapes");
		fenster.pack();
	}
	
	public Sierpinski() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2.setColor(Color.WHITE);
		
		// dDegrees = change in Degrees;
		dDegrees = 360/corners;
		float degrees = 0f;
		
		// clear the pointLists
		pointX.clear();
		pointY.clear();
		
		// calculate and draw the cornerss
		for(int i = 0; i < corners; i++) {
			//calculates the corner coordiantes
			int oldX = originX + (int) (Math.cos(Math.toRadians(degrees+angle))*radius);
			int oldY = originY + (int) (Math.sin(Math.toRadians(degrees+angle))*radius);
			int newX = originX + (int) (Math.cos(Math.toRadians(degrees+angle+dDegrees))*radius);
			int newY = originY + (int) (Math.sin(Math.toRadians(degrees+angle+dDegrees))*radius);
			
			//stores the corner coordinates
			pointX.add(oldX);
			pointY.add(oldY);
			
			//draws a line connecting the corners
			g2.drawLine(oldX, oldY, newX, newY);
			
			//adds the change of degrees to the old degrees
			degrees += dDegrees;
			
			//draw information
			g2.drawString("-- Press C to toggle colorization, W to add a corner, S to remove a corner, R to reset --", 0, 10);
			g2.drawString("Corners: " + corners, 0, 30);
			g2.drawString("Change in degrees: " + dDegrees, 0, 40);
		}
		
		// declares p and q, starting at the first corner
		int p = pointX.get(0), q = pointY.get(0);
		
		// draws the fractal
		for(int j = 0; j < 500000; j++) {
			int z = random.nextInt(pointX.size());
			
			//sets the color according to z
			if(colorize) g2.setColor(Color.getHSBColor((dDegrees*z)/360, 1f, 1f));
			
			//calculates a new point
			p = (p+pointX.get(z))/2;
			q = (q+pointY.get(z))/2;
			g2.fillRect(p, q, 1, 1);
		}
	}

	
	@Override
	public void keyPressed(KeyEvent ev) {}

	// handles the user input
	@Override
	public void keyReleased(KeyEvent ev) {
		switch(ev.getKeyCode()) {
			case KeyEvent.VK_C:
				colorize = !colorize;
				break;
			case KeyEvent.VK_W:
				corners++;
				break;
			case KeyEvent.VK_S:
				corners--;
				break;
			case KeyEvent.VK_Q:
				corners += 0.1f;
				break;
			case KeyEvent.VK_A:
				corners -= 0.1f;
				break;
			case KeyEvent.VK_R:
				corners = 1;
				break;
			default:
				break;
		}
		corners = Math.max(0, corners);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent ev) {}
	
}