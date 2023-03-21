package com.mygdx.game.Sprites;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Scenes.Hud;

public class Star extends InteractiveTileObject {

    private Sprite sprite;

    public Star(World world, TiledMap map, Rectangle bounds, Sprite sprite) {
        super(world, map, bounds);
        this.sprite = sprite;
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        // Set the alpha value of the sprite to 0 to make it transparent
        sprite.setAlpha(0);

        // Set the collision category of the fixture to 0
        Filter filter = new Filter();
        filter.categoryBits = 0;
        fixture.setFilterData(filter);

        // Increment the score
        Hud.addScore(1);
        getCell().setTile(null);
    }

}


