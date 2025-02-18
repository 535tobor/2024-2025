package org.firstinspires.ftc.teamcode.gamecode.auto;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous
public class robot_face_LEFT_bucket extends OpMode {
    IMU imu;
    int yaw;
    double pitch;
    double roll;
    Servo wrist;
    Servo mouth;
    TouchSensor button;
    //DistanceSensor ds;

    DcMotorEx fr,br,fl,bl;
    DcMotor mar, extend;
    double powerSet = 0.5;
    /* This program will use the target positioning with the motors
    * built in motor encoders.*/

    @Override
    public void init_loop() {
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

        telemetry.addData("WAIT!","Line the robot facing Left!");


        // drive motors:
        fr = (DcMotorEx) hardwareMap.get("c0");
        br = (DcMotorEx) hardwareMap.get("c1");
        fl = (DcMotorEx) hardwareMap.get("c2");
        bl = (DcMotorEx) hardwareMap.get("c3");



        mar = (DcMotor) hardwareMap.get("mar");
        extend = (DcMotor) hardwareMap.get("ext");

        mouth = (Servo) hardwareMap.get("mouth");
        wrist = (Servo) hardwareMap.get("wrist");

        imu = (IMU) hardwareMap.get("imu");
        imu.resetYaw();

        button = (TouchSensor) hardwareMap.get("button");
        // button for arm coming down





        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        mar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);




        // built in motor encoders
        fl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        telemetry.addData("mar pos", mar.getCurrentPosition());
        telemetryGive();
    }

    @Override
    public void start() {

        while (!button.isPressed()) {
            mar.setPower(-0.15); // slowly go down until it hits the button
            if (button.isPressed()) {
                mar.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
        }


        mouth.setPosition(0.25); // close claw

        telemetryGive();

        sleep(100);
        mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // ^ hold arm in position


        // mar: 178
        // left o: 0
        // right o: 0
        // center o: 0
        // mouth: 0
        // wrist: 0
        // extend: 0
        // imu: 0


        // __________________________

        telemetryGive();
        // left & right o: -1133

        setPower(+0.3,-0.3,-0.3,+0.3);
        setTarget(+150,-150,-150,+150);
        goToTarget();



        sleep(3000);

        setPower(0.3,0.3,0.3,0.3);
        setTarget(2500,2500,2500,2500);
        goToTarget();


        sleep(2000);

        fl.setPower(0);
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);

        telemetryGive();

        // ____________________


        // mar: 1,000

        mar.setTargetPosition(802);
        mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mar.setPower(0.5);


        sleep(1000);
        mar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // _____________

        // extend: 1,000

        extend.setTargetPosition(-1300);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(0.5);

        sleep(2000);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // _______________
        sleep(500);

        // claw wrist: 0.4
        //wrist.setPosition(0.4);

        //sleep(2000);



        // ________________

        // extend: 750



        mouth.setPosition(0); // open claw

        sleep(2000);

        extend.setTargetPosition(0);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(0.2);

        sleep(800);

        // they are swapped
        // right + is forward
        // left - is forward

        setPower(-0.3,-0.3,-0.3,-0.3);
        setTarget(-2500,-2500,-2500,-2500);
        goToTarget();


        sleep(2000);

        mar.setTargetPosition(202);
        mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mar.setPower(0.3);

        sleep(100);

        mar.setTargetPosition(0);
        mar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mar.setPower(0.2);





    }

    @Override
    public void loop() {
        // read and display the yaw pitch and roll angles

        telemetryGive();
    }






    public void telemetryGive() {
        telemetry.addData("mar pos", mar.getCurrentPosition());
        telemetry.addData("claw wrist pos", wrist.getPosition());
        telemetry.addData("claw mouth pos", mouth.getPosition());
        telemetry.addData("extend pos", extend.getCurrentPosition());

        telemetry.addData("fl pos", fl.getCurrentPosition());
        telemetry.addData("fr pos", fr.getCurrentPosition());
        telemetry.addData("bl pos", bl.getCurrentPosition());
        telemetry.addData("br pos", br.getCurrentPosition());

        telemetry.addData("imu", yaw);

        telemetry.update();
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

