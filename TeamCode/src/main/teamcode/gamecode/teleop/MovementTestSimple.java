package org.firstinspires.ftc.teamcode.gamecode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MovementTestSimple extends OpMode {

    DcMotor bl;
    DcMotor br;
    DcMotor fl;
    DcMotor fr;

    @Override
    public void init() {
        fr = (DcMotor) hardwareMap.get("c0");
        br = (DcMotor) hardwareMap.get("c1");
        fl = (DcMotor) hardwareMap.get("c2");
        bl = (DcMotor) hardwareMap.get("c3");


        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        fl.setPower(-gamepad1.left_stick_y);
        bl.setPower(-gamepad1.left_stick_y);

        br.setPower(-gamepad1.right_stick_y);
        fr.setPower(-gamepad1.right_stick_y);


    }
}
