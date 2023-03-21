package com.mygdx.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Sprites.Kitty;
import com.mygdx.game.Sprites.Spike;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

import javax.sound.sampled.TargetDataLine;

import static com.badlogic.gdx.Gdx.audio;


public class GameScreen implements Screen {

    private MainGame game;
    private TextureAtlas atlas;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Kitty kitty;
    public GameScreen (MainGame game){
        atlas = new TextureAtlas(Gdx.files.internal("KittyAni.txt"));
        this.game = game;
        // for camera the player
        gameCam = new OrthographicCamera();
        //aspect ratio
        gamePort = new StretchViewport(MainGame.V_WIDTH/MainGame.PPM,MainGame.V_HEIGHT/MainGame.PPM,gameCam);
        //create our game HUD
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level0.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MainGame.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-100/MainGame.PPM),true);
        b2dr = new Box2DDebugRenderer();
        b2dr.setDrawBodies(false);
        new B2WorldCreator(world, map);
        kitty = new Kitty(world,this);
        world.setContactListener(new WorldContactListener());

    }
    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.W))
            kitty.b2body.applyLinearImpulse(new Vector2(0,1.5f), kitty.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.D) && kitty.b2body.getLinearVelocity().x <= 2)
            kitty.b2body.applyLinearImpulse(new Vector2(0.05f,0), kitty.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.A) && kitty.b2body.getLinearVelocity().x >= -2)
            kitty.b2body.applyLinearImpulse(new Vector2(-0.05f,0), kitty.b2body.getWorldCenter(),true);
    }

    public void update(float dt){
        handleInput(dt);
        hud.update(dt);
        world.step(1/60f,9,6);
        kitty.update(dt);
        gameCam.position.x = kitty.b2body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        kitty.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if (Spike.hitSpike)
            game.setScreen(new GameOverScreen(game));
        if (Flag.hitFlag)
            game.setScreen(new GameWon(game));

    }




    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        atlas.dispose();
    }
}
