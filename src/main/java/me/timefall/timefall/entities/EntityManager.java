package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.behaviors.Behavior;
import me.timefall.timefall.entities.behaviors.BehaviorAction;
import me.timefall.timefall.entities.behaviors.Routine;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.Vector;

import java.util.ArrayList;
import java.util.List;

public class EntityManager
{
    // Character index - Start
    public static final int NPC_AMOUNT = 1;
    public static final int CHARACTER_SHEEP = 0;
    // Character index - End

    private List<Entity> entities;
    private List<Behavior> behaviors;
    private Player player;

    public EntityManager(int worldX, int worldY)
    {
        System.out.println("  Loading entities ...");

        entities = new ArrayList<>();


        System.out.println("   Creating player ...");
        player = new Player("TestPlayer", new Vector(Vector.globalWorldName, Vector.playerxPos * .0625F, Vector.playeryPos * .0625F), Direction.NORTH, worldX, worldY, Timefall.getSettings().getGender());

        behaviors = new ArrayList<>();
        initBehaviors();
    }

    public void tickEntities()
    {
        // Loop through all the entities and tick the entities in the current world
        for (Entity entity : this.entities)
        {
            if (entity.getLocation() == null || entity.getLocation().getWorldName() == null)
            {
                continue;
            }

            if (entity.getLocation().getWorldName().equals(Timefall.getTileManager().getCurrentWorld().getWorldName()))
            {
                entity.tick();
            }
        }

        // Tick the player
        player.tick();
    }

    public void renderEntities(Screen screen)
    {
        // Loop through all the entities and render the entities in the current world
        for (Entity entity : this.entities)
        {
            if (entity.getLocation() == null || entity.getLocation().getWorldName() == null)
            {
                continue;
            }

            if (entity.getLocation().getWorldName().equals(Timefall.getTileManager().getCurrentWorld().getWorldName()))
            {
                entity.render(screen);
            }
        }
        // Render the player
        player.render(screen);
    }

    public void addNPC(NPC npc)
    {
        this.entities.add(npc);
    }

    public void removeNPC(NPC npc)
    {
        this.entities.remove(npc);
    }

    public List<Entity> getEntities()
    {
        return entities;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void initBehaviors()
    {
        //NoBehavior
        Behavior nobehavior = new Behavior();
        behaviors.add(nobehavior);
        //Idle
        Behavior idle = new Behavior("idle");
        idle.addAction(new BehaviorAction("randomMove"), 0);
        idle.addAction();
        idle.addAction();
        idle.addAction();
        idle.addAction();
        idle.addAction();
        idle.addAction();
        idle.addAction();
        behaviors.add(idle);
    }

    public Behavior getBehavior(String behaviorName)
    {
        for (Behavior behavior : behaviors)
        {
            if (behavior.getBehaviorName().equals(behaviorName))
            {
                return behavior.copy();
            }
        }
        return null;
    }
}
