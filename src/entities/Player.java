package entities;

import environment.BaseEnvironment;
import environment.MovingEnvironment;
import game.Game;

import java.awt.Color;
import java.awt.Graphics;

import sound.Sound;

public class Player extends Entity{
	private long lastJump;
	public int coins;
	public Player(double x,double y) {
		super(x,y);
		super.width = 25;
		super.height = 50;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int)x,(int)y,(int)width,(int)height);
	}

	@Override
	public double getX() {
		return super.getX() - Game.g.xOffset;
	}
	public void changeX(int deltaX){
		if((getdX() < 0 && Game.g.xOffset >= 0 )|| x < Game.g.getWidth() / 2 - width / 2){
			x += deltaX;
		}
		else{
			Game.g.xOffset -= deltaX;
		}
	}
	@Override
	public void tick() {
		double movingGroundDx = 0;
		double movingGroundDy = 0;
		//X collision Detection
		if(getGround() != null){
			if(Math.abs((y + height) - getGround().getY()) > 20 || !BaseEnvironment.xCollide(this,getGround()))
				setGround(null);
			else if(getGround() instanceof MovingEnvironment){
				movingGroundDx = ((MovingEnvironment)getGround()).getdX();
				movingGroundDy = ((MovingEnvironment)getGround()).getdY();
			}
		}
		if((getdX() + movingGroundDx < 0 && Game.g.xOffset >= 0 )|| x < Game.g.getWidth() / 2 - width / 2){
			x += getdX() + movingGroundDx;
			if(collision(0)){
				x -= getdX() + movingGroundDx;
			}
		}
		else{
			Game.g.xOffset -= getdX() + movingGroundDx;
			if(collision(0)){
				Game.g.xOffset += getdX() + movingGroundDx;
			}
		}
		//Y collision Detection
		if(movingGroundDy < 0)
			setdY(getdY() - 0.1);
		this.y += getdY() + movingGroundDy;
		if(collision(1)){
			this.y -= getdY();
			setdY(0);
		}
		friction();
	}
	public void jump(){
		if(getGround() != null && System.currentTimeMillis() - lastJump > 50){
			lastJump = System.currentTimeMillis();
			setdY(getdY() - 10);
		}
	}

	public void pickupCoin(int i) {
		Sound.playSound(Game.getResource("sound/sounds/ItemPickup.wav"));
		coins += i;
	}
}
