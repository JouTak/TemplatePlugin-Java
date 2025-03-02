package org.joutak.joutaktemplate.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class MessageUtils {
    public static Component createTextMessage(String message, NamedTextColor color) {
        return Component.text(message, color);
    }
}
