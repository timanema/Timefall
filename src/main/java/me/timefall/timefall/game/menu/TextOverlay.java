package me.timefall.timefall.game.menu;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.font.DrawWrapper;
import me.timefall.timefall.graphics.font.Font;
import me.timefall.timefall.graphics.font.FontSize;
import me.timefall.timefall.graphics.font.FontType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextOverlay extends GameState {
    private HashMap<DrawWrapper, Boolean> drawnText;
    private boolean textChanges;

    public TextOverlay(Settings settings, Screen screen) {
        super(settings, screen);

        this.drawnText = new HashMap<>();
        this.textChanges = false;
    }

    @Override
    public void tick(double deltaTime) {

    }

    @Override
    public void render(Screen screen) {
        // Check for 'inactive' text
        Iterator iterator = this.drawnText.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry keyPair = (Map.Entry) iterator.next();

            if (!((boolean) keyPair.getValue()))
            {
                this.textChanges = true;

                iterator.remove();
            } else
            {
                this.drawnText.put((DrawWrapper) keyPair.getKey(), false);
            }
        }

        // Only draw when there were changes
        if (this.textChanges)
        {
            screen.clear();

            for (DrawWrapper drawWrapper : this.drawnText.keySet())
            {
                Font.drawText(screen,
                        drawWrapper.getFontType(),
                        drawWrapper.getFontSize(),
                        drawWrapper.getMessage(),
                        drawWrapper.getxPos(),
                        drawWrapper.getyPos()
                );
            }

            this.textChanges = false;
        }
    }

    @Override
    public void saveState() {

    }

    public void requestText(FontType fontType,
                            FontSize fontSize,
                            String message,
                            int xPos,
                            int yPos)
    {
        DrawWrapper drawWrapper = checkDrawn(fontType, fontSize, message, xPos, yPos);

        // Wrapper is null when text has not been drawn yet
        if (drawWrapper == null)
        {
            this.textChanges = true;
            this.drawnText.put(new DrawWrapper(fontType, fontSize, message, xPos, yPos), true);
        } else
        {
            this.drawnText.put(drawWrapper, true);
        }
    }

    private DrawWrapper checkDrawn(FontType fontType,
                               FontSize fontSize,
                               String message,
                               int xPos,
                               int yPos)
    {
        for (DrawWrapper drawWrapper : this.drawnText.keySet())
        {
            if (drawWrapper.getFontType().equals(fontType) &&
                    drawWrapper.getFontSize().equals(fontSize) &&
                    drawWrapper.getMessage().equals(message) &&
                    drawWrapper.getxPos() == xPos &&
                    drawWrapper.getyPos() == yPos)
            {
                return drawWrapper;
            }
        }

        return null;
    }
}
