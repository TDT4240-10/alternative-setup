package no.ntnu.game.Views.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;


import no.ntnu.game.Controllers.PlayerController;
import no.ntnu.game.Views.GameModeTutorialScreen;
import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.Screen;
import no.ntnu.game.Views.ScreenManager;
import no.ntnu.game.Views.Tutorial.Tutorial1aScreen;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * For users to select which tutorial/ explanation they want to see
 *
 * @author Deen
 */
public class SelectTutorialScreen extends Screen {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button gameModeButton;

    private Button controlsButton;
    private Button exitButton;

    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private PlayerController playerController;


    public SelectTutorialScreen(ScreenManager gvm) {
        super(gvm);
        playerController = PlayerController.getPlayerController();
        logo = new Texture("tutorial.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        gameModeButton = rectButtonFactory.createButton("Game Modes", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new GameModeTutorialScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        gameModeButton.setSize(650, 200);
//        gameModeButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 800);
        gameModeButton.setPosition(centerButtonX(gameModeButton), 800);
        controlsButton = rectButtonFactory.createButton("Controls & Game Play", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1aScreen(gvm));
                return true;
            }
        });

        controlsButton.setSize(950, 200); // Set the size of the button
        controlsButton.setPosition(centerButtonX(controlsButton), 550);

        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            }
        });

        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition(centerButtonX(exitButton), 300);

        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(gameModeButton);
        stage.addActor(controlsButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

    }

    public float centerButtonX(Button button) {
        return (Gdx.graphics.getWidth() - button.getWidth()) / 2;
    }


    @Override
    public void render(SpriteBatch sb) {
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        sb.begin();
        sb.draw(logo, logoX, logoY);
        sb.end();

        // draw stage and text field
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {

    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
    @Override
    public void create(){

    }
}