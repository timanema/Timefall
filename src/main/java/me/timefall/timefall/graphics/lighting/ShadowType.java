package me.timefall.timefall.graphics.lighting;

public enum ShadowType
{
    NONE(0), FULL(1), FADE(2), TREE_FADE(3), OUTSIDE_VIEW(4);

    public int type;

    ShadowType(int type)
    {
        this.type = type;
    }
}
