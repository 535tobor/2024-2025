package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class Turn extends OpMode {
    IMU imu;
    YawPitchRollAngles facing;
    @Override
    public void init() {
        imu = (IMU) hardwareMap.get("imu");
    }

    @Override
    public void loop() {
        facing = imu.getRobotYawPitchRollAngles();
        double yaw = facing.getYaw(AngleUnit.DEGREES);

        telemetry.addData("",yaw);
    }
}
