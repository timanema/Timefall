package me.betasterren.bsgame.game.titlescreen;

import me.betasterren.bsgame.GameState;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.graphics.Screen;

public class TitleScreen extends GameState {
    private enum SelectedOption {
        PLAY("Play Timefall!"), SETTINGS("Change settings"), QUIT("Quit to desktop"), SETTINGS_AUDO("Change audio settings"), SETTINGS_GRAPHICS("Change graphics settings");

        private final String fullText;

        private SelectedOption(String fullText) {
            this.fullText = fullText;
        }

        public String getFullText() {
            return fullText;
        }
    }

    private SelectedOption selectedOption;

    public TitleScreen(Settings settings) {
        super(settings);

        selectedOption = SelectedOption.PLAY;
    }

    @Override
    public void tick(double deltaTime) {
        // Hier kan je dus selectedOption enzo aanpassen
    }

    @Override
    public void render(Screen screen) {
        // En hier renderen, duh
    }
}
