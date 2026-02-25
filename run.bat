@echo off
echo === Compiling EventReminderFX.java ===
javac --module-path "C:\Users\bk\Downloads\openjfx-24.0.2_windows-x64_bin-sdk\javafx-sdk-24.0.2\lib" --add-modules javafx.controls,javafx.fxml EventReminderFX.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo === Running EventReminderFX ===
java --module-path "C:\Users\bk\Downloads\openjfx-24.0.2_windows-x64_bin-sdk\javafx-sdk-24.0.2\lib" --add-modules javafx.controls,javafx.fxml EventReminderFX

pause
