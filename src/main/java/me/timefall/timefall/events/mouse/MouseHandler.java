package me.timefall.timefall.events.mouse;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.game.menu.TextOverlay;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener
{
    public static int mouseX = 0, mouseY = 0;

    @Override
    public void mouseClicked(MouseEvent e)
    {
        // Check if settings is initiated and if the splashscreen is gone
        if (Timefall.getSettings() != null &&
                !TextOverlay.fading)
        {
            mouseX = (int) (e.getX() / Timefall.getSettings().getScale());
            mouseY = (int) (e.getY() / Timefall.getSettings().getScale());

            // If this statement is false getScale() returned -1,
            // which means the screen was not yet initiated at the time the user clicked
            if (mouseX > 0 && mouseY > 0)
            {
                Timefall.getButtonHandler().clickAction(mouseX, mouseY);
            }
        }
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
