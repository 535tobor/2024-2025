package org.firstinspires.ftc.teamcode.sidecode.examples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "test")
public class Main extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    @Override
    public void init() {
        fl = (DcMotor) hardwareMap.get("fl");
        fr = (DcMotor) hardwareMap.get("fr");
        bl = (DcMotor) hardwareMap.get("bl");
        br = (DcMotor) hardwareMap.get("br");



        // set up direction for motors before it is told to go a direction
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);



    }

    @Override
    public void loop() {
        telemetry.addData("", gamepad1.left_stick_y);


        // left
        fl.setPower(gamepad1.left_stick_y);
        bl.setPower(gamepad1.left_stick_y);


        // right
        fr.setPower(gamepad1.right_stick_y);
        br.setPower(gamepad1.right_stick_y);


        /* forward robot
        if (gamepad1.left_stick_y < 0) {
            fl.setPower(0.5);
            fr.setPower(0.5);
            bl.setPower(0.5);
            br.setPower(0.5);
        }

        else {
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
        }*/

    }
}
