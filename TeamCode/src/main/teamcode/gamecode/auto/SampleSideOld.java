package org.firstinspires.ftc.teamcode.gamecode.auto;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous
@Disabled
public class SampleSideOld extends OpMode {
    DcMotor oLeft;
    DcMotor oRight;
    DcMotor oCenter;
    IMU imu;
    boolean turnLoop = true;
    YawPitchRollAngles facing;
    int yaw;
    double pitch;
    double roll;
    Servo wrist;
    Servo mouth;
    DistanceSensor ds;

    DcMotor fr,br,fl,bl;
    DcMotor mar, extend;
    double powerSet = 0.5;
    double oLeft_target;
    double oRight_target;
    double oCenter_target;

    double mouthAdd = 0;
    boolean yPressed = false;
    boolean aPressed = false;

    @Override
    public void init_loop() {
        telemetry.addData("inches ds", ds.getDistance(DistanceUnit.INCH));
        telemetry.update();
    }

    @Override
    public void init() {


        /*
         * according to the schematic:
         * port 0 = front right
         * port 1 = back right
         * port 2 = front left
         * port 3 = back left
         * */



        // drive motors:
        fr = (DcMotor) hardwareMap.get("c0");
        br = (DcMotor) hardwareMap.get("c1");
        fl = (DcMotor) hardwareMap.get("c2");
        bl = (DcMotor) hardwareMap.get("c3");

        ds = (DistanceSensor) hardwareMap.get("ds");


        mar = (DcMotor) hardwareMap.get("mar");
        extend = (DcMotor) hardwareMap.get("ext");

        mouth = (Servo) hardwareMap.get("mouth");
        wrist = (Servo) hardwareMap.get("wrist");

        imu = (IMU) hardwareMap.get("imu");
        imu.resetYaw();





        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        mar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);





        /*fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);*/

        oLeft = fl;
        oRight = fr;
        oCenter = br;

        oLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        oRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        oCenter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /* fl y fr encoders run using motors, por que el motors are connected to the oLeft and oRight odometers
        * This insures that the program will be able to move the robot forward using encoders y the command setTarget
        * position while only operation of the dos wheels.*/

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //oLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //oRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //oCenter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);




        telemetry.addData("mar pos", mar.getCurrentPosition());
    }

    @Override
    public void start() {

        /*fl.setPower(0.4);
        bl.setPower(0.4);
        fr.setPower(0.4);
        br.setPower(0.4);*/

        mouth.setPosition(0); // close claw

        while (mar.getCurrentPosition() < 100) {
            mar.setPower(0.5);
            mar.setTargetPosition(250);
            mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // ^ gets arm off ground.


            telemetry.addData("yaw", yaw);
            telemetry.addData("pitch", pitch);
            telemetry.addData("roll", roll);


            mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            telemetry.addData("mar pos", mar.getCurrentPosition());
            telemetry.addData("distance: ", ds.getDistance(DistanceUnit.INCH));

            telemetry.addData("left odom pos", oLeft.getCurrentPosition());
            telemetry.addData("right odom pos", oRight.getCurrentPosition());
            //telemetry.addData("center odom pos", oCenter.getCurrentPosition());
            telemetry.addData("imu", yaw);
            telemetry.update();
        }

        sleep(5000);
        mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // mar: 178
        // left o: 0
        // right o: 0
        // center o: 0
        // mouth: 0
        // wrist: 0
        // extend: 0
        // imu: 0


        // __________________________


        // left & right o: -1133

        /*oLeft_target = -1133;
        oRight_target = 1133;
        goToForward_or_Backward();*/

        oLeft_target = 1133; // target position for going forward


        /* Here is the idea...
        * por que fl = oLeft y fr = oRight
        * this can allow the robot to move forward only using
        * the two front wheels. Although this is not necessary an ideal
        * situation, it is a fine temporary solution. For competition is
        * very close to the present. I am convinced I attempted this
        * project at one point in the close past. I remember it not functioning
        * properly. I believe the past misfortune can be fixed my powering up the
        * dos back wheels until the desired position has been acquired.
        * */

        sleep(3000);
        fl.setTargetPosition((int) oLeft_target);
        fr.setTargetPosition((int) oLeft_target);

        fl.setPower(0.3);
        fr.setPower(0.3);
        br.setPower(0.3);
        bl.setPower(0.3);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /* por que it es un negative number (- es forward). That es why el > es used. */
        /*while (fl.getTargetPosition() > oLeft_target) {}*/
        /* nothing es in el while loop por que when el while loop is going the power is still set to the
        * previous 0.5 power. If el loop es done then the power of all el motors will be set to zero.*/

        sleep(3000);
        fl.setPower(0);
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);






        /*boolean loop = true;
// ds.getDistance(DistanceUnit.INCH) > 10
        while (loop) { // it does make it to the bar in the correct position.!!!!
            if (ds.getDistance(DistanceUnit.INCH) > 10) {
                // forward if greater
                fl.setPower(0.4);
                bl.setPower(0.4);
                fr.setPower(0.4);
                br.setPower(0.4);
            }
            else {
                loop = false;
            }

            telemetry.addData("distance: ", ds.getDistance(DistanceUnit.INCH));

        }*/

        fl.setPower(0);
        bl.setPower(0);
        fr.setPower(0);
        br.setPower(0);

        sleep(5000);


        fl.setPower(0);
        bl.setPower(0);
        fr.setPower(0);
        br.setPower(0);

        sleep(10000);

        // ____________________


        // mar: 1,000

        mar.setTargetPosition(1000);
        mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mar.setPower(0.5);


        sleep(5000);
        mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // _____________

        // extend: 1,000: extend more than normal this way if I don't go far enough then it will still work.
        // if I go far it is okay.

        extend.setTargetPosition(1200);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(0.5);

        sleep(5000);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // _______________
        sleep(5000);

        // claw wrist: 0.4
        wrist.setPosition(0.4);

        sleep(2000);



        // ________________

        // extend: 750

        extend.setTargetPosition(750);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(0.5);

        sleep(5000);

        mouth.setPosition(0.3); // open claw

        sleep(2000);

        // they are swapped
        // right + is forward
        // left - is forward





    }

    @Override
    public void loop() {
        // read and display the yaw pitch and roll angles

        telemetry.addData("yaw", yaw);
        telemetry.addData("pitch", pitch);
        telemetry.addData("roll", roll);
        telemetry.addData("distance: ", ds.getDistance(DistanceUnit.INCH));


        mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("mar pos", mar.getCurrentPosition());

        telemetry.addData("left odom pos", oLeft.getCurrentPosition());
        telemetry.addData("right odom pos", oRight.getCurrentPosition());
        telemetry.addData("center odom pos", oCenter.getCurrentPosition());
        telemetry.addData("imu", yaw);
        telemetry.update();
    }

    public void goToForward_or_Backward() {
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
            fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

