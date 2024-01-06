package org.firstinspires.ftc.teamcode.teleops.testing;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Vision.Companion;
import org.firstinspires.ftc.teamcode.vision.AprilTagDetectionPipeline;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import java.util.ArrayList;

@TeleOp
public class AprilTagTuningOpMode extends OpMode {
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    OpenCvCamera camera;
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry doubleTelemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

    Vision.Companion.CameraData cameraData;

    public double tagSize = 2;
    int numFramesWithoutDetection = 0;

    final float DECIMATION_HIGH = 3;
    final float DECIMATION_LOW = 2;
    final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
    final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

    @Override
    public void init() {
        cameraData = Companion.CameraData.LOGITECH_C920;
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(cameraData);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        camera.setPipeline(aprilTagDetectionPipeline);

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

        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();

        // If there's been a new frame...
        if(detections != null)
        {
            doubleTelemetry.addData("FPS", camera.getFps());
            doubleTelemetry.addData("Overhead ms", camera.getOverheadTimeMs());
            doubleTelemetry.addData("Pipeline ms", camera.getPipelineTimeMs());

            // If we don't see any tags
            if(detections.size() == 0)
            {
                numFramesWithoutDetection++;

                // If we haven't seen a tag for a few frames, lower the decimation
                // so we can hopefully pick one up if we're e.g. far back
                if(numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION)
                {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                }
            }
            // We do see tags!
            else
            {
                numFramesWithoutDetection = 0;

                // If the target is within 1 meter, turn on high decimation to
                // increase the frame rate
                if(detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS)
                {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                }

                for(AprilTagDetection detection : detections)
                {
                    Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

                    doubleTelemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
                    doubleTelemetry.addLine(String.format("Translation X: %.2f in", detection.pose.x));
                    doubleTelemetry.addLine(String.format("Translation Y: %.2f in", detection.pose.y));
                    doubleTelemetry.addLine(String.format("Translation Z: %.2f in", detection.pose.z));

                    doubleTelemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(rot.firstAngle)));
                    doubleTelemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(rot.secondAngle)));
                    doubleTelemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(rot.thirdAngle)));
                }
            }
            doubleTelemetry.update();
        }
    }
}
