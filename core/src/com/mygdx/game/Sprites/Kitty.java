package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import com.mygdx.game.screen.GameScreen;



public class Kitty extends Sprite {
    public enum State {RUNNING, STANDING}
    public State currentState;
    public State previousState;
    private Animation<TextureRegion> kittyRun;
    private float stateTimer;
    private boolean running;
    public World world;
    public Body b2body;
    private TextureRegion KittyIdle;

    public Kitty(World world, GameScreen screen ){
        super(screen.getAtlas().findRegion("walk1"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        running = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(),2,0,14,16));
        frames.add(new TextureRegion(getTexture(),2 + 17,0,14,16));
        frames.add(new TextureRegion(getTexture(),2 + 17+17,0,14,16));
        frames.add(new TextureRegion(getTexture(),2 + 17+17+17,0,14,16));
        kittyRun = new Animation(0.05f,frames);
        kittyRun.setPlayMode(Animation.PlayMode.LOOP);
        frames.clear();

        KittyIdle = new TextureRegion(getTexture(),70,3,14,16);
        defineKitty();
        setBounds(0,0,14/MainGame.PPM,16/MainGame.PPM);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = kittyRun.getKeyFrame(stateTimer);
                break;
            default:
                region = KittyIdle;
                break;
        }
        if ((b2body.getLinearVelocity().x <0 || !running)&& !region.isFlipX()){
            region.flip(true,false);
            running = false;
        }
        else if ((b2body.getLinearVelocity().x >0 || running)&& region.isFlipX()){
            region.flip(true,false);
            running = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0))
            return State.RUNNING;
        else
            return State.STANDING;
    }
    public void defineKitty(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(60/MainGame.PPM, 22/MainGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5f/ MainGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("shape");
    }


}
