package no.ntnu.game.firestore;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class GameRoom extends FirebaseClass {

    public enum GameMode {
        NONE, FASTEST_KNIGHT, LAST_KNIGHT
    }

    public enum GameStatus {
        CREATED, LOBBY, STARTING, PLAYING, COMPLETE
    }

    private String roomCode;
    private Date gameStartTime;

    private GameMode currentGameMode;
    private Player creatingPlayer;
    private Player joiningPlayer;

    private GameState creatingPlayerState;
    private GameState joiningPlayerState;
    private Date createdAt;
    private GameStatus status;

    public GameRoom() {
    }

    public GameRoom(Player creatingPlayer) {
        this.createdAt = new Date();
        this.creatingPlayer = creatingPlayer;
        this.status = GameStatus.CREATED;
        this.roomCode = generateRandomCode();
        this.setDocumentId(UUID.randomUUID().toString());
        creatingPlayerState = new GameState();
        joiningPlayerState = new GameState();
    }

    public static String generateRandomCode() {
        // Define alphabet
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Initialize random generator
        Random random = new Random();

        // Initialize StringBuilder to construct the random code
        StringBuilder sb = new StringBuilder();

        // Generate 4 random letters and append to StringBuilder
        for (int i = 0; i < 4; i++) {
            char randomChar = alphabet.charAt(random.nextInt(alphabet.length()));
            sb.append(randomChar);
        }

        // Convert StringBuilder to String and return
        return sb.toString();
    }

    public void addJoiningPlayer(Player player) {
        this.joiningPlayer = player;
    }

    public GameStatus getStatus() {
        return status;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public Player getCreatingPlayer() {
        return this.creatingPlayer;
    }

    public Player getJoiningPlayer() {
        return this.joiningPlayer;
    }

    public GameMode getGameMode() {
        return this.currentGameMode;
    }

    public void setGameMode(GameMode mode) {
        if (mode == null) {
            return;
        }
        if (mode.equals(GameMode.FASTEST_KNIGHT)) {
            this.joiningPlayerState.setScore(30);
            this.creatingPlayerState.setScore(30);
        } else {
            this.creatingPlayerState.setScore(0);
            this.joiningPlayerState.setScore(0);
        }
        this.currentGameMode = mode;
    }

    public void setGameStatus(GameStatus status) {
        this.status = status;
    }

    public GameState getCreatingPlayerState() {
        return creatingPlayerState;
    }

    public GameState getJoiningPlayerState() {
        return joiningPlayerState;
    }

    public Date getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(Date gameStartTime) {
        this.gameStartTime = gameStartTime;
    }
}
