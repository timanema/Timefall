package me.timefall.timefall.game.screens;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.graphics.components.buttons.ButtonSkin;
import me.timefall.timefall.graphics.components.buttons.NormalButton;
import me.timefall.timefall.graphics.utils.ButtonFactory;

public class TitleScreen extends GameState
{
    private int cloudOffSet;
    private int ticksPassed;

    public TitleScreen(Settings settings,
                       Screen screen)
    {
        super(settings, screen);

        this.cloudOffSet = 0;
        this.init();
    }

    private void init()
    {
        NormalButton startButton = ButtonFactory.createNormalButton(448,
                48,
                Timefall::startGame,
                ButtonSkin.TRANSPARENT,
                true);

        startButton.setLocation(Timefall.MENU_X_RES / 2 - 224,
                (Timefall.MENU_Y_RES / 2 - 24) + 16);
        startButton.setActive(true);
        startButton.setVisible(true);

        NormalButton menuButton = ButtonFactory.createNormalButton(448,
                48,
                Timefall::openMenu,
                ButtonSkin.TRANSPARENT,
                true);

        menuButton.setLocation(Timefall.MENU_X_RES / 2 - 224,
                (Timefall.MENU_Y_RES / 2 - 24) + 144);
        menuButton.setActive(true);
        menuButton.setVisible(true);

        NormalButton quitButton = ButtonFactory.createNormalButton(448,
                48,
                Timefall.getMainDisplay()::onClose,
                ButtonSkin.TRANSPARENT,
                true);

        quitButton.setLocation(Timefall.MENU_X_RES / 2 - 224,
                (Timefall.MENU_Y_RES / 2 - 24) + 272);
        quitButton.setActive(true);
        quitButton.setVisible(true);
    }

    @Override
    public void tick(double deltaTime)
    {
        if (ticksPassed % 3 == 0)
        {
            this.cloudOffSet -= 1;

            if (this.cloudOffSet == Timefall.MENU_X_RES * -1)
            {
                this.cloudOffSet = 0;
            }
        }

        ticksPassed += 1;
    }

    @Override
    public void render(Screen screen)
    {
        // Draw background
        screen.draw(Sprite.backgroundImage, 0, 0);

        // Draw first cloud set
        screen.draw(Sprite.backgroundImageCloud, this.cloudOffSet, 0);

        // Draw second cloud set
        screen.draw(Sprite.backgroundImageCloud, Timefall.MENU_X_RES + this.cloudOffSet, 0);

        // Draw text
        screen.draw(Sprite.backgroundImageText, 0, 0);
    }

    @Override
    public void saveState()
    {

    }
}
