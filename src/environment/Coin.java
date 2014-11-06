package environment;

import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entity;
import entities.Player;
import game.Game;

public class Coin extends BaseEnvironment{
	public Coin(double x,double y){
		setSolid(false);
		setX(x);
		setY(y);
		try {
			graphic = ImageIO.read(Game.getResource("graphics/environment/coin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setWidth(graphic.getWidth(null));
		height = (graphic.getHeight(null));
	}
	public boolean collide(Entity entity,int coord) {
		if(entity.getX() <= getX() + getWidth()
				&& getX() <= entity.getX() + entity.getWidth()
				&& entity.getY() <= y + height
				&& y <= entity.getY() + entity.getHeight() && entity instanceof Player && !isDead()){
			((Player)entity).pickupCoin(1);
			setDead(true);
		}
		return false;
	}
	@Override
	public void tick() {
	}

}
