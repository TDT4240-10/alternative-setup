# Star Knight 🤺

The Star knight game is a 2D platformer game developed using the LibGDX framework.

![StarKnight](https://github.com/TDT4240-10/star-knight/assets/92637738/39fd1e74-007a-4650-bf13-86cc2392883b)

## Getting Started 🚀

### Prerequisites 📋

- Java 17 or higher
- Android Studio Hedgehog | 2023.1.1 Patch 1 or higher

### Installation 🔧

> **Note:** As none of us in the group have an Android device, we primarily utilized the emulator present in Android Studio to get the game up and running.

1. Clone the repository
2. Open the project in Android Studio
3. Set up an android emulator or connect a physical device (Preferably the default - Pixel 3a)
4. Configure the project to run as an Android project
5. Enjoy the game 🎮

### Deploy to Android 📱

1. Navigate to the root of the project
2. Run the following command
   ```sh
   ./gradlew android:assembleRelease
   ```
3. The APK file will be located in `android/build/outputs/apk/`

> **Note:** An APK file is already compiled and located in the root of the project: `star-knight.apk`.

## Structure 🏗

```
├── android/                        Contains the Android specific code.
│ ├── src/no/ntnu/game/
│ │ ├── AndroidFirebase.java        All Firebase logic.
│ │ ├── AndroidLauncher.java        Main class for the Android module.
│ │ ├── FirebasePlayer.java         Firebase player class.
│ ├── google-services.json          Firebase configuration file.
├── core/                           Contains the main game logic.
│ ├── src/no/ntnu/game/
│ │ ├── callback/                   Contains Firebase callback implementations.
│ │ ├── Controllers/                Contains the different controllers of the game.
│ │ ├── Factory/                    Contains the different factories of the game.
│ │ ├── firestore/                  Contains the Firestore helper classes.
│ │ ├── Models/                     Contains the different models of the game.
│ │ ├── Settings/                   Contains setting implementations.
│ │ ├── Sound/                      Contains sound implementations.
│ │ ├── Views/                      Contains the different views of the game.
│ │ │ ├── Sprites/                  Contains the different sprites of the game.
│ │ │ ├── Tutorial/                 Contains the tutorial views.
│ │ ├── StarKnight.java             Main class for the game.
│ │ ├── CoreFirebase.java           Core class that implements the FirebaseInterface.
│ │ ├── FirebaseInterface.java      Interface for Firebase methods.
│ │ ├── FirebaseCompatible.java     Firebase helper Interface
├── gradle/                         Contains the gradle wrapper.
├── .gitignore                      Contains the files that should be ignored by git.
├── .README.md                      Documentation
└── ...
```

> **Note:** The packages 'callback' and 'firestore' are written with lowercase letters to avoid conflicts with the Firebase Firestore package.

## Third-party Libraries 📚

- [LibGDX](https://libgdx.com/) - Game development framework
- [GDX-Video](https://github.com/libgdx/gdx-video) - Video playback
- [Firebase](https://firebase.google.com/) - Backend services

## Firebase 🔥

This project uses Firebase for the backend. The Firebase SDK is included in the Android module.

The project is set up to use:

- Firebase Realtime Database. [Get Started](https://firebase.google.com/docs/database/quickstart#java_1)🚀
- Firebase Firestore. [Get Started](https://firebase.google.com/docs/firestore/quickstart#java_1)🚀

### Using Firebase in the project 🧑🏽‍🚒

Firebase is Android specific, i.e you cannot access Firebase methods from the Desktop or Core files.
All methods must be called through a class that implements the "FirebaseInterface" located in "core".
Any actual Firebase logic should be added into "AndroidFirebase".

For some explanation on how this works, see this [video](https://www.youtube.com/watch?v=WhuWqWVJ-_Y).
