/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.ninjasoft.dwarffamily;

import java.awt.Color;
import java.awt.Dimension;
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
    
    final private ArrayList<Dwarf> dwarfList;
    final private Panel drawPanel;
    
    private int drag_start_x, drag_start_y, panel_drag_start_x, panel_drag_start_y;
    
    public VisualExplorerWindow(ArrayList<Dwarf> dwarves)
    {
        super("Visual explorer");
        
        dwarfList = dwarves;
        drawPanel = new Panel();
        
        this.add(drawPanel);
        this.pack();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
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

class Panel extends JPanel
{
    public int camera_x, camera_y;
    
    public Panel()
    {
        
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 600);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.drawString("This is a test", 10 - camera_x, 10 - camera_y);
    }
}