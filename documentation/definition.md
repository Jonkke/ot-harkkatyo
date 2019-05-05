# Project definition

This is a game based on the classic Breakout game, where the player uses a paddle at the bottom of the screen to hit a ball in an attempt to hit bricks at the top. The goal is to clear all of the bricks by hitting them. The bricks have been arranged into 8 rows and 16 columns. At every other row, the color of the bricks changes, as do the points awarded for them. The topmost yellow bricks will yield 7 points each, the green ones 5, orange ones 3 and the red ones 1 point each. Total amount of points gained by breaking all 128 bricks is 512. The game will become progressively more difficult as the player gains more points: at several intervals during gameplay, the paddle will shrink down in size, while the ball speed will increase.

At the start of a game, the player has three (3) balls at his or her disposal. If they are not able to catch the ball and it goes below the screen, a new ball is spawned after cooldown period if there are balls remaining. If there are not, or if all the bricks have been cleared, the game will end and a popup will be shown to indicate this. The score is then saved for the player, and pressing any key will return the game to the main menu.

## User roles

As this is strictly a singleplayer game (although you could take turns with another player), no complicated user role models are needed. Player profiles can be saved, however.

## Features

The game will have a menu screen scene, which can be used to:
* Start a new game
* Continue game that has been paused
* Select a player from existing ones, or create a new player
* View high score
* Quit the game

The gameplay itself will be implemented in it's own JavaFX scene view, which will utilize / feature:
* A game loop that can be started, paused or stopped - during gameplay, all animations, physics, effects and input handlers are dependent on a running game loop
* An array of bricks of various colours located at the top part of the game arena, each colour yielding different amount of points
* A ball that is flying in the game arena, bouncing off the walls, bricks and the paddle
* A paddle that is used to hit the ball, and is controlled with the mouse

A database to hold player information and top scores will also be included. Database will be a single SQLite file.

