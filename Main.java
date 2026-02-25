package com.bablu.reminder;

import com.bablu.reminder.ui.ConsoleUI;
import com.bablu.reminder.ui.JavaFXUI;

public class Main {
    public static void main(String[] args) {
        boolean useConsole = false;
        for (String a : args) {
            if ("--console".equalsIgnoreCase(a) || "-c".equalsIgnoreCase(a)) {
                useConsole = true;
                break;
            }
        }
        if (useConsole) {
            ConsoleUI.start();
        } else {
            JavaFXUI.launchApp(args);
        }
    }
}