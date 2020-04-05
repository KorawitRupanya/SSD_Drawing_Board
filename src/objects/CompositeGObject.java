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
		for(GObject gObject: gObjects){
			gObject.move(dX,dY);
		}
	}
	
	public void recalculateRegion() {
		for(GObject gObject: gObjects){
			if(gObject.x<=width){
				gObject.x=width;
			}
			if(gObject.y<=height){
				gObject.y=height;
			}
		}
	}

	@Override
	public void paintObject(Graphics g) {
		g.create();
	}

	@Override
	public void paintLabel(Graphics g) {
		g.drawString("Group",x,y+width+10);
	}
	
}
