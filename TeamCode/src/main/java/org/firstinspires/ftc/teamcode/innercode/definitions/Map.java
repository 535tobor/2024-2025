package org.firstinspires.ftc.teamcode.innercode.definitions;

import android.hardware.HardwareBuffer;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.innercode.operations.output.EasierMovement;

public class Map {
    HardwareMap hardwareMap;
    DcMotor fl, fr, bl, br;
    public DcMotor fl() {
        /*
        * c = control hub
        * x = expansion hub
        * example: c0 means port 0 on the control hub
        * example 2: x2 means port 2 on the control hub
        * (this is for the motor ports only.
        * */

        /*
        * according to the schematic:
        * port 0 = front right
        * port 1 = back right
        * port 2 = front left
        * port 3 = back left
        * */

        // drive motors:
        fr = (DcMotor) hardwareMap.get("c0");
        br = (DcMotor) hardwareMap.get("c1");
        fl = (DcMotor) hardwareMap.get("c2");
        bl = (DcMotor) hardwareMap.get("c3");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        return fr;
    }




}
