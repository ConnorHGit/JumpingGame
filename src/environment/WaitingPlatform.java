package environment;

import entities.Entity;
import entities.Player;

public class WaitingPlatform extends MovingPlatform{
	private double waitDx;
	private double waitDy;
	private boolean started;
	public WaitingPlatform(double x, double y,
			double dX, double dY, int xLimit1, int xLimit2, int yLimit1,
			int yLimit2,int graphic) {
		super(x, y, 0, 0, xLimit1, xLimit2, yLimit1, yLimit2,graphic);
		waitDx = dX;
		waitDy = dY;
	}
	public void start(){
		super.dX = waitDx;
		super.dY = waitDy;
		started = true;
	}
	@Override
	public boolean collide(Entity entity, int coord) {
		boolean collided = super.collide(entity, coord);
		if(collided && entity instanceof Player && !started && entity.getY() + entity.getHeight() <= y + 1)
			start();
		return collided;
	}
	public void tick() {
		x += getdX();
		y += getdY();
		if (x > xLimit2)
			setdX(getdX() * -1);
		else if(x < xLimit1 && waitDx != 0){
			started = false;
			setdX(0);
		}
		if (y < yLimit2){
			setdY(getdY() * -1);
		}
		else if(y > yLimit1 && waitDy < 0){
			System.out.println("ASd");
			started = false;
			setdY(0);
		}
	}
}
