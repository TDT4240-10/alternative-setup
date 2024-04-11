package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.Controllers.PlayerController;
import no.ntnu.game.FirebaseInterface;
import no.ntnu.game.StarKnight;
import no.ntnu.game.callback.FirebaseCallback;
import no.ntnu.game.factory.button.RectangleButtonFactory;
import no.ntnu.game.factory.textfield.TextFieldFactory;
import no.ntnu.game.firestore.GameRoom;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Create or join room screen, linked with firestore to allow user to create or join a room
 *
 * @author Deen
 */

public class CreateOrJoinRoomScreen extends Screen {

    private Stage stage;
    private TextField roomId;

    private Texture logo;

    BitmapFont font; // Declare the font variable
    private ShapeRenderer shapeRenderer;

    private Button createRoomButton;

    private Button joinRoomButton;

    private PlayerController playerController;
    private GameRoomController gameRoomController;

    public CreateOrJoinRoomScreen(ScreenManager gvm) {
        super(gvm);

        playerController = PlayerController.getPlayerController();
        gameRoomController = GameRoomController.getInstance();

        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        TextFieldFactory textFieldFactory = new TextFieldFactory();

        roomId = textFieldFactory.createTextfield("");
        roomId.setSize(400, 200);
        roomId.setPosition((float) (Gdx.graphics.getWidth() / 2) - 200, 1050);

        joinRoomButton = rectButtonFactory.createButton("Join", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameRoomController.joinGameRoom(playerController.getPlayer(), roomId.getText().toUpperCase(), new FirebaseCallback<GameRoom>() {
                    @Override
                    public void onCallback(GameRoom result) {
                        if (result != null) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    gvm.set(new CreateGameLobbyScreen(gvm));
                                }
                            });
                        }
                    }
                });
                return true; // Indicate that the touch event is handled
            }
        });

        joinRoomButton.setSize(350, 200); // Set the size of the button
        joinRoomButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);
        createRoomButton = rectButtonFactory.createButton("Create", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameRoomController.createGameRoom(playerController.getPlayer(), new FirebaseCallback<GameRoom>() {
                    @Override
                    public void onCallback(GameRoom result) {
                        if (result != null) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    gvm.set(new CreateGameLobbyScreen(gvm));
                                }
                            });
                        }

                    }
                });
                return true;
            }
        });

        createRoomButton.setSize(350, 200); // Set the size of the button
        createRoomButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);



        stage = new Stage();
        stage.addActor(roomId);
        stage.addActor(joinRoomButton);
        stage.addActor(createRoomButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);
    }



    @Override
    public void render(SpriteBatch sb) {
        // to center the words
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font,"Enter a 4 letters unique room ID!");
        float textWidth = layout.width;
        final float CENTER_WORDS_X = (Gdx.graphics.getWidth() - textWidth) / 2;

        // Create input listeners for buttons


        sb.begin();
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // draw logo
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(logo, logoX, logoY);

        font.draw(sb, "Enter a 4 letters unique room ID!", CENTER_WORDS_X, 1000);
        sb.end();

        // draw stage and text field
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt){

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        logo.dispose();
        stage.dispose(); // Dispose of the stage
        System.out.println("Create or Join Room View Disposed");
    }

    @Override
    public void create(){

    }
}
