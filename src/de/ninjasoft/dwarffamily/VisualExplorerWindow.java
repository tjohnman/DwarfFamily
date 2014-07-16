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
    public int camera_x, camera_y;
    Dwarf originDwarf = null;
    ArrayList<Dwarf> dwarfList;
    
    public FamilyTreePanel(ArrayList<Dwarf> dwarves, Dwarf origin)
    {
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
    
    @Override
    public void paintComponent(Graphics g)
    {
        if(originDwarf == null) return; // Not ready yet.
        
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        drawCenteredString(g, originDwarf.getCasedName(), 0, 0);
    }
    
    private void drawCenteredString(Graphics g, String str, int x, int y)
    {
        FontMetrics fontMetrics = g.getFontMetrics();
        int width = fontMetrics.stringWidth(str);
        g.drawString(str, x-camera_x - width/2, y-camera_y -fontMetrics.getHeight()/2);
    }
}