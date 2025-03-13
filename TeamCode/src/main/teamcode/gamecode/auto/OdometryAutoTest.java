package org.firstinspires.ftc.teamcode.gamecode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous
@Disabled
public class OdometryAutoTest extends OpMode {
    DcMotor oLeft;
    DcMotor oRight;
    DcMotor oCenter;
    IMU imu;
    boolean turnLoop = true;
    YawPitchRollAngles facing;
    int yaw;
    double pitch;
    double roll;

    DcMotor fr,br,fl,bl;
    DcMotor mar, extend;
    double powerSet = 0.5;
    double oLeft_target;
    double oRight_target;
    double oCenter_target;


    @Override
    public void init() {





        /*
         * according to the schematic:
         * port 0 = front right, oRight
         * port 1 = back right, oCenter
         * port 2 = front left, oLeft
         * port 3 = back left
         * */

        // drive motors:
        fr = (DcMotor) hardwareMap.get("c0");
        br = (DcMotor) hardwareMap.get("c1");
        fl = (DcMotor) hardwareMap.get("c2");
        bl = (DcMotor) hardwareMap.get("c3");

        mar = (DcMotor) hardwareMap.get("mar");
        extend = (DcMotor) hardwareMap.get("ext");
        imu = (IMU) hardwareMap.get("imu");
        imu.resetYaw();

        facing = imu.getRobotYawPitchRollAngles();
        yaw = (int) facing.getYaw(AngleUnit.DEGREES);
        pitch = facing.getPitch(AngleUnit.DEGREES);
        roll = facing.getRoll(AngleUnit.DEGREES);



        oLeft = fl;
        oRight = fr;
        oCenter = br;


        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        mar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        oLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        oRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        oCenter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void start() {

        while (mar.getCurrentPosition() < 50) {
            mar.setPower(0.5);
            mar.setTargetPosition(100);
            mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // ^ gets arm off ground.
        }

        // they are swapped
        // right + is forward
        // left - is forward


        oLeft_target = -2100;
        oRight_target = 2100;
        goToForward_or_Backward();
        oCenter_target = 90;
        goToTurn();

        //gotToStrafe();





    }

    @Override
    public void loop() {
        // read and display the yaw pitch and roll angles

        telemetry.addData("yaw", yaw);
        telemetry.addData("pitch", pitch);
        telemetry.addData("roll", roll);


        mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("mar pos", mar.getCurrentPosition());

        telemetry.addData("left odom pos", oLeft.getCurrentPosition());
        telemetry.addData("right odom pos", oRight.getCurrentPosition());
        telemetry.addData("center odom pos", oCenter.getCurrentPosition());
        telemetry.addData("imu", yaw);
        telemetry.update();
    }

    public void goToForward_or_Backward() {
        if (oRight_target > oRight.getCurrentPosition()  && oLeft_target < oLeft.getCurrentPosition()) {
            // must go forward
            while (oRight_target > oRight.getCurrentPosition() && oLeft_target < oLeft.getCurrentPosition()) {
                mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                fl.setPower(+powerSet);
                bl.setPower(+powerSet);
                fr.setPower(+powerSet);
                br.setPower(+powerSet);

                telemetry.addData("mar pos", mar.getCurrentPosition());

                telemetry.addData("left odom pos", oLeft.getCurrentPosition());
                telemetry.addData("right odom pos", oRight.getCurrentPosition());
                telemetry.addData("center odom pos", oCenter.getCurrentPosition());
                telemetry.addData("imu", yaw);
                telemetry.update();
            }
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
        }

        else if (oRight_target < oRight.getCurrentPosition()  && oLeft_target > oLeft.getCurrentPosition()) {
            // must go backward
            while (oRight_target < oRight.getCurrentPosition()  && oLeft_target > oLeft.getCurrentPosition()) {
                mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                fl.setPower(-powerSet);
                bl.setPower(-powerSet);
                fr.setPower(-powerSet);
                br.setPower(-powerSet);

                telemetry.addData("mar pos", mar.getCurrentPosition());

                telemetry.addData("left odom pos", oLeft.getCurrentPosition());
                telemetry.addData("right odom pos", oRight.getCurrentPosition());
                telemetry.addData("center odom pos", oCenter.getCurrentPosition());
                telemetry.addData("imu", yaw);

                telemetry.update();
            }
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
        }

    }

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

    public void goToTurn() {
        if (oCenter_target < oCenter.getCurrentPosition()) {
            while (oCenter_target < oCenter.getCurrentPosition()) {
                turnLeft(0.25);
            }
        }
        else if (oCenter_target < oCenter.getCurrentPosition()) {
            while (oCenter_target < oCenter.getCurrentPosition()) {
                turnRight(0.25);
            }
        }
    }
}

