# OMBC - One More Breakout Clone

A small game project built as part of a university course. Includes some non-related exercises for the first three weeks, that can be found in the folder *laskarit*.

A ball-and-paddle -style game also featuring some bricks to break. In other words, another clone of the classic Breakout game, originally released by Atari in 1976. In this simple 2D game, the player controls a paddle that is used to hit a ball moving in the game area, in an attempt to hit an array of bricks at the top to gain points. The goal is to completely clear the array of bricks while trying to keep the count of lost balls (=balls the player could not bounce back) as small possible.

This project uses Java 8 and Maven 3.5.2.

## Documentation

[Definition](https://github.com/Jonkke/ot-harkkatyo/blob/master/ombc-project/documentation/definition.md)  
[Hours used](https://github.com/Jonkke/ot-harkkatyo/blob/master/ombc-project/documentation/usedhours.md)

## Running & Testing

### Testing

Tests can be run with
```
mvn test
```
And a Jacoco code coverage report (found in */target/site/jacoco*) can be generated with
```
mvn test jacoco:report
```

### Running

You can launch the game by running the command
```
mvn compile exec:java -Dexec.mainClass=main.App
```
