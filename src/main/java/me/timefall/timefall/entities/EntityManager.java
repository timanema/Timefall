package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.Vector;

import java.util.ArrayList;
import java.util.List;

public class EntityManager
{
    private List<Entity> entities;
    private Player player;

    public EntityManager(int worldX, int worldY)
    {
        System.out.println("  Loading entities ...");

        entities = new ArrayList<>();

        System.out.println("   Creating player ...");
        player = new Player("TestPlayer", new Vector(Vector.globalWorldName, Vector.playerxPos * .0625F, Vector.playeryPos * .0625F), Direction.NORTH, worldX, worldY, Timefall.getSettings().getGender());
    }

    public void tickEntities()
    {
        // Loop through all the entities and tick the entities in the current world
        entities.stream().filter(entity -> entity.getLocation().getWorldName().equals(Timefall.getTileManager().getCurrentWorld().getWorldName())).forEach(Entity::tick);

        // Tick the player
        player.tick();
    }

    public void renderEntities(Screen screen)
    {
        // Loop through all the entities and render the entities in the current world
        entities.stream().filter(entity -> entity.getLocation().getWorldName().equals(Timefall.getTileManager().getCurrentWorld().getWorldName())).forEach(entity -> entity.render(screen));

        // Render the player
        player.render(screen);
    }

    public Player getPlayer()
    {
        return player;
    }
}
