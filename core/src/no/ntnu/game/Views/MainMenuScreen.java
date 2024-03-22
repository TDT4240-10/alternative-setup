package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;

public class MainMenuScreen extends Screen {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button playButton;
    private Button tempPlayButton;

    private Button tutorialButton;
    private Button rectSettingsButton;

    private ShapeRenderer shapeRenderer;
    //    private SpriteBatch spriteBatch;
    public MainMenuScreen(GameScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
//        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(SpriteBatch sb) {
        playButton = ButtonFactory.createPlayButton(300,1100);
        tutorialButton = ButtonFactory.createTutorialButton(300,800);
        rectSettingsButton = ButtonFactory.createRectSettingsButton(300,500);

        tempPlayButton = ButtonFactory.createTempPlayButton(300,200);

        // Create input listeners for buttons
        ButtonInputListener menuInputListener = new ButtonInputListener(playButton, gvm, null, sb);
        ButtonInputListener tutorialInputListener = new ButtonInputListener(tutorialButton, gvm, null, sb);
        ButtonInputListener settingsInputListener = new ButtonInputListener(rectSettingsButton, gvm, null, sb);
        ButtonInputListener temoPlayInputListener = new ButtonInputListener(tempPlayButton, gvm, null, sb);


        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(menuInputListener);
        inputMultiplexer.addProcessor(tutorialInputListener);
        inputMultiplexer.addProcessor(settingsInputListener);
        inputMultiplexer.addProcessor(temoPlayInputListener);

        Gdx.input.setInputProcessor(inputMultiplexer);

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

        // Render the menu button
        playButton.render(shapeRenderer,sb);
        tutorialButton.render(shapeRenderer,sb);
        rectSettingsButton.render(shapeRenderer,sb);
        tempPlayButton.render(shapeRenderer, sb);

        shapeRenderer.end();
    }

    @Override
    protected void handleInput() {
        // if play button is pressed, go to CreateOrJoinRoomScreen
//        if(playButton.isPressed()){
//            gvm.set(new CreateOrJoinRoomScreen(gvm));
//        }
    }

    @Override
    public void update(float dt) {

    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}