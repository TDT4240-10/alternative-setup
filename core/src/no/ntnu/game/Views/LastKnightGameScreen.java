package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.Objects;

import no.ntnu.game.Controllers.GameController;
import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.Controllers.KnightController;
import no.ntnu.game.Models.PowerUp;
import no.ntnu.game.Models.PowerUpFactory;
import no.ntnu.game.Models.TimeLimitBar;
import no.ntnu.game.Models.TreeWithPowerUp;
import no.ntnu.game.Views.Sprites.ChoppingKnightSprite;
import no.ntnu.game.Views.Sprites.DeadKnightSprite;
import no.ntnu.game.Views.Sprites.IdleKnightSprite;
import no.ntnu.game.factory.button.CircleButtonFactory;
import no.ntnu.game.factory.button.RectangleButtonFactory;
import no.ntnu.game.firestore.GameRoom;

/**
 * Game Screen View class to render StarKnight game
 *
 * @author Han
 */
public class LastKnightGameScreen extends Screen {


    private Texture powerUpTextLogo;

    private Button leftButton;

    private Button rightButton;
    private Button exitButton;
    private boolean gameStart = false;

    private TreeWithPowerUp treeWithPowerUp;
    private ChoppingKnightSprite choppingKnightSprite;
    private IdleKnightSprite idleKnightSprite;
    private DeadKnightSprite deadKnightSprite;
    private KnightController knightController;

    private ShapeRenderer shapeRenderer;

    private TimeLimitBar timeLimitBar;

    private float temp = 0;

    private float timeLimit = 99999f;

    private float initialTime = 99999f;
    private PowerUp life1;
    private PowerUp life2;
    private PowerUp life3;

    private int score;

    private BitmapFont font;

    private Stage stage;
    private Texture timerLogo;
    private Texture bulletLogo;

    float bulletTimerX = (Gdx.graphics.getWidth() - 300f) / 2;
    float bulletTimerY = Gdx.graphics.getHeight() - 100f;
    private Texture animationTexture;
    private TextureRegion[] animationFrames;
    private float frameDuration = 0.1f; // Adjust this value to change animation speed
    private float stateTime = 0f;

    GameRoomController gameRoomController;

    public LastKnightGameScreen(ScreenManager gvm) {
        super(gvm);
        font = new BitmapFont(); // Assuming you have a font for rendering text
        // Load the background image
        animationTexture = new Texture("background.png");
        // Calculate the width of each frame
        int frameCount = 4; // Assuming 4 frames horizontally
        int frameWidth = animationTexture.getWidth() / frameCount;
        // Split the texture into individual frames
        TextureRegion[][] tmp = TextureRegion.split(animationTexture, frameWidth, animationTexture.getHeight());
        animationFrames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            animationFrames[i] = tmp[0][i];
        }
        powerUpTextLogo = new Texture("power_ups.png");
        timerLogo = new Texture("starknight_logo.png");
        bulletLogo = new Texture("bullet.png");

        gameRoomController = GameRoomController.getInstance();

        timeLimitBar = new TimeLimitBar(initialTime, timeLimit, 300f, 20f, (Gdx.graphics.getWidth() - 300f) / 2, Gdx.graphics.getHeight() - 50f);

        treeWithPowerUp = new TreeWithPowerUp();
        treeWithPowerUp.init();

        shapeRenderer = new ShapeRenderer();

        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        deadKnightSprite = new DeadKnightSprite();

        knightController = new KnightController("last_knight", -80, 500, treeWithPowerUp, timeLimitBar, timeLimit);

        knightController.setIdlePosition(-80, 500);
        knightController.setChoppingPosition(-99999, -99999);
        knightController.setDeadPosition(-99999, -99999);

        life1 = PowerUpFactory.createLivesPowerUp();
        life2 = PowerUpFactory.createLivesPowerUp();
        life3 = PowerUpFactory.createLivesPowerUp();

        float x_offset = 80;
        float y_offset = 100;

