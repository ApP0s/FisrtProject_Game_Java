package com.mygdx.game.Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;


public class Spike extends InteractiveTileObject {
    private Game game;
    public static boolean hitSpike;
    public Spike(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        hitSpike = false;

    }
    @Override
    public void onHit() {
        Gdx.app.log("Spike", "Collision");
        hitSpike = true;
    }

}
