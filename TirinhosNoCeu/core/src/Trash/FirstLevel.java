package Trash;

/**
 * Game Project - TirinhosNoCeu with the intentions to learn how to use classes and frameworks
 *
 * Version alpha - 1.0.0
 *
 * @author - Marco Silva
 */
import GameLevels.GameMainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.CommonScreen;
import com.mygdx.game.LabelTextGround;

/**
 *
 * @author Marco Silva
 */
public class FirstLevel extends CommonScreen {

    //==== Stage | Actors | Camera ====
    private BaseActor spaceShip;
    private BaseActor meteor;
    private BaseActor background;
    private BaseActor win;
    private Camera gameCamera;
    private LabelTextGround labels;
    //==== constants for game size and window size ===
    private final int mapWidth = 800;
    private final int mapHeight = 800;

    // ===== CONSTRUCTOR =====
    public FirstLevel(Game g) {
        super(g);
    }

    /**
     * here we instantiate the actors, textures and positions
     */
    @Override
    public void create() {
        initActor();
        initTextures();
        initPositions();
        addActors();
    }

    /**
     * updates the game
     *
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        playerMovement();
        labels.setTimeElapsed(deltaTime);
        labels.setText("Time: " + (int) labels.getTimeElapsed());
        collision();
        // camera();
    }

    /**
     * player movement
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
        //=== avoiding the margins of the window ===
        // clamp replaces the the if X < 0, or x > marginX
        spaceShip.setX(MathUtils.clamp(spaceShip.getX(), 0,
                getWidth() - spaceShip.getWidth()));
        spaceShip.setY(MathUtils.clamp(spaceShip.getY(), 0,
                getHeight() - spaceShip.getHeight()));
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

    /**
     * here we initialize all the actors for the scene
     *
     * @return actors
     */
    private void initActor() {
        background = new BaseActor();
        spaceShip = new BaseActor();

        meteor = new BaseActor();
        win = new BaseActor();
        labels = new LabelTextGround();
    }

    /**
     * initialize all the textures required to the respective actors
     *
     * @return textures
     */
    private void initTextures() {
        background.setTexture(new Texture("blueBackground.png"));
        spaceShip.setTexture(new Texture("playerShip.png"));
        meteor.setTexture(new Texture("meteorMedium.png"));
        win.setTexture(new Texture("end.png"));
        win.setVisible(false);
    }

    /**
     * sets the position of the elements at the scene
     *
     * @return positions
     */
    private void initPositions() {
        background.setPosition(0, 0);
        spaceShip.setPosition(getWidth() / 2, getHeight() / 2);
        meteor.setPosition(200, 350);
        win.setPosition(0, 0);
        
    }

    /**
     * adds the actors to the respective scenes
     *
     * @return adds actors
     */
    private void addActors() {
        mainStage.addActor(background);
        mainStage.addActor(meteor);

        meteor.setOrigin(meteor.getWidth() / 2, meteor.getHeight() / 2);
        meteor.addAction(Actions.parallel(
                Actions.alpha(1),
                Actions.rotateBy(360 * 5, 30),
                Actions.scaleTo(2, 2, 2),
                Actions.fadeOut(100)));
        mainStage.addActor(spaceShip);

        userInterface.addActor(labels.getTimeLabel());
        userInterface.addActor(win);
    }

    /**
     * defines the scene camera and respective placement
     *
     * @param keycode
     * @return camera
     */
//    private void camera() {
//        gameCamera = mainStage.getCamera();
//
//        //camera here it's centered on the player
//        //uses the clamp method , gets both axis for position and size to calculate
//        gameCamera.position.set(spaceShip.getX() + spaceShip.getOriginX(),
//                spaceShip.getY() + spaceShip.getOriginY(), 0);
//
//        //bounds the camera to the window
//        gameCamera.position.x = MathUtils.clamp(gameCamera.position.x,
//                getWidth() / 2, mapWidth - getWidth() / 2);
//
//        gameCamera.position.y = MathUtils.clamp(gameCamera.position.y,
//                getHeight() / 2, mapHeight - getHeight() / 2);
//
//        gameCamera.update();
//    }
    //key input to go to menu or pause the game
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
            Actions.fadeIn(25),
            Actions.sequence(
                    //shade color
                    Actions.color(new Color(1, 0, 0, 1), 1),
                    Actions.color(new Color(0, 0, 0, 1), 1)
            )
    );
}
