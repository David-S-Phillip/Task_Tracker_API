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


