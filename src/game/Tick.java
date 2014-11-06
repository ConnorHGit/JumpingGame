package game;

import java.util.ArrayList;

import entities.Entity;
import environment.BaseEnvironment;

public class Tick implements Runnable{

	@Override
	public void run() {
		while(true){
			if(Game.g != null){
				ArrayList<Entity> entities = Game.g.entities;
				for(int i = 0;i < entities.size();i++){
					entities.get(i).tick();
				}
				ArrayList<BaseEnvironment> enviro = Game.g.environment;
				for(int i = 0;i < enviro.size();i++){
					enviro.get(i).tick();
				}
				int entOffset = 0;
				for(int i = 0;i < entities.size();i++)
					if(entities.get(i).isDead()){
						entities.remove(i - entOffset);
						entOffset++;
					}
				int envOffset = 0;
				for(int i = 0;i < enviro.size();i++){
					if(enviro.get(i).isDead()){
						enviro.remove(i - envOffset);
						envOffset++;
					}
				}
				if(Game.g.aPressed){
					if(Game.g.entities.get(0).getdX() > -5)
						Game.g.entities.get(0).setdX(Game.g.entities.get(0).getdX() - 0.5);
				}
				if(Game.g.dPressed){
					if(Game.g.entities.get(0).getdX() < 5)
						Game.g.entities.get(0).setdX(Game.g.entities.get(0).getdX() + 0.5);
				}
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
