package edu.iu.habahram.ducksservice.model;

public enum Type {

    ACOUSTIC, ELECTRIC;

    public String toString() {
        return switch (this) {
            case ACOUSTIC -> "acoustic";
            case ELECTRIC -> "electric";
            default -> "unspecified";
        };
    }
}
