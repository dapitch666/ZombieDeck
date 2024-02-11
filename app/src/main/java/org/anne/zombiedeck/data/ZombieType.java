package org.anne.zombiedeck.data;

import org.anne.zombiedeck.R;

public enum ZombieType {
    WALKER(R.string.walker, R.drawable.zombie_walker),
    RUNNER(R.string.runner, R.drawable.zombie_runner),
    FATTY(R.string.fatty, R.drawable.zombie_fatty),
    ABOMINATION(R.string.abomination, R.drawable.zombie_abomination);

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
