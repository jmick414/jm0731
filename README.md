# Big Tools 2 Go
A fictional point-of-sale tool for a store that rents big tools.  🛠️

## Usage
### Running the application
Should be run with Java 17 or above. Have a look at [SDKMAN!](https://sdkman.io/) for a nice tool to manage various SDKs, including Java.

#### Via Gradle Wrapper
From the base directory, `./gradlew bootRun`

#### Via JAR
Run `./gradlew clean bootJar` from the base directory to create an executable JAR file.
Then execute with, e.g., `java -jar build/libs/bigtools2go-0.0.1-SNAPSHOT.jar`

### Stopping the application
The application runs in a continuous loop to facilitate sequential checkouts by a store clerk. To stop the application it is recommended to use CTRL+C while in the terminal (SIGINT).

### Execute tests
Run `./gradlew test` from the base directory.

## Notes
- Uses Spring Boot, largely to take advantage of the in-memory H2 database to simulate a more realistic environment. This is advantageous as tools and their associated properties can be updated without code modification.