        // Create buttons
        CircleButtonFactory circleButtonFactory = new CircleButtonFactory();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        leftButton = circleButtonFactory.createButton("<", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                gvm.set(new CreateOrJoinRoomScreen(gvm));
                gameStart = true;

                if (Objects.equals(knightController.getDirection(), "right")) {
                    // Run chopping animation
                    knightController.moveLeft();
                }
                else {
                    knightController.stayLeft();
                }
                return true; // Indicate that the touch event is handled
            }
        });
        leftButton.setPosition(x_offset, x_offset + y_offset);
        leftButton.setSize(200,200);

        // Create buttons
        float rightButtonX = Gdx.graphics.getWidth() - 200 - x_offset;
        rightButton = circleButtonFactory.createButton(">", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Objects.equals(knightController.getDirection(), "left")) {
                    gameStart = true;

                    // Run chopping animation
                    knightController.moveRight();
                }
                else {
                    knightController.stayRight();
                }
                return true; // Indicate that the touch event is handled
            }
        });
        rightButton.setPosition(rightButtonX, x_offset + y_offset);
        rightButton.setSize(200,200);

        // Create buttons
        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                knightController.stopMusic();
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        float exitButtonX = Gdx.graphics.getWidth() - 300 - x_offset; // Adjust the offset as needed
        float exitButtonY = Gdx.graphics.getHeight() - 200 - x_offset; // Adjust the offset as needed
        exitButton.setPosition(exitButtonX, exitButtonY);
        exitButton.setSize(300,250);

        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

        // Adding left and right keystrokes to move the Knight
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                gameStart = true;

                if (keycode == Input.Keys.LEFT) {
                    if (Objects.equals(knightController.getDirection(), "right")) {
                        knightController.moveLeft();
                    } else {
                        knightController.stayLeft();
                    }
                } else if (keycode == Input.Keys.RIGHT) {
                    if (Objects.equals(knightController.getDirection(), "left")) {
                        knightController.moveRight();
                    } else {
                        knightController.stayRight();
                    }
                }
                return true; // Indicate that the key event is handled
            }
        });
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        // Update the time limit
        if (gameStart) {
            timeLimitBar.updateTime(dt);
            if (timeLimitBar.isTimeUp()) {
                gvm.set(new LastKnightEndGameScreen(gvm, score));
                knightController.stopMusic();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // Update the animation state time
        stateTime += Gdx.graphics.getDeltaTime();

        // Get the current frame index based on the state time and frame duration
        int frameIndex = (int) (stateTime / frameDuration) % animationFrames.length;

        // Draw the current frame
        sb.begin();
        sb.draw(animationFrames[frameIndex], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();
        treeWithPowerUp.draw(sb);

        timeLimitBar.render(shapeRenderer);
        knightController.renderBulletTimer(shapeRenderer);

        knightController.renderIdleKnight(sb);
        knightController.renderChoppingKnight(sb);
        knightController.renderDeadKnight(sb);

        knightController.renderLife1(sb);
        knightController.renderLife2(sb);
        knightController.renderLife3(sb);

        score = knightController.getScore();

        if (Objects.equals(knightController.update(Gdx.graphics.getDeltaTime()), "lose")) {
            gameRoomController.gameOver();
            gvm.set(new LastKnightEndGameScreen(gvm, score));
            return;
        };
        if (gameRoomController.getGameStatus().equals(GameRoom.GameStatus.COMPLETE)) {
            gameRoomController.gameOver();
            gvm.set(new LastKnightYouWinGameScreen(gvm, score));
            return;
        }

        shapeRenderer.end();

        sb.begin();
        sb.draw(powerUpTextLogo, 30, 80);
        font.getData().setScale(4f);
//        font.draw(sb, "Game Timer: ", bulletTimerX - 350, Gdx.graphics.getHeight() - 30f);
//        font.draw(sb, "Bullet Timer: ", bulletTimerX - 350, bulletTimerY);


        // Calculate the position to center the text on the screen
        float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
        float y = Gdx.graphics.getHeight() - 500; // Center vertically
        font.getData().setScale(4f);
        font.draw(sb, "Score: " + score, x, y);

        sb.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        animationTexture.dispose();

    }

    @Override
    public void create(){

    }
}
