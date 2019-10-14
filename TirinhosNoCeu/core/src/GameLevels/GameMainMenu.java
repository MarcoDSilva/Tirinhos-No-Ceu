package GameLevels;

import com.mygdx.game.BasicActor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.CommonScreen;

/**
 *
 * @author MarcoSilva
 */
public class GameMainMenu extends CommonScreen {

    //===== constructor =====
    public GameMainMenu(Game game) {
        super(game);
    }

    // === abstract methods ====
    @Override
    public void create() {
        init();
    }

    // === verifies the keys that are clicked ===
    @Override
    public void update(float deltaTime) {
    }

    @Override
    public boolean keyDown(int keycode) {
        //if key pressed is one of the above
        if (keycode == Keys.ENTER) {
            game.setScreen(new FirstLevel(game));
        }

        if (keycode == Keys.ESCAPE) {
            Gdx.app.exit();
        }

        return false;
    }

    //===== METHODS =====
    /**
     * instantiate the interface Stage and respective actors to be added Background, text Title, and label
     *
     * @return menu design
     */
    public void init() {
        BasicActor background = new BasicActor();
        background.setTexture(new Texture(Gdx.files.internal("purple.png")));
        userInterface.addActor(background);

        BasicActor textTitle = new BasicActor();
        textTitle.setTexture(new Texture(Gdx.files.internal("title.png")));
        textTitle.setPosition(30, 310);
        userInterface.addActor(textTitle);

        BitmapFont font = new BitmapFont();
        String text = "Press Enter to Start, Esc to close";

        LabelStyle stylish = new LabelStyle(font, Color.CORAL);
        Label instructions = new Label(text, stylish);
        instructions.setFontScale(2);
        instructions.setPosition(20, 50);
        actions(instructions);
        userInterface.addActor(instructions);
    }

    /**
     * color blinking for menu
     *
     * @param a
     * @param actions
     */
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
