package me.boomboompower.alert.simplechatalert.utils;

import java.util.regex.Pattern;

public enum ChatColor {

    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    LIGHT_GREEN('a'),
    LIGHT_AQUA('b'),
    LIGHT_RED('c'),
    LIGHT_PURPLE('e'),
    YELLOW('e'),
    WHITE('f'),
    MAGIC('k', true),
    BOLD('l', true),
    ITALIC('o', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    RESET('r', false, true)
    ;

    private static final Character CODE = '\u00A7';

    private Character character;
    private boolean isSpecial;
    private boolean isReset;

    ChatColor(Character character) {
        this(character, false, false);
    }

    ChatColor(Character character, boolean special) {
        this(character, special, false);
    }

    ChatColor(Character character, boolean special, boolean isReset) {
        this.character = character;
        this.isSpecial = special;
        this.isReset = isReset;
    }

    @Override
    public String toString() {
        return CODE.toString() + character;
    }

    public char getColorCode() {
        return CODE;
    }

    public char getColorChar() {
        return character;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public boolean isReset() {
        return isReset;
    }

    public boolean isColor() {
        return !isSpecial && this != RESET;
    }

    public String stripColors(final String message) {
        if (message != null && !message.isEmpty()) {
            return Pattern.compile("(?i)" + String.valueOf(CODE) + "[0-9A-FK-OR]").matcher(message).replaceAll("");
        } else {
            return null;
        }
    }
}
