/**
 * Game Project - TirinhosNoCeu
 * Purpose - To validate the Java unit, I chose to create a game
 * with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 0.1.0
 *
 * @author - Marco Silva
 */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainGame extends ApplicationAdapter {

    private boolean gameOver = false;

    //==== Stage and actors ====
    private Stage mainStage;
    private BasicActor spaceShip;
    private BasicActor meteor;
    private BasicActor background;
    private BasicActor win;

    @Override
    public void create() {
        // === instantiating the stage and actors ====
        mainStage = new Stage();

        background = new BasicActor();
        spaceShip = new BasicActor();
        meteor = new BasicActor();
        win = new BasicActor();

        // === textures  ===
        background.setTexture(new Texture("blueBackground.png"));
        spaceShip.setTexture(new Texture("playerShip.png"));
        meteor.setTexture(new Texture("meteorMedium.png"));
        win.setTexture(new Texture("end.png"));
        win.setVisible(false);

        // === positions ===
        background.setPosition(0, 0);
        background.scaleBy(2);
        spaceShip.setPosition(10, 10);
        meteor.setPosition(350, 400);
        win.setPosition(0, 0);

        // ==== stage elements being added ===
        mainStage.addActor(background);
        mainStage.addActor(meteor);
        mainStage.addActor(spaceShip);        
        mainStage.addActor(win);
    }

    @Override
    public void render() {
        playerMovement();
        update();
        screenClean();
        collision();
        mainStage.draw();
    }

    /**
     * testing movements
     */
    private void playerMovement() {
        //MOVEMENT
        spaceShip.setVelocityX(0);
        spaceShip.setVelocityY(0);

        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            spaceShip.setVelocityX(-200);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            spaceShip.setVelocityX(200);
        }
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            spaceShip.setVelocityY(200);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
            spaceShip.setVelocityY(-200);
        }
    }


    /**
     * collision tryout needs to be fixed
     */
    private void collision() {
        Rectangle shipRect = spaceShip.getBoundingRectangle();
        Rectangle meteorRect = meteor.getBoundingRectangle();

        if (shipRect.overlaps(meteorRect)) {
            gameOver = true;
            win.setVisible(true);
        }
    }

    /**
     * screen color cleaner
     */
    private void screenClean() {
        Gdx.gl.glClearColor(0.5f, 0.1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * updates the stage delta time
     */
    private void update() {
        float fps = Gdx.graphics.getDeltaTime();
        mainStage.act(fps);
    }
}
