package me.timefall.timefall.graphics.font;

public class DrawWrapper {
    private final FontType fontType;
    private final FontSize fontSize;
    private final String message;
    private final int xPos;
    private final int yPos;

    public DrawWrapper(FontType fontType,
                       FontSize fontSize,
                       String message,
                       int xPos,
                       int yPos)
    {
        this.fontType = fontType;
        this.fontSize = fontSize;
        this.message = message;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public FontType getFontType() {
        return this.fontType;
    }

    public FontSize getFontSize() {
        return this.fontSize;
    }

    public String getMessage() {
        return this.message;
    }

    public int getxPos() {
        return this.xPos;
    }

    public int getyPos() {
        return this.yPos;
    }
}
