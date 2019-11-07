/*
 * Game Project - TirinhosNoCeu with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 2.1.0
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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import java.util.ArrayList;
import java.util.Iterator;

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
    private ArrayList<BasicActor> lasersToRemove;
    private ArrayList<BasicActor> meteorsToRemove;

    //sounds
    private Sound laserSound;
    private float soundVolume;

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
        laserRemoval();

        for (PhysicsActor l : lasers) {
            wrap(l);
        }
    }

    // =================== ALL THE METHODS ARE BELOW THIS LINE ===================
    /**
     * all the actors that need to be instantiated are here for a better code reading
     */
    private void initActor() {
        background = new BasicActor();
        win = new BasicActor();

        //player and enemy
        spaceShip = new PhysicsActor();
        meteor = new PhysicsActor();
        laser = new PhysicsActor();

        labels = new LabelTextGround();

        //grouping enemys and lasers
        meteors = new ArrayList();
        lasers = new ArrayList();
        lasersToRemove = new ArrayList();

        //audios
        soundVolume = 0.75f;
        laserSound = Gdx.audio.newSound(Gdx.files.internal("pew_pew.ogg"));

        //coordinates for the meteors being cloned 
        for (int i = 0; i < 4; i++) {
            int randX = random.nextInt(800);
            int randY = random.nextInt(800);

            /**
             * TODO: avoid spawning meteors close or "inside" the ship
             */
            PhysicsActor meteorClone = meteor.cloned();
            meteorClone.setTexture(new Texture(Gdx.files.internal("meteorMedium.png")));
            meteorClone.setPosition(randX, randY);
            meteorClone.setEllipseBoundary();
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
        laser.setOrigin(spaceShip.getOriginX() - ((spaceShip.getWidth() - laser.getWidth()) / 2),
                spaceShip.getOriginY() + ((spaceShip.getHeight() - laser.getHeight()) / 2));

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
            meteorClones.setTypeOfList(meteorsToRemove);
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
            spaceShip.addAccelerationAS(spaceShip.getRotation(), 235);
        }
        wrap(spaceShip);
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

            for (PhysicsActor l : lasers) {
                if (l.overlap(debries, false)) {
                    System.out.println(l.getX() + "");
                    System.out.println("CABOOOM");
                    System.out.println(debries + "foi atacado");
                    meteorsToRemove.add(debries);
                    lasersToRemove.add(l);
                }
            }
        }
    }

    /**
     * makes the spaceship not "stuck" to the corners, and lets it pass to the other side
     */
    private void wrap(PhysicsActor pa) {
        if (pa.getX() < 0) {
            pa.setX(mapWidth);
        }

        if (pa.getX() > mapWidth) {
            pa.setX(0);
        }

        if (pa.getY() < 0) {
            pa.setY(mapHeight);
        }

        if (pa.getY() > mapHeight) {
            pa.setY(0);
        }
    }

    /**
     * key listener for (M)enu , (P)ause and Space(shooting)
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

        if (keycode == Keys.SPACE) {
            System.out.println("PEW PEW");
            laserSound.play(soundVolume);

            PhysicsActor laserShot = new PhysicsActor();
            laserShot = laser.cloned();
            laserShot.setTexture(new Texture(Gdx.files.internal("laser.png")));
            laserShot.setVelocityAS(spaceShip.getRotation(), 420);
            laserShot.centerOrigin(spaceShip);
            laserShot.setTypeOfList(lasers);
            laserShot.setRectangleBoundary();
            lasers.add(laserShot);

            mainStage.addActor(laserShot);

            laserShot.addAction(
                    Actions.sequence(Actions.delay(2),
                            Actions.fadeOut(0.5f), Actions.visible(false)));
        }

        return false;
    }

    /**
     * name itself says it all
     */
    private void laserRemoval() {

        //making the laser destruct itself
        for (PhysicsActor laserG : lasers) {

            if (!laserG.isVisible()) {
                lasersToRemove.add(laserG);
            }
        }

        for (BasicActor pa : lasersToRemove) {
            pa.destroy();
        }

//        for (BasicActor m : meteorsToRemove) {           
//            m.destroy();
//        }
        lasersToRemove.clear();
        meteorsToRemove.clear();
    }

    //========= ACTIONS ==================
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
