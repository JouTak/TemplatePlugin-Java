package org.joutak.joutaktemplate.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatTime(LocalDateTime time) {
        return time.format(FORMATTER);
    }
}
