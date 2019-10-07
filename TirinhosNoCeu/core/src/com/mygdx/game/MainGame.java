/**
 * Game Project - TirinhosNoCeu
 * Purpose - To validate the Java unit, I chose to create a game
 * with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 0.6.0
 *
 * @author - Marco Silva
 */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Camera;

//MAIN CLASS
public class MainGame extends ApplicationAdapter {

    //==== constants for game size and window size ===
    private final int mapWidth = 800;
    private final int mapHeight = 800;
    private final int viewWidth = 640;
    private final int viewHeight = 480;

    //==== Stage | Actors | Camera ====
    private Stage mainStage;
    private Stage uiStage;
    private BasicActor spaceShip;
    private BasicActor meteor;
    private BasicActor background;
    private BasicActor win;
    private Camera gameCamera;

    //labels for score
    LabelTextGround labels;

    @Override
    public void create() {
        // === instantiating the stage and actors ====
        mainStage = new Stage();
        uiStage = new Stage();
        labels = new LabelTextGround();

        initActor();
        initTextures();
        initPositions();

        // === meteor animation action set ====
        meteor.addAction(spinMeRoundBaby);
        meteor.setOrigin(meteor.getWidth() / 2, meteor.getHeight() / 2);
        addActors();
    }

    @Override
    public void render() {
        playerMovement();
        update();
        screenClean();
        collision();
        camera();
        mainStage.draw();
        uiStage.draw();
    }

    // testing movements
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

        //=== avoiding the margins of the window ===
        // clamp replaces the the if X < 0, or x > marginX
        spaceShip.setX(MathUtils.clamp(spaceShip.getX(), 0,
                viewWidth - spaceShip.getWidth()));
        spaceShip.setY(MathUtils.clamp(spaceShip.getY(), 0,
                viewHeight - spaceShip.getHeight()));
    }

    // collision tryout needs to be fixed
    private void collision() {
        Rectangle shipRect = spaceShip.getBoundingRectangle();
        Rectangle meteorRect = meteor.getBoundingRectangle();

        if (shipRect.overlaps(meteorRect)) {
            win.addAction(gameOver);
            win.setVisible(true);
        }
    }

    // screen color cleaner
    private void screenClean() {
        Gdx.gl.glClearColor(0.5f, 0.1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    //updates the stage delta time  
    private void update() {
        float fps = Gdx.graphics.getDeltaTime();
        mainStage.act(fps);
        uiStage.act(fps);
        labels.setTimeElapsed(fps);
        labels.setText("Time: " + (int) labels.getTimeElapsed());
    }

    private void initActor() {
        background = new BasicActor();
        spaceShip = new BasicActor();
        meteor = new BasicActor();
        win = new BasicActor();
    }

    private void initTextures() {
        background.setTexture(new Texture("blueBackground.png"));
        spaceShip.setTexture(new Texture("playerShip.png"));
        meteor.setTexture(new Texture("meteorMedium.png"));
        win.setTexture(new Texture("end.png"));
        win.setVisible(false);
    }

    private void initPositions() {
        background.setPosition(0, 0);
        spaceShip.setPosition(10, 10);
        meteor.setPosition(200, 350);
        win.setPosition(0, 0);
    }

    private void addActors() {
        mainStage.addActor(background);
        mainStage.addActor(meteor);
        mainStage.addActor(spaceShip);
        uiStage.addActor(labels.getTimeLabel());
        uiStage.addActor(win);
    }

    private void camera() {
        gameCamera = mainStage.getCamera();

        //camera here it's centered on the player
        //uses the clamp method , gets both axis for position and size to calculate
        gameCamera.position.set(spaceShip.getX() + spaceShip.getOriginX(),
                spaceShip.getY() + spaceShip.getOriginY(), 0);

        //bounds the camera to the window
        gameCamera.position.x = MathUtils.clamp(gameCamera.position.x,
                viewWidth / 2, mapWidth - viewWidth / 2);

        gameCamera.position.y = MathUtils.clamp(gameCamera.position.y,
                viewHeight / 2, mapHeight - viewHeight / 2);

        gameCamera.update();
    }

    //testing actions spinning the meteor sprite game over end screen flashing tint
    private final Action spinMeRoundBaby = Actions.parallel(
            Actions.alpha(1),
            Actions.rotateBy(360 * 5, 30),
            Actions.scaleTo(2, 2, 2),
            Actions.fadeOut(100)
    );
    private final Action gameOver = Actions.sequence(
            Actions.alpha(0),
            Actions.show(),
            Actions.fadeIn(25),
            Actions.sequence(
                    //shade color
                    Actions.color(new Color(1, 0, 0, 1), 1),
                    Actions.color(new Color(0, 0, 0, 1), 1)
            )
    );
}

/**
 * TODO LIST:
 * - PLAYER MOVEMENT : rotating when left/right keys are pressed, 
 *      slowing down only when down or s is the key being pressed
 * - ASTEROIDS: spawn, movement, destruction.
 * - HP : both for asteroids and for the spaceship
 * - POINTS: for each asteroid some points are awarded
 * - UI.
 * - POWERUPS (?)
 * 
 */
