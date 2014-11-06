package entities;

import java.awt.Graphics;

import environment.BaseEnvironment;
import environment.MovingEnvironment;
import game.Game;

public abstract class Entity {
	protected double x;
	protected double y;
	protected double dX;
	private double dY;
	protected double width;
	protected double height;
	private BaseEnvironment ground;
	protected long lastTouch;
	private boolean dead;
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public Entity(double x,double y){
		this.x = x;
		this.y = y;
	}
	public void tick(){
		double movingGroundDx = 0;
		double movingGroundDy = 0;
		//X collision Detection
		if(getGround() != null){
			if((Math.abs((y + height) - getGround().getY()) > 20) || !BaseEnvironment.xCollide(this,getGround()) || (y - height) - getGround().getY() < 0)
				setGround(null);
			else if(getGround() instanceof MovingEnvironment){
				movingGroundDx = ((MovingEnvironment)getGround()).getdX();
				movingGroundDy = ((MovingEnvironment)getGround()).getdY();
			}
		}
		this.x += getdX() + movingGroundDx;
		if(collision(0)){
			this.x -= getdX() + movingGroundDx;
		}
		//Y collision Detection
		this.y += getdY() + movingGroundDy;
		if(collision(1)){
			this.y -= getdY() + movingGroundDy;
			setdY(0);
		}
		friction();
	}
	public boolean collision(int coord){
		if(x < 0){
			return true;
		}
		for(int i = 0;i < Game.g.environment.size();i++){
			if(Game.g.environment.get(i).collide(this,coord) && Game.g.environment.get(i).isSolid()){
				if(coord == 1){
					setGround(Game.g.environment.get(i));
				}
				return true;
			}
		}
		return false;
	}
	public abstract void render(Graphics g);
	public double getdX() {
		return dX;
	}
	public void setdX(double dX) {
		this.dX = dX;
	}
	public void friction(){
		//Friction && Gravity
		if(getGround() != null){
			double absDx = Math.abs(getdX()) - getGround().getFriction();
			if(absDx < 0)absDx = 0;
			setdX(Math.copySign(absDx,dX)); //Ground friction
			setdY(getdY() + 0.1);//Gravity
		}else{
			double absDx = Math.abs(getdX()) - 0.1;
			if(absDx < 0)absDx = 0;
			setdX(Math.copySign(absDx,dX)); //Air resistance
			if(getdY() < 10)
				setdY(getdY() + 0.5);//Gravity
			else
				setdY(10);
		}
	}
	public void changeX(int deltaX){
		x += deltaX;
	}
	public double getdY() {
		return dY;
	}
	public void setdY(double dY) {
		this.dY = dY;
	}
	public BaseEnvironment getGround() {
		return ground;
	}
	public void setGround(BaseEnvironment ground) {
		this.ground = ground;
	}
}