# OMBC - One More Breakout Clone

A small game project built as part of a university course. Includes some non-related exercises for the first three weeks, that can be found in the folder *laskarit*.

A ball-and-paddle -style game also featuring some bricks to break. In other words, another clone of the classic Breakout game, originally released by Atari in 1976. In this simple 2D game, the player controls a paddle that is used to hit a ball moving in the game area, in an attempt to hit an array of bricks at the top to gain points. The goal is to completely clear the array of bricks in as little time as possible, although this could prove more difficult than it sounds...

This project uses Java 8 and Maven 3.5.2.

## Documentation

[Usage instructions](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/usage_instructions.md)  
[Definition](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/definition.md)  
[Architecture](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/architecture.md)  
[Hours used](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/usedhours.md)
[Testing document](https://github.com/Jonkke/ot-harkkatyo/blob/master/documentation/testing.md)

## Releases

[Week 5](https://github.com/Jonkke/ot-harkkatyo/releases/tag/week5)  
[Week 6](https://github.com/Jonkke/ot-harkkatyo/releases/tag/week6)

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

### jar generation

A .jar file can be generated with
```
mvn package
```
The generated .jar can be run from the "target" directory with
```
java -jar OMBC-1.0-SNAPSHOT.jar
```

### Checkstyle report generation

To generate a checkstyle report in */target/site/checkstyle.html*, run the command
```
mvn jxr:jxr checkstyle:checkstyle
```

### JavaDoc generation

JavaDoc can be generated with the command
```
mvn javadoc:javadoc
```
