package Zeitdilatation;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Zeitdilatation extends JPanel implements MouseListener, KeyListener 
{
    private static final long serialVersionUID = 1L;
     
    public static final int WIDTH = 1000, HEIGHT = 1000;
    
    LightClock clock, clock2;
    Camera cam;
    
    boolean movingRight;
    
    long lastTime = System.nanoTime();
    private float delta = 0;
    
    public static void main(String[] args) {
        JFrame fenster = new JFrame();
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setContentPane(new Zeitdilatation());
        fenster.setVisible(true);
        fenster.setTitle("Zeitdilatation");
        fenster.pack();
    }
     
    public Zeitdilatation() {
        this.setPreferredSize(new Dimension((int) WIDTH, (int) HEIGHT));
         
        cam = new Camera(10, 4f);
        clock = new LightClock(0, 0, 0.2f, 0.5f);
        clock2 = new LightClock(-5f, 1f, 0.2f, 0.5f);
        clock2.vel.x = 0.9f;
        
        new Thread(new Thread() {
        	 @Override
    		public void run() {
    			while(true) {
    				long newTime = System.nanoTime();
    		    	delta = (float) (newTime - lastTime)/1000000000;
    		    	lastTime = newTime;
    				
    				update(delta);
    				paintImmediately(0, 0, WIDTH, HEIGHT);
    			}
    		}
        }).start();
        
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
		this.requestFocusInWindow();
    }
     
    public void update(float delta) {
    	clock.update(delta);
    	clock2.update(delta);
    }
    
    static int x = 0;
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, (int) WIDTH, (int) HEIGHT);
        
        g2.setStroke(new BasicStroke(2));
        clock.draw(g2, cam);
        clock2.draw(g2, cam);
        
        g2.setColor(Color.WHITE);
        g2.drawString("FPS: " + 1f / delta, 0, 10);
    }
     
    public void mousePressed(MouseEvent e) {
    	input.set(cam.realToWorldX(e.getX()), cam.realToWorldY(e.getY()));
    	if(clock.contains(input)) {
    		cam.setPos(clock.pos);
    		clock2.vel.x = -clock.vel.x;
    		clock.vel.x = 0;
    		clock.time = 0;
    		clock2.time = 0;
    	} else if(clock2.contains(input)) {
    		cam.setPos(clock2.pos);
    		clock.vel.x = -clock2.vel.x;
    		clock2.vel.x = 0;
    		clock.time = 0;
    		clock2.time = 0;
    	}
    }
 
    public void mouseReleased(MouseEvent e) {}
 
    Vector input = new Vector();
    @Override
    public void mouseEntered(MouseEvent e) {}
 
    @Override
    public void mouseExited(MouseEvent e) {}
 
    @Override
    public void mouseClicked(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			clock.vel.x = -0.995f;
			break;
		case KeyEvent.VK_RIGHT:
			clock.vel.x = 0.995f;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			clock.vel.x = 0f;
			break;
		case KeyEvent.VK_RIGHT:
			clock.vel.x = 0f;
			break;
		default:
			break;
		}
	}

}