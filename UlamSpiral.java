import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UlamSpiral extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 500, HEIGHT = 500;
	int k = 400;
	
	ArrayList<Integer> primes = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		JFrame fenster = new JFrame();
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setContentPane(new UlamSpiral());
		fenster.setVisible(true);
		fenster.setTitle("Ulam Spiral");
		fenster.pack();
	}
	
	public UlamSpiral() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		primes.add(2);
	}
	
	public boolean checkPrime(int p) {
		boolean isPrime = true;
		for(int i = 0; i < primes.size(); i++) {
			if(p % primes.get(i) == 0 && !primes.contains(p)) {
				isPrime = false;
				break;
			}
		}
		if(isPrime) primes.add(p);
		return isPrime;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		int xDir = 1, yDir = 1, currentX = 0, currentY = 0, w = 1, h = 1;
		int n = 1;
		
		g2.setColor(Color.WHITE);
		for(int i = 0; i < k; i++) {
			for(int x = 0; x < w; x++) {
				currentX += xDir;
				n++;
				if(checkPrime(n)) g2.fillRect(WIDTH/2 + currentX, HEIGHT/2 - currentY, 1, 1);
			}
			for(int y = 0; y < h; y++) {
				currentY += yDir;
				n++;
				if(checkPrime(n)) g2.fillRect(WIDTH/2 + currentX, HEIGHT/2 - currentY, 1, 1);
			}
			w++;
			h++;
			xDir *= -1;
			yDir *= -1;
		}
	}
	
}