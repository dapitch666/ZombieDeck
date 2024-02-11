package org.anne.zombiedeck.data;

import org.anne.zombiedeck.R;

public enum ZombieType {
    WALKER(R.string.walker, R.drawable.walker),
    RUNNER(R.string.runner, R.drawable.runner),
    FATTY(R.string.fatty, R.drawable.fatty),
    ABOMINATION(R.string.abomination, R.drawable.abomination);

    private final int name;
    private final int image;

    ZombieType(int name, int image) {
        this.name = name;
        this.image = image;
    }

    public int getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
