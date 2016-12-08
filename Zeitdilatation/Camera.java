package Zeitdilatation;

import java.awt.Graphics2D;

public class Camera {
	Vector pos = new Vector(0, 0);
	float viewportWidth, viewportHeight;
	
	public Camera(float viewportWidth, float viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
	}
	
	public void setPos(float x, float y) {
		this.pos.set(x, y);
	}
	
	public void setPos(Vector p) {
		setPos(p.x, p.y);
	}
	
	public float realToWorldX(int x) {
		return x * (viewportWidth/(float) Zeitdilatation.WIDTH) - viewportWidth/2 + pos.x;
	}
	
	public float realToWorldY(int y) {
		return (Zeitdilatation.HEIGHT - y) * (viewportHeight/Zeitdilatation.HEIGHT) - viewportHeight/2 + pos.y;
	}
	
	public int worldToRealX(float x) {
		return (int) ((Zeitdilatation.WIDTH/viewportWidth) * (x + viewportWidth/2 - pos.x));
	}
	
	public int worldToRealY(float y) {
		return Zeitdilatation.HEIGHT - (int) ((Zeitdilatation.HEIGHT/viewportHeight) * (y + viewportHeight/2 - pos.y));
	}
	
	public void drawLine(Graphics2D g2, float x1, float y1, float x2, float y2) {
		g2.drawLine(worldToRealX(x1), worldToRealY(y1), worldToRealX(x2), worldToRealY(y2));
	}
}
