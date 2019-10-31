/*
 * Game Project - TirinhosNoCeu with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 1.5.0
 */
package testes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Silva
 */
public class StartingLevel extends CommonScreen {

    //=== level elements ===  
    private BasicActor background;
    private BasicActor win;
    private PhysicsActor spaceShip;
    private PhysicsActor meteor;
    private ArrayList<PhysicsActor> meteors;
    private final int[] meteorCoordinates = {100, 10, 200, 20, 200, 30, 100, 40};
    //=== game size ===
    private final float mapWidth;
    private final float mapHeight;

    //=== label for score ===
    private LabelTextGround labels;

    // ===== CONSTRUCTOR =====
    public StartingLevel(Game game) {
        super(game);
        mapHeight = 800;
        mapWidth = 800;
    }

    /**
     * where all the objects that make part of this level are created
     */
    @Override
    public void create() {
        try {
            initActor();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(StartingLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
        initTextures();
        initPositions();
        initActors();
    }

    @Override
    public void update(float deltaTime) {
        playerMovement(deltaTime);
        collisions();
    }

    // =================== ALL THE METHODS ARE BELOW THIS LINE ===================
    /**
     * all the actors that need to be instantiated are here for a better code reading
     */
    private void initActor() throws CloneNotSupportedException {
        background = new BasicActor();
        win = new BasicActor();
        spaceShip = new PhysicsActor();
        meteor = new PhysicsActor();
        meteor.setEllipseBoundary();

        labels = new LabelTextGround();
        meteors = new ArrayList<>();

        //coordinates for the meteors being cloned
//        for (int i = 0; i < 4; i++) {
//            BasicActor meteorClone = meteor.clone();
//
//            meteorClone.setPosition(meteorCoordinates[2 * i], meteorCoordinates[2 * i + 1]);
//            mainStage.addActor(meteorClone);
//            meteors.add((PhysicsActor) meteorClone);
//        }
    }

    /**
     * all the textures that need to be instantiated are here
     */
    private void initTextures() {
        background.setTexture(new Texture(Gdx.files.internal("blueBackground.png")));
        win.setTexture(new Texture(Gdx.files.internal("end.png")));
        win.setVisible(false);
        spaceShip.setTexture(new Texture(Gdx.files.internal("playerShip.png")));
        meteor.setTexture(new Texture(Gdx.files.internal("meteorMedium.png")));
    }

    /**
     * all the textures that need to be instantiated are here
     */
    private void initPositions() {
        background.setPosition(0, 0);
        
        spaceShip.setPosition(400, 250);
        

        if (spaceShip.getWidth() == 0) {
            spaceShip.setOrigin(spaceShip.getWidth() / 2, spaceShip.getHeight() / 2);
        }
        spaceShip.setMaxSpeed(300);
        spaceShip.setDeceleration(40);
        spaceShip.setEllipseBoundary();
        spaceShip.setRotation(90);
        
        meteor.setPosition(22, 222);        
        win.setPosition(0, 0);
    }

    /**
     * all the actors that need to be added to the mainStage are here
     */
    private void initActors() {
        mainStage.addActor(background);
        mainStage.addActor(win);
        mainStage.addActor(meteor);
        mainStage.addActor(spaceShip);
        
    }

    /**
     * player movement properties, and key listeners
     */
    private void playerMovement(float deltaTime) {
        spaceShip.setAccelerationXY(0, 0);

        //KEYS INPUT
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            spaceShip.rotateBy(180 * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            spaceShip.rotateBy(-180 * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            spaceShip.addAccelerationAS(spaceShip.getRotation(), 80);
        }
//        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
//            spaceShip.decelerateSpeed(20);
//        }
        wrap();
    }

    /**
     * detects the collision between game objects
     */
    private void collisions() {
        if (spaceShip.overlap(meteor, true)) {
            win.addAction(gameOver);
            win.setVisible(true);
        }
    }
    
    private void wrap() {
        if(spaceShip.getX() < 0) {
            spaceShip.setX(mapWidth);
        }
        
        if(spaceShip.getX() > mapWidth) {
            spaceShip.setX(0);
        }
        
        if(spaceShip.getY() < 0) {
            spaceShip.setY(mapHeight);
        }
        
        if(spaceShip.getY() > mapHeight) {
            spaceShip.setY(0);
        }
        
    }

    //action to blink game over
    public Action gameOver = Actions.sequence(
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
