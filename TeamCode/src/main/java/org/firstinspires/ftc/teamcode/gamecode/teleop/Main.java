package org.firstinspires.ftc.teamcode.gamecode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp



public class Main extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    Servo wrist;
    Servo claw;
    DcMotor shaft;
    DcMotor arm;
    Boolean ONCELER = false;

    @Override
    public void init() {

        fl = (DcMotor) hardwareMap.get("fl");
        fr = (DcMotor) hardwareMap.get("fr");
        bl = (DcMotor) hardwareMap.get("bl");
        br = (DcMotor) hardwareMap.get("br");
        wrist = (Servo) hardwareMap.get("wrist");
        claw = (Servo) hardwareMap.get("claw");
        shaft = (DcMotor) hardwareMap.get("shaft");
        arm = (DcMotor) hardwareMap.get("arm");
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.left_stick_y < 0) {  //this is flipped so the joystick will act normal for drivers//
        //forward//
            fl.setPower(-0.5);
            fr.setPower(-0.5);
            bl.setPower(-0.5);
            br.setPower(-0.5);

        } else if (gamepad1.left_stick_y > 0) {

            fl.setPower(0.5);
            fr.setPower(0.5);
            bl.setPower(0.5);
            br.setPower(0.5);
            //this will make the robot reverse //

        }


        if (gamepad1.right_stick_x > 0) {
            // when right joystick pulled right, robot turns right //
            fl.setPower(-0.5);
            fr.setPower(0.5);
            bl.setPower(-0.5);
            br.setPower(0.5);
        }

        if (gamepad1.right_stick_x < 0) {
            // when right joystick pulled left, robot turns left //
            fl.setPower(0.5);
            fr.setPower(-0.5);
            bl.setPower(0.5);
            br.setPower(-0.5);


        }






        else if (gamepad2.dpad_left){

            shaft.setPower(-1);
        }

        else if (gamepad2.dpad_right){

            shaft.setPower(1);
        }


         else if (gamepad2.a){

             claw.setPosition(0);


        }

         else if (gamepad2.y){

             claw.setPosition(1);
        }


        else {
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
            shaft.setPower(0);
        }



         if (gamepad1.dpad_down){

            arm.setPower(1);
        }

        else if (gamepad1.dpad_up){

            arm.setPower(-1);
        } else{
             arm.setPower(0);

         }




    }


}






