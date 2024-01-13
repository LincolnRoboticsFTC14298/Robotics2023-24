package org.firstinspires.ftc.teamcode.teleops.testing;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Vision.Companion;
import org.firstinspires.ftc.teamcode.vision.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.apriltag.AprilTagPose;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import java.util.ArrayList;

@TeleOp
public class VisionSubsystemTestingOpMode extends OpMode {
    Vision visionSubsystem;

    @Override
    public void init() {

        visionSubsystem = new Vision(hardwareMap, Vision.FrontPipeline.APRIL_TAG, telemetry);


    }

    @SuppressLint("DefaultLocale")
    @Override
    public void loop() {
        // Calling getDetectionsUpdate() will only return an object if there was a new frame
        // processed since the last time we called it. Otherwise, it will return null. This
        // enables us to only run logic when there has been a new frame, as opposed to the
        // getLatestDetections() method which will always return an object

        AprilTagPose position = visionSubsystem.getLeftAprilTag(false);


        // If there's been a new frame...
        if (position != null) {
            telemetry.addData("Position x", position.x);
            telemetry.addData("Position y", position.y);
            telemetry.addData("Position z", position.z);
            telemetry.update();
        }
    }
}
