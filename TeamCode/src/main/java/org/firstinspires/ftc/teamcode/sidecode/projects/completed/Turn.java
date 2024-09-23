package org.firstinspires.ftc.teamcode.sidecode.projects.completed;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous
public class Turn extends OpMode {
    IMU imu;
    YawPitchRollAngles facing;
    int yawGoal;

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    void turnRight(double power) {
        fl.setPower(+power);
        bl.setPower(+power);
        fr.setPower(-power);
        br.setPower(-power);
    }

    void turnLeft(double power) {
        // turn left is just like turn right except it is reverse
        turnRight(-power);
    }

    void setPower(double power) {
        fl.setPower(+power);
        bl.setPower(+power);
        fr.setPower(+power);
        br.setPower(+power);
    }

    @Override
    public void init() {
        imu = (IMU) hardwareMap.get("imu");
        imu.resetYaw();

        // 90 degrees as the goal for the turn (yaw)
        yawGoal = 90;

        // get wheel motors from hardware map
        fl = (DcMotor) hardwareMap.get("fl");
        fr = (DcMotor) hardwareMap.get("fr");
        bl = (DcMotor) hardwareMap.get("bl");
        br = (DcMotor) hardwareMap.get("br");


        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {


        // read and display the yaw pitch and roll angles
        facing = imu.getRobotYawPitchRollAngles();
        int yaw = (int) facing.getYaw(AngleUnit.DEGREES);
        double pitch = facing.getPitch(AngleUnit.DEGREES);
        double roll = facing.getRoll(AngleUnit.DEGREES);

        telemetry.addData("yaw", yaw);
        telemetry.addData("pitch", pitch);
        telemetry.addData("roll", roll);



        // the -3 and the +3 is to add a tolerance. The three is the tolerance.
        // the necessary reason for tolerance is because the robot overshoots its set position due to being to fast.
        // however the robot can only go so slow.
        if (yaw < yawGoal-5) {
            turnRight(0.25);
        }
        else if (yaw > yawGoal+5) {
            turnLeft(0.25);
        }
        else {
            setPower(0); // all wheels set power to zero.
        }
        // the code a above will make the robot want to stay at 90 degrees (or what ever amount is set)
        // no matter what. It works very well!


    }
}
