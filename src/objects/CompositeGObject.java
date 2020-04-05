package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		for(GObject gObject: gObjects) {
			gObject.move(dX, dY);
		}
	}
	
	public void recalculateRegion() {
		int re_width = 0, re_height = 0;
		int re_x = gObjects.get(0).x, re_y = gObjects.get(0).y;

		for(GObject gObject: gObjects){
			if(re_width <= gObject.width + gObject.x){
				  re_width = gObject.width + gObject.x;
			}
			if(re_height <= gObject.height + gObject.y){
				re_height = gObject.height + gObject.y;
			}
			if(re_x >= gObject.x){
				re_x = gObject.x;
			}
			if(re_y >= gObject.y){
				re_y = gObject.y;
			}
		}

		super.x = re_x;
		super.y = re_y;
		super.width = re_width - re_x;
		super.height = re_height - re_y;
	}


	@Override
	public void paintObject(Graphics g) {
		for(GObject gObject: gObjects){
			gObject.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		g.drawString("Group", x, y+height+15);
	}
	
}
