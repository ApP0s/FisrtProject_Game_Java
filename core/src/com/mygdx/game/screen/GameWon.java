package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.Scenes.Hud;

public class GameWon implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameWon(Game game){
        this.game = game;
        viewport = new StretchViewport(MainGame.V_WIDTH,MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MainGame)game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.BLACK);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label gameScore = new Label("Your Scores: ", font);
        Label gameTime = new Label("Time: ", font);
        table.add(gameScore).expand();
        table.add(Hud.scoreLabel).expand();
        table.row();
        table.add(gameTime).expand().padTop(5f);
        table.add(Hud.countdownLabel).expand();

        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255,255,255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
