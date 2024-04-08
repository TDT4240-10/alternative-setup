package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * PowerupFactory class to create different Powerup  for the game
 *
 * @author Jeff
 */
public class PowerUpFactory {
    private static Texture powerUpTexture; // Texture containing all power-up sprites
    // Load the power-up texture
    public static void loadTextures() {
        powerUpTexture = new Texture("powerups.png");
    }
    // A power will be created randomly
    public static PowerUp createPowerUp() {
        loadTextures();
        Random random = new Random();
        int randomIndex = random.nextInt(2) ; // Adjust the range based on the number of power-up types

        switch (randomIndex) {
            case 0:
                return createLivesPowerUp();
            case 1:
                return createShieldPowerUp();
            // Add cases for other types of PowerUps as needed
            default:
                return createLivesPowerUp(); // Default to speed power-up
        }
    }

    // Method to create a Extra lives PowerUp
    private static PowerUp createLivesPowerUp() {
        return new PowerUp("heart", 2000,getTextureRegionForType("heart")); // Example duration: 5000 milliseconds
    }

    // Method to create a Shield PowerUp
    private static PowerUp createShieldPowerUp() {
        return new PowerUp("Shield", 5000,getTextureRegionForType("shield")); // Example duration: 10000 milliseconds
    }

    // Method to get the texture region for a specific type of power-up
    private static TextureRegion getTextureRegionForType(String type) {
        int index = getIndexForType(type); // Get the index of the sprite for this type
        int rows = 4;
        int spriteWidth = 32;
        int spriteHeight = 32;
        int row = index / rows;
        int col = index % rows;
        return new TextureRegion(powerUpTexture, col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
    }

    // Method to get the index of a power-up type
    private static int getIndexForType(String type) {
        // Example logic to map power-up types to indices
        switch (type.toLowerCase()) {
            case "heart":
                return 1;
            case "shield":
                return 4;
            // Add cases for other types...
            default:
                return 0; // Default to coin
        }
    }
}