/**
 * 
 */
package com.wegames.graphics;

/**
 * @author Neptune
 *
 */
public class Screen {
	public int width, height;
	public int[] pixels;
	
	/**
	 * 
	 */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[this.width*this.height];
	}
	
	public void drawCircle(int diameter, int xPos, int yPos, int color) {
		for (int y = 0; y <= diameter; y++) {
			if((y+yPos)<0||(y+yPos)>=height) continue;
			for (int x = 0; x <= diameter; x++) {
				if((x+xPos)<0||(x+xPos)>=width) continue;
				if(Math.sqrt(Math.pow(x-diameter/2, 2)+Math.pow(y-diameter/2, 2))<=diameter/2) pixels[((x-diameter/2)+xPos)+((y-diameter/2)+yPos)*width]=color;
			}
		}
	}
	public void drawLine(int x0, int y0, int x1, int y1, int color) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = (x0 < x1) ? 1 : -1;
		int sy = (y0 < y1) ? 1 : -1;

		int err = dx - dy;

		while (true) {
			pixels[x0+y0*width]=color;
			
			if (x0 == x1 && y0 == y1) {
				break;
			}
			
			int e2 = 2 * err;
			
			if (e2 > -dy) {
				err -= dy;
				x0 += sx;
			}
			
			if (e2 < dx) {
				err += dx;
				y0 += sy;
			}
		}
	}
	
}