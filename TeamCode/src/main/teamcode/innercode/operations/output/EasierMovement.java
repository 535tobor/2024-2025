package org.firstinspires.ftc.teamcode.innercode.operations.output;

import com.qualcomm.robotcore.hardware.DcMotor;

public class EasierMovement {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    public void setPower(double fl_power, double bl_power, double fr_power, double br_power) {
        fl.setPower(+fl_power);
        bl.setPower(+bl_power);
        fr.setPower(+fr_power);
        br.setPower(+br_power);
    }
}
