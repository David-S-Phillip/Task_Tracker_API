# Welcome to Docker for life

## What a Dockerfile is
Aweh ! docker is basically a controlled environment you 'build' which ensures your program will always execute perfectly unless there are code issues.
a dockerfile is the blueprint of this perfect environment.
Inside a dockerfile you state the Operating system, the .jar file you want to use and the entry point to make it easier to understand ill explain the cycle. 

First a dockerfile --> then we create a docker image --> then we create a docker container --> this is the program running in a perfect and controlled environment.

## What needs to be done before attempting anything 
First ensure your pom.xml file has the following plugin: maven-shade-plugin
This ensure when we run mvn clean package all our dependencies are included in the .jar file creating a big boy jar e.g fat jar. #no body shaming we just like curves this side.
Make sure the < mainclass > tags include the full package name and class name of your runner class e.g <mainClass>za.co.gabos.task.App</mainClass>

## How to run the dockerfile using the makefile
First run mvn clean package --> this packages your code in a fat jar your .jar will be in the /target folder
```bash
mvn clean package
```
