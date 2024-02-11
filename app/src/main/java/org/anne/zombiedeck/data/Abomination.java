package org.anne.zombiedeck.data;

import org.anne.zombiedeck.R;

public enum Abomination {
    HOBOMINATION(R.string.hobomination, R.drawable.abomination_hobomination, R.string.hobomination_rule),
    ABOMINACOP(R.string.abominacop, R.drawable.abomination_abominacop, R.string.abominacop_rule),
    ABOMINAWILD(R.string.abominawild, R.drawable.abomination_abominawild, R.string.abominawild_rule),
    PATIENT_0(R.string.patient_0, R.drawable.abomination_patient_0, R.string.patient_0_rule);

    private final int name;
    private final int image;
    private final int rule;

    Abomination(int name, int image, int rule) {
        this.name = name;
        this.image = image;
        this.rule = rule;
    }

    public int getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getRule() {
        return rule;
    }
}
