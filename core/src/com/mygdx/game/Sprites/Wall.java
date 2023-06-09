package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;


public class Wall extends InteractiveTileObject{
    public Wall(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        moveWall();
    }

    public void moveWall(){

    }
    @Override
    public void onHit() {
        Gdx.app.log("Wall", "Collision");
    }
}
