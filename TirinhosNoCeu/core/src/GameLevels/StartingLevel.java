/*
 * Game Project - TirinhosNoCeu with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 2.1.0
 */
package GameLevels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import static com.badlogic.gdx.math.MathUtils.random;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import java.util.ArrayList;
import PlayerClasses.BasicActor;
import PlayerClasses.PhysicsActor;

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
    private ArrayList<BasicActor> elementsToRemove;

    //sounds
    private Sound laserSound;
    private Music bgSound;
    private float soundVolume;

    //=== game size ===
    private final float mapWidth;
    private final float mapHeight;

    //=== label for score and timer ===
    private LabelTextGround timerLabel;
    private LabelTextGround scoreLabel;
    private int totalScore;

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

    /**
     * the update method takes care of the draw part of the game
     * and updates the game by frame per sec (deltaTime)
     * @param deltaTime 
     */
    @Override
    public void update(float deltaTime) {
        playerMovement(deltaTime);
        collisions();
        laserAndMeteorRemoval();

        timerLabel.setTimeElapsed(deltaTime);
        timerLabel.setText("Time: " + (int) timerLabel.getTimeElapsed());

        scoreLabel.setTimeElapsed(deltaTime);
        scoreLabel.setText("Score: " + totalScore);
    }

    // =================== ALL THE METHODS ARE BELOW THIS LINE ===================
    /**
     * all the actors that need to be instantiated are here for a better code reading
     */
    private void initActor() {
        background = new BasicActor();
        win = new BasicActor(); //the gameover place holder

        //player and enemy
        spaceShip = new PhysicsActor();
        meteor = new PhysicsActor();
        laser = new PhysicsActor();

        //score and timer
        timerLabel = new LabelTextGround();
        scoreLabel = new LabelTextGround();
        totalScore = 0;

        //grouping enemys and lasers
        meteors = new ArrayList();
        lasers = new ArrayList();
        elementsToRemove = new ArrayList();

        //audios
        soundVolume = 0.75f;
        laserSound = Gdx.audio.newSound(Gdx.files.internal("pew_pew.ogg"));

        //coordinates for the meteors being cloned 
        for (int i = 0; i < 6; i++) {
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

        timerLabel.positionSet(50, 700);
        scoreLabel.positionSet(600, 700);

        if (spaceShip.getWidth() != 0) {
            spaceShip.setOrigin(spaceShip.getWidth() / 2, spaceShip.getHeight() / 2);
        }

        //spaceship properties
        spaceShip.setMaxSpeed(625);
        spaceShip.setDeceleration(50);
        spaceShip.setEllipseBoundary();
        spaceShip.setRotation(90);

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

        //cloning the original meteor to create difference varieties
        //setting it's hitbox to be one ellipse
        for (PhysicsActor meteorClones : meteors) {
            meteorClones.setEllipseBoundary();
            meteorClones.setTypeOfList(meteors);
            mainStage.addActor(meteorClones);
        }

        mainStage.addActor(spaceShip);
        userInterface.addActor(timerLabel.getTimeLabel());
        userInterface.addActor(scoreLabel.getTimeLabel());
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
        warpMapWalls(spaceShip);
    }

    /**
     * detects the collision between the ship and the meteors
     */
    private void collisions() {
        for (PhysicsActor debries : meteors) {
            if (spaceShip.overlap(debries, false)) {
                win.addAction(gameOver);
                win.setVisible(true);
                togglePaused();
            }
        }
    }

    /**
     * makes the spaceship not getting "stuck" to the corners of the map and lets it warp to the other side
     */
    private void warpMapWalls(PhysicsActor pa) {
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
            //menu paused to be inserted here
        }

        /**
         * when the space key is pressed we proceed to: cloning the base laser which is content will be added to a new laserShot the laserShot gets the new respective properties and boundaries needed to act and we finalize with the actions to remove laser from memory after 2 seconds
         */
        if (keycode == Keys.SPACE) {
            laserSound.play(soundVolume);

            PhysicsActor laserShot = laser.cloned();
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
     * This method is used to verify if the lasers/meteors are not visible anymore or if the laser and meteors collided. If they have contact between each other, remove them from the game.
     */
    private void laserAndMeteorRemoval() {
        elementsToRemove.clear();

        for (PhysicsActor laserG : lasers) {
            //warpMapWalls(laserG);

            if (!laserG.isVisible()) {
                elementsToRemove.add(laserG);
            }

            for (PhysicsActor debries : meteors) {
                if (laserG.overlap(debries, false)) {
                    elementsToRemove.add(laserG);
                    debries.setVisible(false);
                    elementsToRemove.add(debries);
                    totalScore += 25 * scoreLabel.getTimeElapsed();
                }
            }
        }

        //loop to remove lasers and meteors from play and memory
        for (BasicActor pa : elementsToRemove) {
            pa.destroy();
        }

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
