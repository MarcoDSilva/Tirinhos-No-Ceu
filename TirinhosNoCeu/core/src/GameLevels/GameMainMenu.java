package GameLevels;

import com.mygdx.game.BasicActor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 *
 * @author MarcoSilva
 */
public class GameMainMenu extends Game {

    //===== properties =====
    private Stage userInterface;
    private final Game game;

    //===== constructor =====
    public GameMainMenu(Game game) {
        this.game = game;
        create();
    }

    @Override
    public void create() {
        init();

    }

    public void render(float deltaTime) {
        //VERIFIES THE KEY THAT IS PRESSED
        //
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void show() {
    }

    public void hide() {
    }

    //===== METHODS =====
    //INITIALIZE THE STAGE AND BASE ACTOR
    //BITMAP AND LABELS AS WELL
    public void init() {
        userInterface = new Stage();
        BasicActor background = new BasicActor();
        background.setTexture(new Texture(Gdx.files.internal("imagenfundo.png")));

        BasicActor textTitle = new BasicActor();
        textTitle.setTexture(new Texture(Gdx.files.internal("textinho.png")));

        BitmapFont font = new BitmapFont();
        String text = "Press Enter to Start, Esc to close";

        LabelStyle stylish = new LabelStyle(font, Color.CORAL);
        Label instructions = new Label(text, stylish);
        instructions.setFontScale(4);
        instructions.setPosition(200, 100);

        actions(instructions);
    }

    public void actions(Label a) {
        a.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(1, 1, 0, 1), 0.5f),
                                Actions.delay(0.5f),
                                Actions.color(new Color(0.5f, 0.5f, 0, 1), 0.5f))
                )
        );
    }
}
