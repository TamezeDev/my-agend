package org.zeki.myagend.controller.theme;

import org.zeki.myagend.util.Path;

import java.io.*;

public class ThemeFileController {

    private String themSelected;

    public void saveConfigTheme() {
        File file = new File(Path.getInstance().getCONFIG_FILE());

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            bf.write("==== Config ====");
            bf.newLine();
            bf.newLine();
            bf.write("user_theme: " + themSelected);
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadConfigTheme() {
        File file = new File(Path.getInstance().getCONFIG_FILE());
        String[] userData = new String[2];
        userData[1] = "Default";
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("theme")) {
                        userData = line.split(" ");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return applySavedTheme(userData[1]);
    }

    private String applySavedTheme(String userTheme) {
        String pathTheme;
        switch (userTheme) {
            case "Spring" -> pathTheme = Path.getInstance().getSPRING_THEME_STYLE();
            case "Summer" -> pathTheme = Path.getInstance().getSUMMER_THEME_STYLE();
            case "Autumn" -> pathTheme = Path.getInstance().getAUTUMN_THEME_STYLE();
            case "Winter" -> pathTheme = Path.getInstance().getWINTER_THEME_STYLE();
            default -> pathTheme = Path.getInstance().getDEFAULT_THEME_STYLE();
        }
        return pathTheme;
    }

    public void setThemSelected(String themSelected) {
        this.themSelected = themSelected;
    }
}


