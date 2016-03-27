/**
 * 
 */
package com.wegames.level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.wegames.Main;
import com.wegames.graphics.Screen;

/**
 * @author Neptune
 *
 */
public class Node {
	public int x=0, y=0;
	
	Level level;
	
	int maxDistance=400;
	
	Map<Integer, Integer> rawSupply = new HashMap<Integer, Integer>();
	Map<Integer, Integer> productionHarvesting = new HashMap<Integer, Integer>();
	
	Map<Integer, Integer> supply = new HashMap<Integer, Integer>();
	Map<Integer, Integer> demand = new HashMap<Integer, Integer>();
	Map<Integer, Integer> excess = new HashMap<Integer, Integer>();
	Map<Integer, Integer> productionExcess = new HashMap<Integer, Integer>();
	
	List<Node> nearbyNodes;
	
	public double getDistance(Node node) {
		int dx = node.x-x;
		int dy = node.y-y;
		
		return Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
	}
	/**
	 * Returns nearby nodes, the range is defined by the integer maxDistance. This should be used for the purpose of trading.
	 */
	public static List<Node> getNearbyNodes(Node target) {
		List<Node> result = new ArrayList<Node>();
		for (int i = 0; i < Level.nodes.size(); i++) {
			if(target.getDistance(Level.nodes.get(i))<=target.maxDistance) result.add(Level.nodes.get(i));
		}
		
		result.sort(new Comparator<Node>() {
			public int compare(Node o1, Node o2) {
				if(target.getDistance(o1)>target.getDistance(o2)) return 1;
				else if(target.getDistance(o1)<target.getDistance(o2)) return-1;
				else return 0;
			}
		});
		
		return result;
	}
	
	private Random random = new Random();
	private int standardSize = random.nextInt(100)/8;
	
	private void generateResources() {
		for (int i = 0; i < Resource.resources.size(); i++) {
			if(!Resource.resources.get(i).isRaw()) continue;
			int amount = (int) ((random.nextDouble()/2+1.5)*standardSize*1000*Resource.resources.get(i).rarity);
			rawSupply.put(i, amount);
		}
	}
	
	public void calculateResources() {
		for (int i = 0; i < Resource.resources.size(); i++) {
			if(supply.get(i)!=null&&productionHarvesting.get(i)!=null) {
				supply.put(i, supply.get(i)+productionHarvesting.get(i));
			}
			if(supply.get(i)!=null&&productionExcess.get(i)!=null) {
				supply.put(i, supply.get(i)+productionExcess.get(i));
			}
			if(supply.get(i)==null&&productionHarvesting.get(i)!=null) {
				supply.put(i, productionHarvesting.get(i));
			}
			if(supply.get(i)==null&&productionExcess.get(i)!=null) {
				supply.put(i, productionExcess.get(i));
			}
			if(supply.get(i)==null&&demand.get(i)==null) {
				return;
			} else if(supply.get(i)==null&&demand.get(i)!=null) {
				excess.put(i, -demand.get(i));
			} else if(supply.get(i)!=null&&demand.get(i)==null) {
				excess.put(i, supply.get(i));
			} else {
				excess.put(i, supply.get(i)-demand.get(i));
			}
		}
	}
	int toUpdate=0;
	public void tick() {
		nearbyNodes=getNearbyNodes(this);
		if(toUpdate<1800) {
			toUpdate++;
		} else {
			toUpdate=0;
			calculateResources();
		}
	}
	
	public void render(Screen screen) {
		screen.drawCircle(standardSize*10, x, y, 0xffffffff);
	}
	
	public Node() {
		x=random.nextInt(Main.width);
		y=random.nextInt(Main.height);
		
		Level.nodes.add(this);
		generateResources();
	}
	public Node(int x, int y) {
		this.x=x;
		this.y=y;
		
		Level.nodes.add(this);
		generateResources();
	}
	
	public static void main(String[] args) {
	}
	
	@SuppressWarnings("unused")
	private class Factory {
		Resource type;
		
		int production = 1;
		
		int totalUpgrades=0;
		int maxUpgrades=100;
		
		public Factory(Resource type) {
			this.type=type;
		}
		/**
		 * @return true if upgrade exceeds max upgrades.
		 */
		public boolean upgrade() {
			if(totalUpgrades>=maxUpgrades) return true;
			totalUpgrades++;
			production+=2;
			production=(int) (production*1.1);
			return false;
		}
	}
}







