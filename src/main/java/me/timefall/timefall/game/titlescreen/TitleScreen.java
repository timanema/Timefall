package me.timefall.timefall.game.titlescreen;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.buttons.ButtonSkin;
import me.timefall.timefall.graphics.components.buttons.NormalButton;
import me.timefall.timefall.graphics.handlers.ButtonFactory;

public class TitleScreen extends GameState
{
    private enum SelectedOption
    {
        PLAY("Play Timefall!"), SETTINGS("Change settings"), QUIT("Quit to desktop"), SETTINGS_AUDO("Change audio settings"), SETTINGS_GRAPHICS("Change graphics settings");

        private final String fullText;

        private SelectedOption(String fullText)
        {
            this.fullText = fullText;
        }

        public String getFullText()
        {
            return fullText;
        }
    }

    private SelectedOption selectedOption;

    public TitleScreen(Settings settings, Screen screen)
    {
        super(settings, screen);

        selectedOption = SelectedOption.PLAY;
        this.init();
    }

    private void init()
    {
        NormalButton normalButton = ButtonFactory.createNormalButton("", 30, 25, Timefall::startGame, ButtonSkin.DEFAULT, true);

        normalButton.setLocation(23, 56);
        normalButton.setActive(true);
        normalButton.setVisible(true);
    }

    @Override
    public void tick(double deltaTime)
    {
        // Hier kan je dus selectedOption enzo aanpassen
    }

    @Override
    public void render(Screen screen)
    {
        // En hier renderen, duh
    }
}
