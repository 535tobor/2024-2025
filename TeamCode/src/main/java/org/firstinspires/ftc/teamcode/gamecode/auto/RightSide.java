/*
*
* Name: Wesley Chastain
* Date Started: 9-14-2024
* Objective: To use odometry
*
*
*
* */


package org.firstinspires.ftc.teamcode.gamecode.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.innercode.operations.input.Turn;

@Autonomous
public class RightSide extends LinearOpMode {
    // First lets create some variables
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    // motor variables for drive

    Turn turn;


    DcMotor leftOdom;
    DcMotor rightOdom;
    DcMotor backOdom;
    // motor variables for drive


    public void resetOdom() {
        leftOdom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOdom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backOdom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void runOpMode() {

        fl = (DcMotor) hardwareMap.get("fl");
        fr = (DcMotor) hardwareMap.get("fr");
        bl = (DcMotor) hardwareMap.get("bl");
        br = (DcMotor) hardwareMap.get("br");


        leftOdom = (DcMotor) hardwareMap.get("fl");
        rightOdom = (DcMotor) hardwareMap.get("fr");
        backOdom = (DcMotor) hardwareMap.get("bl");


        waitForStart();

        turn.main(90);



        // resetOdom(); // reset the odometry wheels for future use.

    }
}
