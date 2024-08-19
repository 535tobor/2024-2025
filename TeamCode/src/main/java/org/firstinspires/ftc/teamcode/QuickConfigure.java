package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Quick Configure")

public class QuickConfigure extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor fl;
        DcMotor fr;
        DcMotor bl;
        DcMotor br;

        // define what each port will be called after the program is ran once.
        fr = (DcMotor) hardwareMap.get("port0");
        fl = (DcMotor) hardwareMap.get("port1");
        br = (DcMotor) hardwareMap.get("port2");
        bl = (DcMotor) hardwareMap.get("port3");

        // test to see which ports go backwards
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while(opModeIsActive()) {
            // See what port is what...
            fl.setPower(gamepad1.left_stick_y);
            bl.setPower(gamepad1.left_stick_x);
            fr.setPower(gamepad1.right_stick_y);
            br.setPower(gamepad1.right_stick_x);

            /*
            *
            *     fl                fr
            *   |______|         |------|
            *   |      | bl      |      |  br
            *   |______|         |------|
            *
            *
            *
            *
            *
            * */

        }
    }
}
