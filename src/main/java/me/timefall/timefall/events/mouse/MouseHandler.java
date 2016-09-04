package me.timefall.timefall.events.mouse;

import me.timefall.timefall.Timefall;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener
{
    public static int mouseX = 0, mouseY = 0;

    @Override
    public void mouseClicked(MouseEvent e)
    {
        mouseX = (int) (e.getX() / Timefall.getSettings().getScale());
        mouseY = (int) (e.getY() / Timefall.getSettings().getScale());

        Timefall.getButtonHandler().clickAction(mouseX, mouseY);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        //mouseX = (int) (e.getX() / Timefall.getSettings().getScreenSize().getScale());
        //mouseY = (int) (e.getY() / Timefall.getSettings().getScreenSize().getScale());
    }
}
