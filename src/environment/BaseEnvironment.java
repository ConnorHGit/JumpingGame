package environment;

import java.awt.Graphics;
import java.awt.Image;

import entities.Entity;
import game.Game;

public abstract class BaseEnvironment {
	private boolean Solid;
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected double friction;
	protected Image graphic;
	private boolean dead;
	public boolean collide(Entity entity,int coord) {
		return(entity.getX() <= getX() + getWidth()
				&& getX() <= entity.getX() + entity.getWidth()
				&& entity.getY() <= y + height
				&& y <= entity.getY() + entity.getHeight());
	}
	public void render(Graphics g){
		g.drawImage(graphic, (int)(x + Game.g.xOffset),(int)y, null);
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	public double getFriction() {
		return friction;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	public abstract void tick();
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public static boolean xCollide(Entity entity,BaseEnvironment base){
		return(entity.getX() <= base.getX() + base.getWidth()
				&& base.getX() <= entity.getX() + entity.getWidth());
	}
	public boolean isSolid() {
		return Solid;
	}
	public void setSolid(boolean solid) {
		Solid = solid;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
