package environment;

import entities.Entity;
import game.Game;
import graphics.Images;

import java.io.IOException;

import javax.imageio.ImageIO;

public class MovingPlatform extends MovingEnvironment {
	protected int xLimit1;
	protected int xLimit2;
	protected int yLimit1;
	protected int yLimit2;

	public MovingPlatform(double x, double y,
			double dX, double dY, int xLimit1, int xLimit2, int yLimit1,
			int yLimit2,int graphic) {
		super.x = x;
		super.y = y;
		super.setSolid(true);
		try {
			super.graphic = ImageIO.read(Game.getResource(Images.image[graphic]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.width = super.graphic.getWidth(null);
		super.height = super.graphic.getHeight(null);
		super.friction = 0.2;
		this.setdX(dX);
		this.setdY(dY);
		this.xLimit1 = xLimit1;
		this.xLimit2 = xLimit2;
		this.yLimit1 = yLimit1;
		this.yLimit2 = yLimit2;
	}

	@Override
	public boolean collide(Entity entity, int coord) {
		if (entity.getX() <= getX() + getWidth()
				&& getX() <= entity.getX() + entity.getWidth()
				&& entity.getY() <= y + height
				&& y <= entity.getY() + entity.getHeight()) {
			if (coord == 0) {
				if ((entity.getX() + entity.getWidth() <= x + getWidth() / 2 && getdX() < 0)) {
					entity.changeX((int) getdX());
					setSolid(false);
					if(entity.collision(0))
						entity.changeX(-(int) getdX());
					setSolid(true);
				} else if (entity.getX() >= x + getWidth() / 2 && getdX() > 0) {
					entity.changeX((int) getdX());
					setSolid(false);
					if(entity.collision(0))
						entity.changeX(-(int) getdX());
					setSolid(true);
				}
			}
			return true;
		}
		return false;
	}

	public void tick() {
		x += getdX();
		y += getdY();
		if (x < xLimit1 || x > xLimit2)
			setdX(getdX() * -1);
		if (y < yLimit1 || y > yLimit2)
			setdY(getdY() * -1);
	}
}
