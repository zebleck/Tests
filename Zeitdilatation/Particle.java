package Zeitdilatation;

public class Particle {
	
	Vector point = new Vector();
	float alpha, hideSpeed;
	
	public Particle(float x, float y, float hideSpeed) {
		this.point.set(x, y);
		this.alpha = 1f;
		this.hideSpeed = hideSpeed;
	}
	
	public void update(float delta) {
		alpha -= hideSpeed * delta;
	}
}
