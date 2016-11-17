package Mandelbrot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
 
public class Mandelbrot extends JPanel implements MouseListener, KeyListener 
{
    private static final long serialVersionUID = 1L;
     
    public static final double WIDTH = 800, HEIGHT = 800;
    public static double VWIDTH = 3, VHEIGHT = 3, VMOVEX = -0.5f, VMOVEY = 0;
    public static final double BORDER = 2;
    private int k = 0; 
    
    public static void main(String[] args) {
        JFrame fenster = new JFrame();
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setContentPane(new Mandelbrot());
        fenster.setVisible(true);
        fenster.setTitle("Mandelbrot");
        fenster.pack();
    }
     
    public Mandelbrot() {
        this.setPreferredSize(new Dimension((int) WIDTH, (int) HEIGHT));
         
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
		this.requestFocusInWindow();
    }
     
    public static double wToVW(int w) {
        return (VWIDTH/(double) WIDTH) * w - VWIDTH/2 + VMOVEX;
    }
     
    public static double hToVH(int h) {
        return (VHEIGHT/(double) HEIGHT) * h - VHEIGHT/2 + VMOVEY;
    }
     
    public static double VWToW(double w) {
        return ( WIDTH / VWIDTH) * (w-VMOVEX) + WIDTH/2;
    }
     
    public static double VHToH(double h) {
        return ( HEIGHT / VHEIGHT) * (h-VMOVEY) + HEIGHT/2;
    }
     
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, (int) WIDTH, (int) HEIGHT);
         
        Complex cZero = new Complex(0, 0);
        Complex c = new Complex(0, 0);
        Complex cIt = new Complex(0, 0);
        g2.setColor(Color.WHITE);
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                c.x = wToVW(x);
                c.y = hToVH(y);
                cIt.x = cZero.x;
                cIt.y = cZero.y;
                for(int i = 0; i < k; i++) {
                    cIt.multiply(cIt);
                    cIt.add(c);
                    if(cIt.abs() >= BORDER) {
                        g2.setColor(Color.getHSBColor(0.01f*i, 1f, 1f));
                        g2.fillRect(x, y, 1, 1);
                        break;
                    }
                }
            }
        }
    }
     
    public void mousePressed(MouseEvent e) {
    }
 
    int x1, y1, x2, y2;
    public void mouseReleased(MouseEvent e) {
       if(x1 == 0 && y1 == 0) {
           x1 = e.getX();
           y1 = e.getY();
       } else {
           x2 = e.getX();
           y2 = e.getY();

           VMOVEX = wToVW(x1) + (wToVW(x2) - wToVW(x1))/2;
           VWIDTH = wToVW(x2) - wToVW(x1);

           System.out.println(hToVH(y2) - hToVH(y1));
           
           VMOVEY = hToVH(y1) + (hToVH(y2) - hToVH(y1))/2;
           VHEIGHT = hToVH(y2) - hToVH(y1);
            
           x1 = 0;
           y1 = 0;
            
           repaint();
       }
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {}
 
    @Override
    public void mouseExited(MouseEvent e) {}
 
    @Override
    public void mouseClicked(MouseEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			k += 10;
			repaint();
			break;
		case KeyEvent.VK_R:
			k = 0;
			VWIDTH = 3;
			VHEIGHT = 3;
			VMOVEX = -0.5f;
			VMOVEY = 0;
			repaint();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}