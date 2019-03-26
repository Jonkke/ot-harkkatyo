# Project definition

Task: to create a Breakout-like game. The game will not be an exact copy of the original Breakout, and things like formation of bricks and rewarding of point might be different, but the basic idea behind gameplay will be the same. The player uses a paddle to hit a bouncing ball in the game arena to hit bricks at the top until they are all cleared.

## User roles

As this is strictly a singleplayer game (although you could take turns with another player), no complicated user role models are needed. Player profiles can be saved, however.

## Features

The game will have a menu screen scene, which can be used to:
* Start a new game (and choose a player)
* Create a new player
* View high score
* Possibly view a settings screen for changing various game settings
* Quit the game

The gameplay itself will be implemented in it's own JavaFX scene view, which will utilize / feature:
* A game loop that can be started, paused or stopped - during gameplay, all animations, physics, effects and input handlers are dependent on a running game loop
* An array of bricks of various strengths and colours located at the top part of the game arena
* A ball that is flying in the game arena
* A paddle that is used to hit the ball, and is controller either with the mouse or keyboard
* Possibly some nice visual effects when the bricks are hit or something else out-of-the-ordinary happens.
* Possibly an accurate physics simulation for the ball (perhaps using JBox2D, depending on how difficult to implement it turns out to be)

A database to hold player information and top scores will also be included. Database will be a single file.

