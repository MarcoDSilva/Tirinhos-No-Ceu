/**
 * Game Project - TirinhosNoCeu
 * Purpose - To validate the Java unit, I chose to create a game
 * with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 0.0.1
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

public class MainGame extends ApplicationAdapter {

    //background image tests
    private SpriteBatch batch;
    private Sprite spaceBackground;
    ;

    //battleship image tests
    private Sprite spaceShip;

    //meteor
    private Sprite meteorTexture;

    //gameOVer
    private boolean gameOver = false;
    private Sprite win;

    @Override
    public void create() {
        //====== Sprites for textures =====
        batch = new SpriteBatch();

        spaceBackground = new Sprite(new Texture(Gdx.files.internal("blueBackground.png")));
        spaceShip = new Sprite(new Texture(Gdx.files.internal("playerShip.png")));
        meteorTexture = new Sprite(new Texture(Gdx.files.internal("meteorMedium.png")));
        win = new Sprite(new Texture(Gdx.files.internal("end.png")));

        //====== position of textures/elements ======
        spaceBackground.setPosition(0, 0);
        spaceShip.setPosition(10, 10);
        meteorTexture.setPosition(350, 350);
    }

    @Override
    public void render() {

        playerMovement();
        screenClean();
        collision();
        drawTextures();
    }

    @Override
    public void dispose() {
        batch.dispose();      
    }

    /**
     * testing movements
     */
    private void playerMovement() {
        //MOVEMENT
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            spaceShip.translateX(-2);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            spaceShip.translateX(2);
        }
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            spaceShip.translateY(2);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
            spaceShip.translateY(-2);
        }
    }

    /**
     * seeing if methods cleans the code better the rendering
     */
    private void drawTextures() {
        batch.begin();
        
        spaceBackground.draw(batch);
        spaceShip.draw(batch);
        meteorTexture.draw(batch);
        
        if (gameOver) {
            win.draw(batch);
        }
        batch.end();
    }

    /**
     * collision tryout
     */
    private void collision() {
        Rectangle shipRect = spaceShip.getBoundingRectangle();
        Rectangle meteorRect = meteorTexture.getBoundingRectangle();

        if (shipRect.overlaps(meteorRect)) {
            gameOver = true;
        }
    }

    /**
     * screen color cleaner
     */
    private void screenClean() {
        Gdx.gl.glClearColor(0.5f, 0.1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
