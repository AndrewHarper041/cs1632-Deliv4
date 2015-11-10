@echo off

javac -cp .;lib/junit-4.12.jar -d lib *.java
java -cp .;lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar;lib org.junit.runner.JUnitCore ArraysTest 
 
echo.
