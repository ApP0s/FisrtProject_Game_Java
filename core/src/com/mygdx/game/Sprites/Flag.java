package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Scenes.Hud;

public class Flag extends InteractiveTileObject {
    public static boolean hitFlag;
    public Flag(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        hitFlag = false;
    }

    @Override
    public void onHit() {
        Gdx.app.log("Flag", "locked");
        if (Hud.score == 4){
            Gdx.app.log("Flag", "Unlocked");
            hitFlag = true;
        }
    }
}
