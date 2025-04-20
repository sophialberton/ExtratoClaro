@echo off
javac -d bin -sourcepath src/main src/main/*.java src/main/controller/*.java src/main/model/*.java src/main/service/*.java src/main/repository/*.java
if %errorlevel% equ 0 (
    java -cp bin main.Main
)
pause