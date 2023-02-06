package com.example.welldrink.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.Button;

import com.example.welldrink.R;

import java.util.List;

public class ButtonHandler {

    public static int handleClick(Resources resources, int selected, List<Button> buttons, Button button, int bg, int txt) {
        if (selected != -1 && buttons.get(selected).equals(button)) {
            button.setBackgroundColor(bg);
            button.setTextColor(txt);
            selected = -1;
        } else {
            button.setBackgroundColor(resources.getColor(R.color.md_theme_light_primary));
            button.setTextColor(resources.getColor(R.color.md_theme_light_onPrimary));
            for (int i = 0; i < buttons.size(); i++)
                if (buttons.get(i).equals(button))
                    selected = i;
        }
        resetOthers(buttons, button, bg, txt);
        return selected;
    }

    private static void resetOthers(List<Button> buttons, Button button, int bg, int txt) {
        for (int i = 0; i < buttons.size(); i++)
            if (!buttons.get(i).equals(button))
                resetColor(buttons.get(i), bg, txt);
    }

    private static void resetColor(Button button, int bg, int txt) {
        button.setBackgroundColor(bg);
        button.setTextColor(txt);
    }

    public static boolean isDarkMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    public static int getBgDark(Resources resources) {
        return resources.getColor(R.color.md_theme_dark_inverseOnSurface);
    }

    public static int getTxtDark(Resources resources) {
        return resources.getColor(R.color.md_theme_dark_primary);
    }

    public static int getBgLight(Resources resources) {
        return resources.getColor(R.color.md_theme_light_inverseOnSurface);
    }

    public static int getTxtLight(Resources resources) {
        return resources.getColor(R.color.md_theme_light_primary);
    }

}
