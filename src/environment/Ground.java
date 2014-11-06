package environment;

import game.Game;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Ground extends BaseEnvironment {
	public Ground(){
		super();
		super.setSolid(true);
		super.x = 0;
		try {
			super.graphic = ImageIO.read(Game.getResource("graphics/environment/background/groundl1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.height = 50;
		super.y  = (int) Game.g.getLevelSize().getSize().getHeight() - height;
		super.width = (int) Game.g.getLevelSize().getSize().getWidth();
		super.friction = 0.2;
	}


	@Override
	public void tick() {
	}
	
}
