package org.firstinspires.ftc.teamcode.gamecode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.innercode.operations.output.EasierMovement;

public class AutoPark extends LinearOpMode {
    DcMotor sideOdometer; // var used for odometry
    EasierMovement move;
    int goal = 5_000; // goal is set amount of ticks to reach.

    public void setup() {
        sideOdometer = (DcMotor) hardwareMap.get("fl");
    }

    @Override
    public void runOpMode() throws InterruptedException {

        // init button pressed
        setup();

        // start button pressed
        waitForStart();

        // strafe right distance

        while (sideOdometer.getCurrentPosition() < goal) {
            move.setPower(1,-1,-1,1);
        }
    }
}
