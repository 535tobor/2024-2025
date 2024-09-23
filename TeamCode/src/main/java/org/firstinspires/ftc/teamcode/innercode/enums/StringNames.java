package org.firstinspires.ftc.teamcode.innercode.enums;

public enum StringNames {
    unknown("unknown");
    // enums are used for easier calling of variables

    private final String text;

    StringNames(final String text) {this.text = text;}

    public String getString() {
        return text;
    }
}