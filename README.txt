
Event Reminder System (Console + Modern JavaFX GUI)
===================================================

This bundle is pre-wired to compile and run on Windows with JavaFX 24 (path hardcoded).
For Linux/macOS commands, see below.

Folder Layout
-------------
EventReminderSystem/
 ├─ src/
 │   └─ com/bablu/reminder/
 │        ├─ Main.java
 │        ├─ core/
 │        │    ├─ Event.java
 │        │    ├─ EventManager.java
 │        │    ├─ ReminderScheduler.java
 │        ├─ storage/
 │        │    └─ Storage.java
 │        └─ ui/
 │             ├─ ConsoleUI.java
 │             ├─ JavaFXUI.java
 │             ├─ MainView.fxml
 │             └─ theme.css
 │   └─ icons/
 │        └─ reminder.png
 ├─ run.bat
 └─ README.txt

Windows (PowerShell or CMD)
---------------------------
Double-click **run.bat** (or run it in a terminal). It will:
1) Collect sources, 2) Compile with JavaFX, 3) Copy resources to `out/`, 4) Launch the GUI.

If JavaFX is installed in a different directory, edit `MP` inside `run.bat`.

Manual Commands (PowerShell)
----------------------------
$MP="C:\openjfx-24.0.2_windows-x64_bin-sdk\javafx-sdk-24.0.2\lib"
Get-ChildItem -Recurse src\*.java | ForEach-Object { $_.FullName } > sources.txt
javac --module-path "$MP" --add-modules javafx.controls,javafx.graphics -d out (Get-Content sources.txt)
# Copy resources
Copy-Item src\com\bablu\reminder\ui\theme.css -Destination out\com\bablu\reminder\ui -Force
Copy-Item src\com\bablu\reminder\ui\MainView.fxml -Destination out\com\bablu\reminder\ui -Force
New-Item -ItemType Directory -Force -Path out\icons | Out-Null
Copy-Item src\icons\reminder.png -Destination out\icons -Force
# Run
java --enable-native-access=javafx.graphics --module-path "$MP" --add-modules javafx.controls,javafx.graphics -cp out com.bablu.reminder.Main

Linux/macOS (bash)
------------------
export JAVAFX_HOME=/path/to/javafx-sdk-24/lib
find src -name "*.java" > sources.txt
javac --module-path "$JAVAFX_HOME" --add-modules javafx.controls,javafx.graphics -d out @sources.txt
# copy resources
mkdir -p out/com/bablu/reminder/ui out/icons
cp src/com/bablu/reminder/ui/theme.css out/com/bablu/reminder/ui/
cp src/com/bablu/reminder/ui/MainView.fxml out/com/bablu/reminder/ui/
cp src/icons/reminder.png out/icons/
# run
java --enable-native-access=javafx.graphics --module-path "$JAVAFX_HOME" --add-modules javafx.controls,javafx.graphics -cp out com.bablu.reminder.Main

Notes
-----
- Storage file location:
  Windows:  %USERPROFILE%\.event-reminder\events.csv
  Linux/macOS: $HOME/.event-reminder/events.csv
- Repeats supported: NONE, DAILY, WEEKLY.
- Scheduler checks every 30s and shows a toast + alert when due.
- The GUI is code-built (no FXML dependency). A sample FXML is included for your future use.
