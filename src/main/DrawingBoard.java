package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
		repaint();
	}
	
	public void groupAll() {
		CompositeGObject group_all = new CompositeGObject();
		for(GObject gObject: gObjects){
			if(gObject instanceof CompositeGObject){
				group_all = (CompositeGObject) gObject;
			}
			else{
				gObject.deselected();
				group_all.add(gObject);
			}
		}

		group_all.recalculateRegion();
		gObjects.clear();
		gObjects.add(group_all);
		repaint();
	}

	public void deleteSelected() {
		gObjects.remove(target);
	}
	
	public void clear() {
		gObjects.clear();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		private int posX;
		private int posY;

		private void deselectAll() {
			if(target!=null){
				target.deselected();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			posX = e.getX();
			posY = e.getY();
			boolean click = false;

			for(GObject gObject:gObjects){
				if(gObject.pointerHit(posX,posY)){
					if(target!=null) target.deselected();
					gObject.selected();
					target = gObject;
					gObjects.remove(target);
					gObjects.add(target);
					click=true;
				}
			}
			if(!click) deselectAll();;
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int newPostX = e.getX() - posX;
			int newPostY = e.getY() - posY;
			target.move(newPostX,newPostY);
			repaint();
			posX = e.getX();
			posY = e.getY();
		}
	}
	
}