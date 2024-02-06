package org.anne.zombiedeck.data;

import org.anne.zombiedeck.R;

public enum Abomination {
    HOBOMINATION(0, R.string.hobomination, R.drawable.abomination_hobomination, R.string.hobomination_rule),
    ABOMINACOP(1, R.string.abominacop, R.drawable.abomination_abominacop, R.string.abominacop_rule),
    ABOMINAWILD(2, R.string.abominawild, R.drawable.abomination_abominawild, R.string.abominawild_rule),
    PATIENT_0(3, R.string.patient_0, R.drawable.abomination_patient_0, R.string.patient_0_rule);

    private final int index;
    private final int name;
    private final int image;
    private final int rule;

    Abomination(int index, int name, int image, int rule) {
        this.index = index;
        this.name = name;
        this.image = image;
        this.rule = rule;
    }

    public int getIndex() {
        return index;
    }

    public static Abomination valueOf(int index) {
        return switch (index) {
            case 0 -> HOBOMINATION;
            case 1 -> ABOMINACOP;
            case 2 -> ABOMINAWILD;
            case 3 -> PATIENT_0;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
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
