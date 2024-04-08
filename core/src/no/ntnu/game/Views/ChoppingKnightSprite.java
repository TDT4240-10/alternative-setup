package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Chopping Knight Sprite View class to render chopping knight animation
 *
 * @author Han
 */
public class ChoppingKnightSprite {
    private Animation<TextureRegion> knightAnimation;
    private TextureRegion[] knightFrames;
    private Sprite knightSprite;
    private float stateTime;
    private float x, y; // Position of the sprite

    public ChoppingKnightSprite() {
//        currentFrameIndex = 0;
//        animationRunning = false;

        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        Texture frame0= new Texture("chopping_frames/frame0.png");
        Texture frame1 = new Texture("chopping_frames/frame1.png");
        Texture frame2 = new Texture("chopping_frames/frame2.png");
        Texture frame3 = new Texture("chopping_frames/frame3.png");
        Texture frame4 = new Texture("chopping_frames/frame4.png");
        Texture frame5 = new Texture("chopping_frames/frame5.png");

        knightFrames = new TextureRegion[6];
        knightFrames[0] = new TextureRegion(frame0);
        knightFrames[1] = new TextureRegion(frame1);
        knightFrames[2] = new TextureRegion(frame2);
        knightFrames[3] = new TextureRegion(frame3);
        knightFrames[4] = new TextureRegion(frame4);
        knightFrames[5] = new TextureRegion(frame5);

        knightAnimation = new Animation<>(0.1f, knightFrames); // Frame duration 0.1 seconds
        knightSprite = new Sprite(knightAnimation.getKeyFrame(0));

    }

    public void flipDirection() {
        for (TextureRegion region : knightFrames) {
            region.flip(true, false);
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void render(SpriteBatch batch) {
        batch.begin();
        // Render the sprite on the screen
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = knightAnimation.getKeyFrame(stateTime, true);

        // Draw the sprite onto the batch with scaling
        int knightWidth = currentFrame.getRegionWidth() * 6; // Original width multiplied by scale factor
        int knightHeight = currentFrame.getRegionHeight() * 6; // Original height multiplied by scale factor
        knightSprite.setRegion(currentFrame);
        knightSprite.setBounds(x, y, knightWidth, knightHeight); // Set bounds with scaled dimensions
        knightSprite.draw(batch);
        batch.end();

    }

    public void setBounds (float x, float y, float width, float height) {
        knightSprite.setBounds(x, y, width, height);
    }

        // Additional methods for sprite animation, if needed

    public void dispose() {
        // Dispose of the sprite's texture when no longer needed
        for (TextureRegion frame : knightFrames) {
            frame.getTexture().dispose();
        }
    }
}
