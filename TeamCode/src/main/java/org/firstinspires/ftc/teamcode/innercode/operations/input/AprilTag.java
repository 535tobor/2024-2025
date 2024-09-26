package org.firstinspires.ftc.teamcode.innercode.operations.input;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.innercode.enums.DsCaptions;
import org.firstinspires.ftc.teamcode.innercode.enums.StringNames;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class AprilTag {
    public static VisionPortal visionPortal;
    public static double firstTagId, tagId;
    public static AprilTagDetection firstTagFound;
    public static double tagsFound;
    public static String tagName;
    public static boolean cameraConnected;

    public static List tagPosition;

    public static AprilTagProcessor aprilTag;

    public void initAprilTag(HardwareMap mapHardware, String CameraName, Telemetry telemetry) {

        try {
            // Create the AprilTag processor
            aprilTag = AprilTagProcessor.easyCreateWithDefaults();

            visionPortal = VisionPortal.easyCreateWithDefaults(
                    mapHardware.get(WebcamName.class, CameraName), aprilTag);
            cameraConnected = true;
        }
        catch (Exception e) {
            telemetry.addData(DsCaptions.arrow.display(), "Camera is not connected.");
            cameraConnected = false;
        }
    }


    // setAprilTagVariables is used for setting up the april tag
    // to use if statement for if desired tag is found use the tagName variable bellow
    public void setAprilTagVariables() {
        // add AprilTag found information to set variables

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        tagsFound = currentDetections.size();

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                // find all information for the april tag

                tagName = detection.metadata.name;
                tagPosition = new ArrayList();
                tagPosition.add(detection.ftcPose.x);
                tagPosition.add(detection.ftcPose.y);
                tagPosition.add(detection.ftcPose.z);
                tagPosition.add(detection.ftcPose.pitch);
                tagPosition.add(detection.ftcPose.roll);
                tagPosition.add(detection.ftcPose.yaw);
                tagPosition.add(detection.ftcPose.range);
                tagPosition.add(detection.ftcPose.bearing);
                tagPosition.add(detection.ftcPose.elevation);

            }
            else {

                tagName = StringNames.unknown.getString();
            }

            tagId = detection.id;
            // detection.id is the name of the april tag (the number value)

        }
    }

}