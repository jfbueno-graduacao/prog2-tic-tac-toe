if not exist ".\bin\" mkdir .\bin\resources

del .\bin\*.class

javac Program.java -d .\bin -encoding UTF-8

copy .\resources\*.* .\bin\resources\*.*

cd bin

java Program

cd ..


