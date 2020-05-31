all: app-jar
app-jar: ../bin/App.jar
app-class: ../bin/app/App.class
functions-class: ../bin/app/App.class
../bin/App.jar: app-class functions-class
	cd ../bin/ ; jar cfe App.jar app/App app/App.class app/Functions.class
../bin/app/App.class: app/App.java app/Functions.java
	javac -parameters -d ../bin/ app/App.java app/Functions.java
clean:
	rm -rf ../bin/*

