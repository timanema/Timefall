package me.timefall.timefall.game.titlescreen;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.buttons.ButtonSkin;
import me.timefall.timefall.graphics.components.buttons.NormalButton;
import me.timefall.timefall.graphics.font.Font;
import me.timefall.timefall.graphics.font.FontSize;
import me.timefall.timefall.graphics.font.FontType;
import me.timefall.timefall.graphics.utils.ButtonFactory;

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
        NormalButton testButton = ButtonFactory.createNormalButton("asfsf", 50, 25, Timefall::startGame, ButtonSkin.DEFAULT, true);

        testButton.setLocation(Timefall.MENU_X_RES / 2 - 25, Timefall.MENU_Y_RES / 2 - 13);
        testButton.setActive(true);
        testButton.setVisible(true);

       /* //TODO: Remove example code
        NormalButton normalButton = ButtonFactory.createNormalButton("", 30, 25, Timefall::startGame, ButtonSkin.DEFAULT, true);

        normalButton.setLocation(23, 56);
        normalButton.setActive(true);
        normalButton.setVisible(true);

        //Timefall.getTime().scheduleTask(5 * 30, () -> System.out.println("Sync task: " + Thread.currentThread().getName()));
        //Timefall.getTime().scheduleASyncTask(5 * 30 + 5, () -> System.out.println("ASync task: " + Thread.currentThread().getName()));
        Timefall.getTime().scheduleTask(0, 30, () -> System.out.println(Timefall.getTime().getTimeString()));*/
    }

    @Override
    public void tick(double deltaTime)
    {
        // Hier kan je dus selectedOption enzo aanpassen
    }

    @Override
    public void render(Screen screen)
    {
        Font.drawText(FontType.DEFAULT, FontSize.LARGE, "Hello me friend", 0, 0);
    }

    @Override
    public void saveState()
    {

    }
}
