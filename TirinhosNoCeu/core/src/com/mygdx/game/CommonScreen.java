/*
 * class to refactor the code and to learn about abstract classes
 */
package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 *
 * @author MarcoSilva
 */
public abstract class CommonScreen implements Screen, InputProcessor {

    //giving acess to subclasses in any package
    //may turn into private for encapsulation
    protected Game game;
    protected Stage mainStage;
    protected Stage userInterface;

    //==== PROPERTIES =====
    private int viewHeight = 800;
    private int viewWidth = 800;
    private boolean paused;

    //CONSTRUCTOR
    public CommonScreen(Game game) {
        this.game = game;
        
        mainStage = new Stage(new FitViewport(viewWidth, viewHeight));
        userInterface = new Stage(new FitViewport(viewWidth, viewHeight));
        paused = false;

        InputMultiplexer multInput = new InputMultiplexer(this, userInterface, mainStage);

        Gdx.input.setInputProcessor(multInput);
        
        create();
    }

    // ===== ABSTRACT METHODS ======
    public abstract void create();

    public abstract void update(float deltaTime);

    // gameloop method, to update and then render.
    @Override
    public void render(float deltaTime) {
        userInterface.act(deltaTime); //to update the actor based on time

        //verifies if the game is pause, and if isn't, updates the mainStage
        if (!isPaused()) {
            mainStage.act(deltaTime);
            update(deltaTime);
        }

        //clears the oldest frames
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draws the new frames
        mainStage.draw();
        userInterface.draw();
    }

    //=== pause methods ===
    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void togglePaused() {
        paused = !paused;
    }

    //=== resize method with the sizes of the viewports when they are changed
    @Override
    public void resize(int w, int h) {
        mainStage.getViewport().update(w, h, true);
        userInterface.getViewport().update(w, h, true);
    }

    // === window properties gets
    public int getWidth() {
        return viewWidth;
    }

    public int getHeight() {
        return viewHeight;
    }

    /**
     * Below are the methods required from the Screen class
     */
    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void show() {
    }

    /**
     * Below are the methods from the InputProcessor class
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyTyped(char chara) {
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        return false;
    }

    @Override
    public boolean keyDown(int keyCode) {
        return false;
    }
}
