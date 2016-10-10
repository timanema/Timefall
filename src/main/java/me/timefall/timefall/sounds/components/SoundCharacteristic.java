package me.timefall.timefall.sounds.components;

public enum SoundCharacteristic
{
    TIME_OF_DAY_DAWN(SoundPriority.NORMAL), TIME_OF_DAY_MORNING(SoundPriority.NORMAL), TIME_OF_DAY_AFTERNOON(SoundPriority.NORMAL), TIME_OF_DAY_DUSK(SoundPriority.NORMAL), TIME_OF_DAY_MIDNIGHT(SoundPriority.NORMAL),
    WEATHER_SUNNY(SoundPriority.NORMAL), WEATHER_RAINY(SoundPriority.NORMAL), WEATHER_SNOWY(SoundPriority.NORMAL), WEATHER_THUNDER(SoundPriority.NORMAL), WEATHER_WINDY(SoundPriority.NORMAL), WEATHER_MORNING_DAWN(SoundPriority.NORMAL),
    WEATHER_CHANGING_SUN_RAIN(SoundPriority.NORMAL), WEATHER_CHANGING_RAIN_SUN(SoundPriority.NORMAL), WEATHER_CHANGING_CALM_WINDY(SoundPriority.NORMAL), WEATHER_CHANGING_SUN_SNOW(SoundPriority.NORMAL), WEATHER_CHANGING_SNOW_SUN(SoundPriority.NORMAL),
    ACTIONS_EXPLORING(SoundPriority.VERY_HIGH), ACTIONS_BATTLE(SoundPriority.VERY_HIGH),
    SURROUNDINGS_MOUNTAINS(SoundPriority.HIGH), SURROUNDINGS_PUB(SoundPriority.HIGH), SURROUNDINGS_FIELD(SoundPriority.HIGH), SURROUNDINGS_DUNGEON(SoundPriority.HIGH), SURROUNDINGS_FOREST(SoundPriority.HIGH),
    MAIN_MENU(SoundPriority.OBEY);

    private SoundPriority priorityCharacteristic;

    private SoundCharacteristic(SoundPriority priorityCharacteristic)
    {
        this.priorityCharacteristic = priorityCharacteristic;
    }

    public SoundPriority isPriorityCharacteristic()
    {
        return this.priorityCharacteristic;
    }
}
