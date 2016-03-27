/**
 * 
 */
package com.wegames.level;

import java.util.ArrayList;
import java.util.List;

import com.wegames.Main;

/**
 * @author Neptune
 *
 */
public class Level {
	public static List<Node> nodes = new ArrayList<Node>();
	
	public static int cameraPosX=0, cameraPosY=0;
	
	Main main;
	
	public Level(Main main) {
		this.main=main;
	}
	
	public void tick() {
		if(main.mouse.x<50) cameraPosX-=5;
		if(main.mouse.y<50) cameraPosY-=5;
		if(main.mouse.x>Main.width-50) cameraPosX+=5;
		if(main.mouse.y>Main.height-120)cameraPosY+=5; // This one is strange, add another 70 to counteract the effect.
		for (int i = 0; i < Level.nodes.size(); i++) {
			nodes.get(i).tick();
		}
	}
}
