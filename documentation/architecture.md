# Architecture

### Overview

The game consists of a main class, which is used to launch all other class instances. The game itself has been divided into various scenes, including the menu scene, player creation/selection scene, high score scene, and most importantly the game scene itself, where the actual gameplay takes place. A service class, SceneDirectorService is used to manage and switch between all these scenes. In this service, a single scene is rendered at all times, while different "scenes" are in reality just various JavaFX root nodes being swapped to the main root node.  

The gameplay itself has it's own service class, GameStateService, which holds all the information that has to do with a game session. That information includes the current state of the game (currently selected player, play duration, current points, balls left, etc...), as well as a list of all the game objects currently in play.  

There is also another service (not in the below class diagram though), DatabaseService, that is responsible for handling connection to the underlying database.  

Domain classes include a base GameObject class, which is inherited by the Ball, Paddle and Brick classes. There is also a special CollisionObject class, which is used to separate collision detections away from other GameObject logic. All GameObjects that are part of the physics engine have a CollisionObject attached to them. Other domain classes include Player and Score classes, which are used for storing information in the Player selection/creation scene and Highscore scene.  

Attempt has been made to separate the game logic from the UI as much as possible in the menu scenes. The drawing of the game scene is a different story. Since in this implementation, each game object is responsible for drawing themselves on the GraphicsContext they are supplied, it has not been possible (or even meaningful) to completely rip the drawing of the game away from the logic, since they share the same classes (that is, i.e. the Ball object is responsible for both updating itself and drawing itself).  

The high level architecture for the game is depcited in the image below:

![Image of class hierarchy](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/baseClassDiagram.jpg)

### Game loop

The GameScene implements the underlying game loop (an implementation of JavaFX's AnimationTimer), that is used to update the state of all game objects during gameplay. A list of all game ojects is contained within a special service class, GameStateService, that holds all information relating to a single gameplay session. This service class has it's own update() and draw() methods, that are used to update and render all GameObjects during a single update cycle. Every GameObject have their own subsequent update() and draw() implementations.  

This is a sequence diagram that depicts a high-level overview of a single iteration in the game loop:

![Sequence diagram of a single game loop iteration](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/gameLoopIteration.png)

### Data persistence

The game also has it's own data persistence layer, that uses a single SQLite database via the JDBC library interface to persist data. Persistent data includes player information and high scores.  

Classes related to the data persistence layer include DatabaseService, which holds information about database connection and shares it, the DAO classes, Dao (interface), PlayerDao, and ScoreDao, which are used to store and retrieve information to and from the database. PlayerDao is responsible for saving player data, while ScoreDao stores all the achieved scores from the game.


## Known weaknesses

Some classes, especially the PlayerScene could definitely use some refactoring. Data persistence layer in general was whipped up pretty quickly without proper planning, as it was not the main focus of the project.

