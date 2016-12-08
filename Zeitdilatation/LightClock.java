package Zeitdilatation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LightClock {

	public final List<Particle> particles = new ArrayList<Particle>();
	public final List<Particle> usedParticles = new ArrayList<Particle>();
	
	public Particle createParticle(float x, float y, float hideSpeed) {
		
		Particle newP;
		if(usedParticles.size() > 0) {
			newP = usedParticles.get(0);
			newP.point.set(x, y);
			newP.alpha = 1f;
			newP.hideSpeed = hideSpeed;
			particles.add(newP);
			usedParticles.remove(0);
		} else {
			newP = new Particle(x, y, hideSpeed);
			particles.add(newP);
		}
		return newP;
	}
	
	public void updateParticles(float delta) {
		for(Particle particle : particles) {
			particle.update(delta);
		}
		
		Iterator<Particle> it = particles.iterator();
		while(it.hasNext()) {
			Particle p = it.next();
			if(p.alpha <= 0) {
				it.remove();
				usedParticles.add(p);
			}
		}
	}
	
	public Vector lightPos = new Vector(), pos = new Vector(), vel = new Vector();
	public float width, height, c, gamma, time;
	
	public LightClock(float x, float y, float width, float height) {
		this.pos.set(x, y);
		this.width = width;
		this.height = height;
		this.lightPos.set(x, y+height);
		this.c = 1f;
		this.vel.set(0f, -c);
	}
	
	public boolean contains(Vector p) {
		return (p.x > pos.x-width/2 && p.x < pos.x+width/2) && (p.y > pos.y && p.y < pos.y+height);
	}
	
	public void update(float delta) {
		createParticle(lightPos.x, lightPos.y, 2f);
    	updateParticles(delta);
    	
    	vel.y = Math.signum(vel.y) * (float) Math.sqrt(c*c - vel.x*vel.x);
    	
    	pos.x += vel.x * delta;
    	
    	lightPos.x += vel.x * delta;
    	lightPos.y += vel.y * delta;
    	
    	if(lightPos.x > pos.x - width/2 && lightPos.x < pos.x + width/2) {
	    	if(lightPos.y > pos.y + height) {
	    		vel.y *= -1;
	    		lightPos.y = pos.y + height - (lightPos.y - (pos.y + height));
	    	} else if(lightPos.y < pos.y) {
	    		vel.y *= -1;
	    		lightPos.y = pos.y + (pos.y - lightPos.y);
	    	}
    	}
    	
    	gamma = (float) Math.sqrt(1/((1-(vel.x*vel.x)/(c*c))));
    	time += delta/gamma;
	}
	
	public void draw(Graphics2D g2, Camera cam) {
		Particle oldP = null;
        for(Particle particle : particles) {
        	if(oldP != null) {
        		g2.setColor(new Color(1f, 1f, 0f, particle.alpha));
        		cam.drawLine(g2, oldP.point.x, oldP.point.y, particle.point.x, particle.point.y);
        
        	}
        	oldP = particle;
        }
        
        g2.setColor(Color.WHITE);
        cam.drawLine(g2, pos.x-width/2, pos.y, pos.x+width/2, pos.y);
        cam.drawLine(g2, pos.x-width/2, pos.y+height, pos.x+width/2, pos.y+height);
        
        g2.drawString(Utils.format(time), cam.worldToRealX(pos.x - width/2), cam.worldToRealY(pos.y)+15);
        g2.drawString(Utils.format(time-(int) time), cam.worldToRealX(pos.x - width/2), cam.worldToRealY(pos.y)+30);
        g2.drawString("c: " + c, cam.worldToRealX(pos.x - width/2), cam.worldToRealY(pos.y)+45);
        g2.drawString("clock speed: " + Math.abs(vel.x), cam.worldToRealX(pos.x - width/2), cam.worldToRealY(pos.y)+60);
        g2.drawString("% of c: " + Math.abs(vel.x)*100/c + "%", cam.worldToRealX(pos.x - width/2), cam.worldToRealY(pos.y)+75);
        g2.drawString("gamma: " + Utils.format(gamma), cam.worldToRealX(pos.x - width/2), cam.worldToRealY(pos.y)+90);
	}
	
}
