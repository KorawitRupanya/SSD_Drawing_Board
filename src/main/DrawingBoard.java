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
		repaint();
	}
	
	public void clear() {
		gObjects.clear();
		repaint();
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

		private int currentPosX;
		private int currentPosY;

		private void deselectAll() {
			for (GObject gObject:gObjects){
				gObject.deselected();
			}
			target=null;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			currentPosX = e.getX();
			currentPosY = e.getY();

			deselectAll();
			for(GObject gObject:gObjects){
				if(gObject.pointerHit(currentPosX,currentPosY)){
					target=gObject;
					gObject.selected();
					break;
				}
			}
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(target!=null){
				int newPostX = e.getX() - currentPosX;
				int newPostY = e.getY() - currentPosY;
				target.move(newPostX,newPostY);
				repaint();
				currentPosX = e.getX();
				currentPosY = e.getY();
			}
		}
	}
	
}