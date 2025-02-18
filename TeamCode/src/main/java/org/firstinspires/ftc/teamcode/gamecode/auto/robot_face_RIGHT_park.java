package org.firstinspires.ftc.teamcode.gamecode.auto;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous

public class robot_face_RIGHT_park extends LinearOpMode {
    DcMotor fr,br,fl,bl,mar;
    TouchSensor button;

    @Override
    public void runOpMode() throws InterruptedException {
        /*
         * according to the schematic:
         * port 0 = front right
         * port 1 = back right
         * port 2 = front left
         * port 3 = back left
         * */

        telemetry.addData("WAIT!","Line the robot facing Right!");

        // drive motors:
        fr = (DcMotor) hardwareMap.get("c0");
        br = (DcMotor) hardwareMap.get("c1");
        fl = (DcMotor) hardwareMap.get("c2");
        bl = (DcMotor) hardwareMap.get("c3");
        mar = (DcMotor) hardwareMap.get("mar");

        button = (TouchSensor) hardwareMap.get("button");
        // button for arm coming down

        mar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // built in motor encoders
        fl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        double slow_power = 3.5;

        waitForStart();

        while (!button.isPressed()) {
            mar.setPower(-0.15); // slowly go down until it hits the button
            if (button.isPressed()) {
                mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
        }

        if (isStopRequested()) return;



        // strafes left away from wall
        setPower(-0.3,+0.3,+0.3,-0.3);
        setTarget(-100,+100,+100,-100);

        sleep(3000);

        setPower(0.3,0.3,0.3,0.3);
        setTarget(2500,2500,2500,2500);
        goToTarget();


        // robot moves forward 0.




    }



    public void setTarget(int fl_target, int fr_target, int bl_target, int br_target) {
        fl.setTargetPosition(fl_target);
        fr.setTargetPosition(fr_target);
        bl.setTargetPosition(bl_target);
        br.setTargetPosition(br_target);
    }

    public void setPower(double fl_power, double fr_power, double bl_power, double br_power) {
        fl.setPower(fl_power);
        fr.setPower(fr_power);
        bl.setPower(bl_power);
        br.setPower(br_power);
    }

    public void goToTarget() {
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


}