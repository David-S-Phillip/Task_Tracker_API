# compiling code to check for build success
.PHONY: compile
compile:
	@echo "running mvn clean compile bruv"
	mvn clean compile

# this command runs the test suite
.PHONY: test
test:
	@echo "running unit test suite"
	mvn clean test

# package code into a .jar file
.PHONY: package
package:
	@echo "creating .jar file inside /target folder"
	mvn clean package

# Stop and remove any existing containers with the same name
.PHONY: clean-container
clean-container:
	@echo "removing any containers with the name task-api-containers"
	-docker rm -f task-api-container

# creates the docker image, we run clean-container and then make package
.PHONY: build-image
build-image: package clean-container
	@echo building the docker image
	docker build --no-cache -t task-tracker-api .

# Creates the docker container by running the docker image
.PHONY: build-container
build-container:
	@echo creating the docker container
	docker run -d -p 7070:7070 --name task-api-container task-tracker-api

# view live container logs for debugging bruv
.PHONY: logs
logs:
	docker logs -f task-api-container

# stop the running container
.PHONY: stop
stop:
	docker stop task-api-container

# remove the container after stopping it
.PHONY: remove
remove:
	@echo removing docker container
	docker rm task-api-container
