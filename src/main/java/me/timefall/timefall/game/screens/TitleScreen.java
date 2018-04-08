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
    public TitleScreen(Settings settings,
                       Screen screen)
    {
        super(settings, screen);

        this.init();
    }

    private void init()
    {
        getScreen().draw(Sprite.backgroundImage[0][0], 0, 0);

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

    }

    @Override
    public void render(Screen screen)
    {

    }

    @Override
    public void saveState()
    {

    }
}
