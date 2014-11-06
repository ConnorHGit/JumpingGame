package menu;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Game;

import javax.swing.JButton;

public class MainMenu extends AbstractMenu {
	public MainMenu(){
		super();
		addComponent(new JButton("Start"),350, 200, 100, 40);
		((JButton) getComponent(0)).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeMenu();
				if(Game.g.entities.size() <= 0){
					Game.g.startGame("testlevel.lvl");
				}
				else
					Game.g.gameStarted = true;
			}
		});
	}
	@Override
	public void render(Graphics g) {
	}
	@Override
	public void openMenu() {
		super.openMenu();
		if(Game.g.entities.size() > 0)
			((JButton) getComponent(0)).setText("Resume");
		else
			((JButton) getComponent(0)).setText("Start");
	}
}
