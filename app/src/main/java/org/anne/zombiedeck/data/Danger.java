package org.anne.zombiedeck.data;

import org.anne.zombiedeck.R;

public enum Danger {
    BLUE(0, R.color.danger_blue),
    YELLOW(1, R.color.danger_yellow),
    ORANGE(2, R.color.danger_orange),
    RED(3, R.color.danger_red);

    private final int index;
    private final int color;

    Danger(int index, int color) {
        this.index = index;
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public static Danger valueOf(int index) {
        return switch (index) {
            case 0 -> BLUE;
            case 1 -> YELLOW;
            case 2 -> ORANGE;
            case 3 -> RED;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }

    public int getColor() {
        return color;
    }
}
