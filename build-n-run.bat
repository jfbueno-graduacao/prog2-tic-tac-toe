if not exist ".\bin\" mkdir .\bin

del .\bin\*.class

javac Program.java -d .\bin -encoding UTF-8

cd bin

java Program

cd ..


