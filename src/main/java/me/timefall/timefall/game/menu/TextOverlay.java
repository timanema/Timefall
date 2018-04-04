package me.timefall.timefall.game.menu;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Colour;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.graphics.font.DrawWrapper;
import me.timefall.timefall.graphics.font.Font;
import me.timefall.timefall.graphics.font.FontSize;
import me.timefall.timefall.graphics.font.FontType;
import me.timefall.timefall.threads.GameThread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextOverlay extends GameState {
    private HashMap<DrawWrapper, Boolean> drawnText;
    private boolean textChanges;

    public float fadeFactor = 0;
    private float fade = 0;
    private int fadeDelay = 0;
    public static boolean fading;

    public TextOverlay(Settings settings,
                       Screen screen,
                       boolean disableSplash)
    {
        super(settings, screen);

        this.drawnText = new HashMap<>();
        this.textChanges = false;

        if (!disableSplash)
        {
            this.startFade(1.9, 7);
            Timefall.getSoundHandler().startMusicTitleScreen();
        }
    }

    @Override
    public void tick(double deltaTime)
    {
        if (this.fade > 0.0)
        {
            if (fadeDelay > 0)
            {
                this.fadeDelay -= 1;
            } else
            {
                this.fade -= this.fadeFactor;

                if (this.fade <= 0)
                {
                    // Setting text changes to true, so fade gets deleted and text rendered properly
                    this.textChanges = true;
                    fading = false;
                }
            }
        }
    }

    @Override
    public void render(Screen screen)
    {
        // Render fade effects
        renderFade(screen);

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
            renderFade(screen);

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

    public void startFade(double duration,
                          double fadeDelay)
    {
        fading = true;
        this.fade = 1F;
        this.fadeFactor = (float) (1 / (duration * GameThread.TICKS));
        this.fadeDelay = (int) fadeDelay * GameThread.TICKS;
    }

    private void renderFade(Screen screen)
    {
        if (this.fade > 0.0)
        {
            for (int x = 0; x < screen.width; x++) {
                for (int y = 0; y < screen.height; y++) {
                    screen.draw(new Colour(this.fade, 0.745F, 0.737F, 0.659F), x, y);
                }
            }

            if (this.fadeDelay > 0)
            {
                screen.draw(Sprite.startupLogo[0][0],
                        640 - Sprite.startupLogo[0][0].width / 2,
                        360 - Sprite.startupLogo[0][0].height / 2);
            }
        }
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
