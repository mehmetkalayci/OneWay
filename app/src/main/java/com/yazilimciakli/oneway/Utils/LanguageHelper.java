package com.yazilimciakli.oneway.Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class LanguageHelper {

    public static void changeLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(configuration.locale);
        }
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}