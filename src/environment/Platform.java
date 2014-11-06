package environment;

import game.Game;
import graphics.Images;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Platform extends BaseEnvironment {
	public Platform(double x, double y, int graphic) {
		super();
		super.setSolid(true);
		super.x = x;
		try {
			super.graphic = ImageIO.read(Game.getResource(Images.image[graphic]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.height = super.graphic.getHeight(null);
		super.y = y;
		super.width = super.graphic.getWidth(null);
		super.friction = 0.2;
	}

	@Override
	public void tick() {
	}

}
