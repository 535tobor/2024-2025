package org.firstinspires.ftc.teamcode.gamecode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp



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

        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        // > means up, < means down. It's built like an airplane. //
        if (gamepad1.left_stick_y < 0) {

            fl.setPower(1);
            fr.setPower(1);
            bl.setPower(1);
            br.setPower(1);

        } else if (gamepad1.left_stick_y > 0) {

            fl.setPower(-1);
            fr.setPower(-1);
            bl.setPower(-1);
            br.setPower(-1);
            //this will make the robot reverse //

        }

        // the robot will go this way --> and this way <--- //
        else if (gamepad1.left_stick_x < 0) {
// when left joystick is pulled left, robot moves left. //
            fl.setPower(-1);
            fr.setPower(1);
            bl.setPower(1);
            br.setPower(-1);
        } else if (gamepad1.left_stick_x > 0) {
            // when left joystick is pulled right, robot move right //
            fl.setPower(1);
            fr.setPower(-1);
            bl.setPower(-1);
            br.setPower(1);
        } else if (gamepad1.right_stick_x > 0) {
            // when right joystick pulled right, robot turns right //

        }

    }
}




