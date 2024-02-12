package org.anne.zombiedeck.data;

import org.anne.zombiedeck.R;

public enum Danger {
    BLUE(0, R.string.blue, R.color.danger_blue),
    YELLOW(1, R.string.yellow, R.color.danger_yellow),
    ORANGE(2, R.string.orange, R.color.danger_orange),
    RED(3, R.string.red, R.color.danger_red);

    private final int index;
    private final int name;
    private final int color;

    Danger(int index, int name, int color) {
        this.index = index;
        this.name = name;
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public int getColor() {
        return color;
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
}
