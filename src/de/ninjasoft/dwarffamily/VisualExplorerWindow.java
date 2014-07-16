/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.ninjasoft.dwarffamily;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Smith
 */
public class VisualExplorerWindow extends JFrame implements MouseListener, MouseMotionListener {
    
    private ArrayList<Dwarf> dwarfList = null;
    private FamilyTreePanel drawPanel = null;
    private Dwarf originDwarf = null;
    
    private int drag_start_x, drag_start_y, panel_drag_start_x, panel_drag_start_y;
    
    public VisualExplorerWindow(ArrayList<Dwarf> dwarves, int origin)
    {
        super("Loading...");
        if(origin == -1)
        {
            System.err.println("Invalid dwarf index passed to VisualExplorerWindow");
            return;
        }
        this.setTitle("Family tree of " + dwarves.get(origin).getCasedName());
        
        dwarfList = dwarves;
        originDwarf = dwarves.get(origin);
        drawPanel = new FamilyTreePanel(dwarfList, originDwarf);
        
        this.add(drawPanel);
        this.pack();
        drawPanel.centerOnOrigin();
        drawPanel.plotTree();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON3)
        {
            drawPanel.centerOnOrigin();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        drag_start_x = me.getX();
        drag_start_y = me.getY();
        panel_drag_start_x = drawPanel.camera_x;
        panel_drag_start_y = drawPanel.camera_y;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        drawPanel.camera_x = panel_drag_start_x - (me.getX() - drag_start_x);
        drawPanel.camera_y = panel_drag_start_y - (me.getY() - drag_start_y);
        drawPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
}

class FamilyTreePanel extends JPanel
{
    class Rect
    {
        public int x, y, width, height;
        public String str;
        
        public Rect(int _x, int _y, int _w, int _h)
        {
            x = _x; y = _y; width = _w; height = _h;
        }
    }
    
    HashMap< Number, ArrayList<Rect> > generations;
    
    public int camera_x, camera_y;
    Dwarf originDwarf = null;
    ArrayList<Dwarf> dwarfList;
    
    public FamilyTreePanel(ArrayList<Dwarf> dwarves, Dwarf origin)
    {
        generations = new HashMap<>();
        dwarfList = dwarves;
        originDwarf = origin;
    }
    
    public void centerOnOrigin()
    {
        camera_x = -getWidth() / 2;
        camera_y = -getHeight() / 2;
        repaint();
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 600);
    }
    
    public void plotTree()
    {
        Graphics g = getGraphics();
        if(g == null) return;
        
        plotDwarfName(g, originDwarf, 0, 0, 0);
        plotDwarfDescendancy(g, originDwarf, 0, 0, 0);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        if(originDwarf == null) return; // Not ready yet.
        
        super.paintComponent(g);
        FontMetrics fontMetrics = g.getFontMetrics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        
        for(int i=0; i<generations.size(); ++i)
        {
            for(int j=0; j<generations.get(i).size(); ++j)
            {
                Rect r = generations.get(i).get(j);
                g.drawRect(r.x - camera_x - 5, r.y - camera_y - 5 - fontMetrics.getAscent(), r.width, r.height);
                g.drawString(r.str, r.x - camera_x, r.y - camera_y);
            }
        }
    }
    
    /*private void drawCenteredString(Graphics g, String str, int x, int y)
    {
        FontMetrics fontMetrics = g.getFontMetrics();
        int width = fontMetrics.stringWidth(str);
        g.drawString(str, x-camera_x - width/2, y-camera_y -fontMetrics.getHeight()/2);
    }*/
            
    private Rect plotDwarfName(Graphics g, Dwarf dwarf, int x, int y, int generation)
    {
        FontMetrics fontMetrics = g.getFontMetrics();
        
        int width = fontMetrics.stringWidth(dwarf.getCasedName()) + 10;
        int height = fontMetrics.getHeight() + 10;
        g.setColor(Color.yellow);
        
        int drx = x - width/2 - 5;
        int dry = y - height - 5 - fontMetrics.getAscent()/2;
        
        Rect r = new Rect(drx, dry, width, height);
        r.str = dwarf.getCasedName();
        
        while(generationContainsIntersectingRect(generation, r))
        {
            r.x += 10;
            x += 10;
        }
        
        drx = r.x;
        
        if(!generations.containsKey(generation))
        {
            generations.put(generation, new ArrayList<>());
        }
        if(!generations.get(generation).contains(r))
        {
            generations.get(generation).add(r);
        }
        
        return r;
    }
    
    private boolean generationContainsIntersectingRect(int generation, Rect r) {
        if(!generations.containsKey(generation)) return false;
        
        ArrayList<Rect> gen = generations.get(generation);
        
        for(int i=0; i<gen.size(); ++i)
        {
            Rect r2 = gen.get(i);
            if((r.x > r2.x && r.x < r2.x + r2.width) ||
               (r.x + r.width > r2.x && r.x + r.width < r2.x + r2.width) ||
               (r2.x > r.x && r2.x < r.x + r.width) ||
               (r2.x + r2.width > r.x && r2.x + r2.width < r.x + r.width))
                return true;
        }
        
        return false;
    }
    
    private void plotDwarfDescendancy(Graphics g, Dwarf dwarf, int parent_x, int parent_y, int generation)
    {
        if(dwarf.getChildren().isEmpty()) return;
        
        FontMetrics fontMetrics = g.getFontMetrics();
        
        int numChildren = dwarf.getChildren().size();
        int reservedWidth = 0;
        for(int i=0; i<numChildren; ++i)
        {
            reservedWidth += fontMetrics.stringWidth(dwarf.getChildren().get(i).getCasedName()) + 40; // 10 padding + 10 margin
        }
        
        int initial_x = parent_x - reservedWidth/2 + fontMetrics.stringWidth(dwarf.getCasedName())/2;
        
        for(int i=0; i<numChildren; ++i)
        {
            int y = parent_y + fontMetrics.getHeight()*2 + 20;
            Rect r = plotDwarfName(g, dwarf.getChildren().get(i), initial_x, y, generation + 1);
            plotDwarfDescendancy(g, dwarf.getChildren().get(i), r.x, y, generation + 1);
            initial_x = r.x + r.width;
        }
    }
}