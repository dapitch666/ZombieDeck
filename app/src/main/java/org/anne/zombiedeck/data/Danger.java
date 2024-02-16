package org.anne.zombiedeck.data;

import org.anne.zombiedeck.R;

/**
 * Enum for the danger level
 * Defines the name, color, and index for each danger level
 * Also provides methods to get the next and previous danger levels
 */
public enum Danger {
    BLUE(0, R.string.blue, R.color.danger_blue),
    YELLOW(1, R.string.yellow, R.color.danger_yellow),
    ORANGE(2, R.string.orange, R.color.danger_orange),
    RED(3, R.string.red, R.color.danger_red);

    private final int index;
    private final int name;
    private final int bgColor;

    Danger(int index, int name, int bgColor) {
        this.index = index;
        this.name = name;
        this.bgColor = bgColor;
    }

    public int getIndex() {
        return index;
    }

    public int getBgColor() {
        return bgColor;
    }

    public int getName() {
        return name;
    }

    public Danger previous() {
        return switch (this) {
            case BLUE -> throw new IllegalStateException("Unexpected value: " + this);
            case YELLOW -> BLUE;
            case ORANGE -> YELLOW;
            case RED -> ORANGE;
        };
    }

    public Danger next() {
        return switch (this) {
            case BLUE -> YELLOW;
            case YELLOW -> ORANGE;
            case ORANGE -> RED;
            case RED -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public int getTextColor() {
        return this == YELLOW ? R.color.black : R.color.white;
    }
}
