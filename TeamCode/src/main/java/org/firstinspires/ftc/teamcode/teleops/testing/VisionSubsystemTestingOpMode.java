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
    OpenCvCamera camera;
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry doubleTelemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

    Companion.CameraData cameraData;


    @Override
    public void init() {
        cameraData = Companion.CameraData.LOGITECH_C920;
        visionSubsystem = new Vision(hardwareMap, Vision.FrontPipeline.APRIL_TAG, doubleTelemetry);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 360); //1920, 1080

            }

            @Override
            public void onError(int errorCode) {

            }
        });
        dashboard.startCameraStream(camera, 10.0);
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
        doubleTelemetry.addData("Position", position);
        doubleTelemetry.update();
    }
}
