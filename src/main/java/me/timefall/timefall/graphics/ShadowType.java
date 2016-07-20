package me.timefall.timefall.graphics;

public enum ShadowType
{
    NONE(0), FULL(1), FADE(2), TREE_FADE(3);

    public int type;

    ShadowType(int type)
    {
        this.type = type;
    }
}
