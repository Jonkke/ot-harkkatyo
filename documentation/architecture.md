# Architecture

The high level architecture for the game is depcited in the image below (image will be updated). The game consists of a main class, which is used to launch all other class instances. The game itself has been divided into various scenes, including the menu scene, player creation/selection scene, high score scene, and most importantly the game scene itself, where the actual gameplay takes place. A service class, SceneDirectorService is used to manage and switch between all these scenes.  

The GameScene implements the underlying game loop (an implementation of JavaFX's AnimationTimer), that is used to update the state of all game objects during gameplay. A list of all game ojects is contained within a special service class, GameStateService, that holds all information relating to a single gameplay session. This service class has it's own update() method, that is used to update all GameObjects during a single update cycle. Every GameObject has it's own subsequent update() implementation.

![Image of class hierarchy](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/baseClassDiagram.jpg)

This is a sequence diagram that depicts a high-level overview of a single iteration in the game loop:

![Sequence diagram of a single game loop iteration](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/gameLoopIteration.png)
