/*
 * Name: Wesley Chastain
 * Date Started: 9-15-2024
 * Objective: To create an odometer coordinate positioning algorithm
 * Overview: This algorithm originally was going to be a system that use x and y coordinates,
 however later it was reviewed and found that it would be less complex to use a left, right, and back
 positioning system instead, this way the same goal can be met but instead of doing it with x and y it
 will be done with left, right, and back variables using the context of the free spinning wheels.
 */



package org.firstinspires.ftc.teamcode.sidecode.examples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

// class goal for the set position goal of x and y values
class Goal {
    // use each free spinning wheel as a check point for setting the location position goal
    double left;
    double right;
    double back;

    // Constructor to initialize the variable
    public Goal(double left, double right, double back) {
        this.left = left;
        this.right = right;
        this.back = back;
    }
}


// class Pos for position of left, right, and back values
class Pos {
    // Declare the variable as an instance variable
    double left;
    double right;
    double back;

    // Constructor to initialize the variable
    public Pos(double left, double right, double back) {
        this.left = left;
        this.right = right;
        this.back = back;
    }
}


@Disabled
@Autonomous
public class Odom extends LinearOpMode {
    // setting variables
    IMU imu;

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    DcMotor leftOdom;
    DcMotor rightOdom;
    DcMotor backOdom;

    // setting a position goal
    Goal goal = new Goal(10, 10, 10);
    // The pos position of left, right, and back will have to update in the loop and use the updatePos method.
    Pos pos = new Pos(0,0, 0);


    // method to reset the cordinate position x and y values in the init stage
    void resetPos() {
        leftOdom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOdom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backOdom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // method that uses the odometer inputs and calculates the x and y positions of the robot.
    void updatePos() {
        pos.left = leftOdom.getCurrentPosition();
        pos.right = rightOdom.getCurrentPosition();
        pos.back = backOdom.getCurrentPosition();
    }

    // setPower method for DRY method to take effect (Don't Repeat Yourself) This way code is reduced
    void setPower(int flPower, int frPower, int blPower, int brPower) {
        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);
    }


    @Override
    public void runOpMode() {
        // pulling inputs from hardware map
        // internal measurement unit being pulled bellow
        imu = (IMU) hardwareMap.get("imu");


        // motor four wheel vars
        fl = (DcMotor) hardwareMap.get("fl");
        fr = (DcMotor) hardwareMap.get("fl");
        bl = (DcMotor) hardwareMap.get("fl");
        br = (DcMotor) hardwareMap.get("fl");


        // set necessary directions to the drive motors
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);


        // free spinning wheel Odom detection variables
        leftOdom = fl;
        rightOdom = fr;
        backOdom = bl;

        // init reset stage
        // resets all position values to zero for the free spinning wheel odometer encoders
        resetPos();

        waitForStart();


        while (opModeIsActive()) {
            /*
            update to new position of the robot by using the left, right, and back Odom positions
            calculated in the updatePos method using the free spinning odometer wheels.
            */

            updatePos();


            // current Odom readings
            telemetry.addData("pos.left : left wheel current", pos.left);
            telemetry.addData("pos.right : right wheel current", pos.right);
            telemetry.addData("pos.back : back wheel current", pos.back);



            // Odom reading goals
            telemetry.addData("goal.left : left wheel goal", goal.left);
            telemetry.addData("goal.right : right wheel goal", goal.right);
            telemetry.addData("goal.back : back wheel goal", goal.back);


            // with if/else statements go from most specific to least specific
            if (pos.left > goal.left && pos.right > goal.right) {
                /*
                if the position for both left and right is bigger than the wanted left and right goal
                then move robot backwards
                */
                setPower(-1,-1,-1,-1);
            }
            // if not both sides NOT greater than objective then continue
            else if (pos.left < goal.left && pos.right < goal.right) {
                /*
                if the position for both left and right is smaller than the wanted left and right goal
                then move the robot forwards
                */
                setPower(+1,+1,+1,+1);
            }

            else if (fl.getCurrentPosition() == 2) { // fl.getCurrentPosition() == 2 is just a place holder!!!
                // if not both sides NOT smaller than objective
                // this means that both left and right sides are unequal, the robot is at an angle
                // continue

                YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
                //angles.getPitch()


                // { # ? !!!!!! ? # }
                // before robot continues check imu if the robot is at an angle then turn, if not then strafe
                // { # ? !!!!!! ? # }
            }



            else if (pos.back > goal.back) {
                // robot position back wheel is placed towards further right, so move left
                // strafe left
                setPower(+1,-1,-1,+1);
            }
            // if the robot's back wheel is NOT bigger than the objective continue
            else if (pos.back < goal.back) {
                // robot position back wheel is placed towards further left, so move right
                // strafe right
                setPower(-1,+1,+1,-1);
            }


        }


    }
}