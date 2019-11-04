/*
 * Game Project - TirinhosNoCeu with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 1.5.0
 */
package testes;

import GameLevels.GameMainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import static com.badlogic.gdx.math.MathUtils.random;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.*;
import java.util.ArrayList;

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
    private PhysicsActor laser;

    //groups
    private ArrayList<PhysicsActor> meteors;
    private ArrayList<PhysicsActor> lasers;

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
        initActor();
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
    private void initActor() {
        background = new BasicActor();
        win = new BasicActor();

        spaceShip = new PhysicsActor();
        meteor = new PhysicsActor();
        laser = new PhysicsActor();
        labels = new LabelTextGround();
        meteors = new ArrayList();
        lasers = new ArrayList();

        //coordinates for the meteors being cloned 
        for (int i = 0; i < 8; i++) {
            int randX = random.nextInt(800);
            int randY = random.nextInt(800);

            /**
             * TODO: avoid spawning meteors close or "inside" the ship
             */
            PhysicsActor meteorClone = new PhysicsActor();
            meteorClone.copy(meteor);
            meteorClone.setTexture(new Texture(Gdx.files.internal("meteorMedium.png")));
            meteorClone.setPosition(randX, randY);
            meteors.add(meteorClone);
        }

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
        laser.setTexture(new Texture(Gdx.files.internal("laser.png")));
    }

    /**
     * all the textures that need to be instantiated are here
     */
    private void initPositions() {
        background.setPosition(0, 0);

        spaceShip.setPosition(400, 250);

        if (spaceShip.getWidth() != 0) {
            spaceShip.setOrigin(spaceShip.getWidth() / 2, spaceShip.getHeight() / 2);
        }

        //spaceship properties
        spaceShip.setMaxSpeed(625);
        spaceShip.setDeceleration(50);
        spaceShip.setEllipseBoundary();
        spaceShip.setRotation(90);

        //meteor properties
        meteor.setPosition(22, 222);
        meteor.setEllipseBoundary();

        //laser props
        laser.setMaxSpeed(470);
        laser.setDeceleration(0);
        laser.setEllipseBoundary();

        laser.setAutoAngle(true);

        win.setPosition(0, 0);
    }

    /**
     * all the actors that need to be added to the mainStage are here
     */
    private void initActors() {
        mainStage.addActor(background);
        mainStage.addActor(meteor);

        //cloning the original meteor to create difference varieties
        //setting it's hitbox to be one ellipse
        for (PhysicsActor meteorClones : meteors) {
            meteorClones.setEllipseBoundary();
            mainStage.addActor(meteorClones);
        }

        mainStage.addActor(spaceShip);
        labels = new LabelTextGround();
        mainStage.addActor(win);
    }

    /**
     * player movement properties, and key listeners
     */
    private void playerMovement(float deltaTime) {
        spaceShip.setAccelerationAS(0, 0);

        //KEYS INPUT
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            spaceShip.rotateBy(180 * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            spaceShip.rotateBy(-180 * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            spaceShip.addAccelerationAS(spaceShip.getRotation(), 125);
        }

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            System.out.println("PEW PEW");
        }
        wrap();
    }

    /**
     * detects the collision between game objects
     */
    private void collisions() {
        for (PhysicsActor debries : meteors) {
            if (spaceShip.overlap(debries, false)) {
                win.addAction(gameOver);
                win.setVisible(true);
            }
        }
    }

    /**
     * makes the spaceship not "stuck" to the corners, and lets it pass to the other side
     */
    private void wrap() {
        if (spaceShip.getX() < 0) {
            spaceShip.setX(mapWidth);
        }

        if (spaceShip.getX() > mapWidth) {
            spaceShip.setX(0);
        }

        if (spaceShip.getY() < 0) {
            spaceShip.setY(mapHeight);
        }

        if (spaceShip.getY() > mapHeight) {
            spaceShip.setY(0);
        }
    }

    /**
     * key listener for (M)enu and (P)ause
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.M) {
            game.setScreen(new GameMainMenu(game));
        }

        if (keycode == Keys.P) {
            togglePaused();
        }

        return false;
    }

    //action to blink game over
    public Action gameOver = Actions.sequence(
            Actions.alpha(0),
            Actions.show(),
            Actions.fadeIn(55),
            Actions.sequence(
                    //shade color
                    Actions.color(new Color(1, 0, 0, 1), 1),
                    Actions.color(new Color(0, 0, 0, 1), 1)
            )
    );

}
