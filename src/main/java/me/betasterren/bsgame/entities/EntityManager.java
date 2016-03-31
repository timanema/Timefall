package me.betasterren.bsgame.entities;

import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.Direction;
import me.betasterren.bsgame.level.Vector;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private List<Entity> entities;
    private Player player;

    public EntityManager(int worldX, int worldY) {
        System.out.println("  Loading entities ...");

        entities = new ArrayList<>();

        System.out.println("   Creating player ...");
        player = new Player("TestPlayer", new Vector(Vector.playerxPos * .0625F, Vector.playeryPos * .0625F), Direction.NORTH, worldX, worldY);
    }

    public void tickEntities() {
        entities.forEach(me.betasterren.bsgame.entities.Entity::tick);

        player.tick();
    }

    public void renderEntities(Screen screen) {
        for (Entity entity : entities)
            entity.render(screen);

        player.render(screen);
    }

    public Player getPlayer() {
        return player;
    }
}
