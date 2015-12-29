package com.AndroidTest.game.GameObjects;

import java.util.Random;

import com.AndroidTest.game.Constants;
import com.AndroidTest.game.GameWorld.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Wall {
	public enum Direction {
		
		UP(0, 1),
		DOWN(0, -1),
		RIGHT(1, 0),
		LEFT(-1, 0);
		
		public final float x, y;
		
		private Direction(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public static Direction getRandomDirection() {
			Random rand = new Random();
			 return values()[rand.nextInt(values().length)];
		}
	}
	
	public Direction direction;
	public float speed, width, height;
	public Body body;
	
	private Wall() {
		this.speed = 30f;
	}
	
	public Wall(GameWorld gameWorld, float size, float length) {
		this();
		this.direction = Direction.getRandomDirection();
		initWall(gameWorld, direction, size, length);
	}
	
	public Wall(GameWorld gameWorld, Direction direction, float size, float length) {
		this();
		this.direction = direction;
		initWall(gameWorld, direction, size, length);
	}
	
	private void initWall(GameWorld gameWorld, Direction direction, float size, float length) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(new Vector2(2, 15));  
		
		PolygonShape box = new PolygonShape();
		Random rand = new Random();
		
		/*
		 * this is some weird complicated stuff
		 */
		
		width = Math.abs(direction.x)*(length/2)+Math.abs(direction.y)*(size/2);
		height = Math.abs(direction.y)*(length/2)+Math.abs(direction.x)*(size/2);
		box.setAsBox(width, height);
		
		bodyDef.position.set(Math.abs(direction.x)*((-direction.x+1)*Constants.WORLD_WIDTH*0.5f+((-length/2)*direction.x))+Math.abs(direction.y)*(rand.nextFloat()*(Constants.WORLD_WIDTH-size)+size/2), 
				Math.abs(direction.x)*(rand.nextFloat()*(Constants.WORLD_HEIGHT-size)+size/2)+Math.abs(direction.y)*((-direction.y+1)*Constants.WORLD_HEIGHT*0.5f+((-length/2)*direction.y)));
		
		body = gameWorld.world.createBody(bodyDef);
		body.setLinearVelocity(speed * direction.x, speed * direction.y);
		
		body.createFixture(box, 0.0f); 
		box.dispose();
	}
	
	public boolean getDeath() {
		
		/* Following expression results in:
		 * 
		 * if UP: (body.getPosition().y >= Constants.WORLD_HEIGHT+height)
		 * if DOWN: (-height >= body.getPosition().y
		 * if RIGHT: (body.getPosition().x >= Constants.WORLD_WIDTH+width)
		 * if LEFT: (-width >= body.getPosition().x)
		 * 
		 * So the wall is dead after it is outside the "world"
		 */
		
		return (Math.abs(direction.x)*((direction.x+1)*0.5f*body.getPosition().x+(1-direction.x)*0.5f*(-width))
				+Math.abs(direction.y)*((direction.y+1)*0.5f*body.getPosition().y+(1-direction.y)*0.5f*(-height))
				>= Math.abs(direction.x)*((direction.x+1)*0.5f*(Constants.WORLD_WIDTH+width)+(1-direction.x)*0.5f*body.getPosition().x)
				+Math.abs(direction.y)*((direction.y+1)*0.5f*(Constants.WORLD_HEIGHT+height)+(1-direction.y)*0.5f*body.getPosition().y)) ? true : false;
	}
}
