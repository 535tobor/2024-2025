/*
This example code was found on game manuel zero
at https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
 */

package org.firstinspires.ftc.teamcode.gamecode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp

public class TimeLimit3 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor fr,br,fl,bl;
        DcMotor extend; // Motor Arm Rotation (MAR)
        DcMotorEx mar;
        IMU imu;
        Servo mouth;
        Servo wrist;
        DistanceSensor ds;
        TouchSensor button;
        YawPitchRollAngles facing;
        double mouthAdd = 0;
        boolean yPressed = false;
        boolean aPressed = false;
        int yaw;
        /*
         * according to the schematic:
         * port 0 = front right
         * port 1 = back right
         * port 2 = front left
         * port 3 = back left
         * */

        DcMotor oLeft;
        DcMotor oRight;
        DcMotor oCenter;

        // drive motors:
        fr = (DcMotor) hardwareMap.get("c0");
        br = (DcMotor) hardwareMap.get("c1");
        fl = (DcMotor) hardwareMap.get("c2");
        bl = (DcMotor) hardwareMap.get("c3");


        oLeft = fl;
        oRight = fr;
        oCenter = br;

        mar = (DcMotorEx) hardwareMap.get("mar");
        extend = (DcMotor) hardwareMap.get("ext");

        mouth = (Servo) hardwareMap.get("mouth");
        wrist = (Servo) hardwareMap.get("wrist");

        imu = (IMU) hardwareMap.get("imu");
        imu.resetYaw();

        button = (TouchSensor) hardwareMap.get("button");
        // button for arm coming down



        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        mar.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        mar.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        oLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        oRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        oCenter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ds = (DistanceSensor) hardwareMap.get("ds");

        waitForStart();

        if (isStopRequested()) return;


        while (opModeIsActive()) {
            facing = imu.getRobotYawPitchRollAngles();
            yaw = (int) facing.getYaw(AngleUnit.DEGREES);
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            double slow_power = 0.4;




            // game-pad 2:

            telemetry.addData("mar pos", mar.getCurrentPosition());
            telemetry.addData("distance: ", ds.getDistance(DistanceUnit.INCH));
            telemetry.addData("left odom pos", oLeft.getCurrentPosition());
            telemetry.addData("right odom pos", oRight.getCurrentPosition());
            telemetry.addData("center odom pos", oCenter.getCurrentPosition());

            telemetry.addData("mouth", mouth.getPosition());
            telemetry.addData("wrist", wrist.getPosition());
            telemetry.addData("extend", extend.getCurrentPosition());


            telemetry.addData("imu", yaw);
// 1,223

            telemetry.update();

            /*
            *
            *
            * DO NOT slam the robot
            * this is prevented by doing a clockwise and counter clockwise
            * instead of jumping from 12:00 to 3:00
            * DO NOT do that!
            *
            *
            * */


            // mar

            // go clockwise with the letters to find larger numbers.
            // Starting with 'B' this is the smallest tick value (it does not scrape the ground)

            if (gamepad2.b) {
                mar.setTargetPosition(0);
                mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                mar.setPower(0.5);
            }
            else if (gamepad2.a) {
                mar.setTargetPosition(300);
                mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                mar.setPower(0.5);
            }
            else if (gamepad2.x) {
                mar.setTargetPosition(800);
                mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                mar.setPower(0.5);
            }

            else if (gamepad2.y) {
                mar.setTargetPosition(1080);
                mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                mar.setPower(0.5);
            }

            else if (gamepad2.left_stick_y != 0) {
                mar.setPower(gamepad2.left_stick_y);
            }

            else if (gamepad2.right_stick_y != 0) {
                extend.setPower(gamepad2.right_stick_y);
            }

            else {
                mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                // if no buttons are pressed (a,b,x, or y)
                // then lock to that position, nothing was changed. Let's keep it that way.
            }

            // mouth

            if (gamepad2.left_trigger == 1) {
                mouth.setPosition(0);
                // close
            }
            else if (gamepad2.left_bumper) {
                mouth.setPosition(0.25);
                // open
            }




            // make triggers and bumpers for the wrist instead.
            // wrist


            if (gamepad2.right_trigger == 1) {
                wrist.setPosition(0);
                // mouth of claw is facing down now
            }
            else if (gamepad2.right_bumper) {
                // mouth of claw is facing down now // up
                wrist.setPosition(0.8);
            }

            /*
            * safe variables for the wrist are numbers between: 0 and 0.4
            * */









            // game-pad 1:

            // first basket: 500
            // second basket: 2200

            // extend
            if (gamepad1.y) { // 12:00
                extend.setTargetPosition(-6_358); // -2200 * 2.89
                extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extend.setPower(0.5);
            }

            else if (gamepad1.b) { // 3:00
                extend.setTargetPosition(0);
                extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extend.setPower(0.5);
            }

            else if (gamepad1.a) { // 6:00
                extend.setTargetPosition(-1_734); // -600 * 2.89
                extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extend.setPower(0.5);
            }

            else if (gamepad1.x) { // 9:00
                extend.setTargetPosition(-2_890); // -1_000 * 2.89
                extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extend.setPower(0.5);
            }

            else if (gamepad2.left_stick_button && gamepad2.right_stick_button) {

                if (button.isPressed()) {

                    mar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                    mar.setTargetPosition(600);
                    mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    mar.setPower(0.5);
                    sleep(1000);

                    mar.setVelocity(-20);

                    mar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                }
                else {
                    mar.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    mar.setPower(0); // slowly go down until it hits the button

                }
            }

            else {
                extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                // if no buttons are pressed (a,b,x, or y)
                // then lock to that position, nothing was changed. Let's keep it that way.
            }






            if (gamepad1.left_bumper) {
                fl.setPower(-slow_power);
                bl.setPower(-slow_power);
                fr.setPower(+slow_power);
                br.setPower(+slow_power);
            }
            else if (gamepad1.right_bumper) {
                fl.setPower(+slow_power);
                bl.setPower(+slow_power);
                fr.setPower(-slow_power);
                br.setPower(-slow_power);
            }
            else if (gamepad1.dpad_up || gamepad2.dpad_up) {
                fl.setPower(+slow_power);
                bl.setPower(+slow_power);
                fr.setPower(+slow_power);
                br.setPower(+slow_power);
            }
            else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                fl.setPower(-slow_power);
                bl.setPower(-slow_power);
                fr.setPower(-slow_power);
                br.setPower(-slow_power);
            }
            else if (gamepad1.dpad_left || gamepad2.dpad_left) {
                fl.setPower(-slow_power);
                bl.setPower(+slow_power);
                fr.setPower(+slow_power);
                br.setPower(-slow_power);
            }
            else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                fl.setPower(+slow_power);
                bl.setPower(-slow_power);
                fr.setPower(-slow_power);
                br.setPower(+slow_power);
            }
            else {
                /*
                Not working correctly (lags)
                fl.setPower(frontLeftPower);
                bl.setPower(backLeftPower);
                fr.setPower(frontRightPower);
                br.setPower(backRightPower);*/

                if (gamepad1.left_stick_y > 0.1) {
                    fl.setPower(-gamepad1.left_stick_y);
                    bl.setPower(-gamepad1.left_stick_y);
                    fr.setPower(-gamepad1.left_stick_y);
                    br.setPower(-gamepad1.left_stick_y);
                }

                else if (gamepad1.left_stick_y < -0.1) {
                    fl.setPower(-gamepad1.left_stick_y);
                    bl.setPower(-gamepad1.left_stick_y);
                    fr.setPower(-gamepad1.left_stick_y);
                    br.setPower(-gamepad1.left_stick_y);
                }

                else if (gamepad1.right_stick_x > 0.1) {
                    fl.setPower(+gamepad1.left_stick_x);
                    bl.setPower(+gamepad1.left_stick_x);
                    fr.setPower(-gamepad1.left_stick_x);
                    br.setPower(-gamepad1.left_stick_x);
                }

                else if (gamepad1.right_stick_x < -0.1) {
                    fl.setPower(-gamepad1.left_stick_x);
                    bl.setPower(-gamepad1.left_stick_x);
                    fr.setPower(+gamepad1.left_stick_x);
                    br.setPower(+gamepad1.left_stick_x);
                }

                else if (gamepad1.left_stick_x > 0.1) {
                    fl.setPower(+gamepad1.right_stick_x);
                    bl.setPower(-gamepad1.right_stick_x);
                    fr.setPower(-gamepad1.right_stick_x);
                    br.setPower(+gamepad1.right_stick_x);
                }

                else if (gamepad1.left_stick_x < -0.1) {
                    fl.setPower(-gamepad1.right_stick_x);
                    bl.setPower(+gamepad1.right_stick_x);
                    fr.setPower(+gamepad1.right_stick_x);
                    br.setPower(-gamepad1.right_stick_x);
                }

                else {
                    fl.setPower(0);
                    bl.setPower(0);
                    fr.setPower(0);
                    br.setPower(0
                    );
                }
            }
        }
    }
}