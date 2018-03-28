# Archipelago path finder
## Prerequisites
Java 1.8.0_151 or higher <br/>
Maven 3.3.9 or higher
## Build
1. Run  `mvn clean compile assembly:single` this will generate a *.jar file with all the dependencies
2. Navigate to the `target` folder
3. Run `java -jar` to run the application
## Run
After you started the program, a window with a generated map should appear. Inside the command line you can list all the
available commands by using the`?list` command. In this prototype no search algorithms are implemented yet, only the random 
walk which you can start by using the `srw` command. 
