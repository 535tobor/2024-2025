package org.firstinspires.ftc.teamcode.innercode.enums;

public enum DsCaptions {
    arrow("--> "),
    empty("");

    private final String name;

    DsCaptions(final String name) {this.name = name;}

    public String display() {
        return name;
    }
}