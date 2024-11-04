echo "Build Project"

./gradlew clean build

echo "Start Server"

cd ./build/libs
java -jar demo-0.0.1-SNAPSHOT.jar