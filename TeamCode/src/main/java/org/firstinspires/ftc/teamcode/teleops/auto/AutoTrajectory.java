package org.firstinspires.ftc.teamcode.teleops.auto;

import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoTrajectory extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d blueRightStartingPose = new Pose2d(-63, -36, toRadians(-180));
        Pose2d blueLeftStartingPose = new Pose2d(-63, 12, toRadians(-180));
        Pose2d redLeftStartingPose = new Pose2d(63, -36, toRadians(0));
        Pose2d redRightStartingPose = new Pose2d(63, 12, toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, blueRightStartingPose);

        Action blueRight = drive.actionBuilder(blueRightStartingPose)
                .setTangent(toRadians(-90))
                .splineToConstantHeading(new Vector2d(-57, -42), toRadians(0))
                .splineToLinearHeading(new Pose2d(-45, -36, toRadians(0)), toRadians(180))
                .splineToLinearHeading(new Pose2d(-60, 12, toRadians(-90)), toRadians(90))
                .splineToLinearHeading(new Pose2d(-36, 36, toRadians(-90)), toRadians(90))
                .splineToConstantHeading(new Vector2d(-63, 48), toRadians(90))
                .splineToConstantHeading(new Vector2d(-63, 60), toRadians(90))
                .build();

        Action blueLeft = drive.actionBuilder(blueLeftStartingPose)
                .setTangent(toRadians(-90))
                .splineToConstantHeading(new Vector2d(-57, 18), toRadians(0))
                .splineToLinearHeading(new Pose2d(-45, 12, 0), toRadians(-15))
                .splineToLinearHeading(new Pose2d(-36, 36, toRadians(-90)), toRadians(90))
                .splineToConstantHeading(new Vector2d(-63, 48), toRadians(90))
                .splineToConstantHeading(new Vector2d(-63, 60), toRadians(90))
                .build();

        Action redLeft = drive.actionBuilder(redLeftStartingPose)
                .setTangent(toRadians(-90))
                .splineToConstantHeading(new Vector2d(57, -42), toRadians(180))
                .splineToLinearHeading(new Pose2d(45, -36, toRadians(180)), toRadians(0))
                .splineToLinearHeading(new Pose2d(60, 12, toRadians(-90)), toRadians(90))
                .splineToLinearHeading(new Pose2d(36, 36, toRadians(-90)), toRadians(90))
                .splineToConstantHeading(new Vector2d(63, 48), toRadians(90))
                .splineToConstantHeading(new Vector2d(63, 60), toRadians(90))
                .build();

        Action redRight = drive.actionBuilder(redRightStartingPose)
                .setTangent(toRadians(90))
                .splineToConstantHeading(new Vector2d(57, 18), toRadians(180))
                .splineToLinearHeading(new Pose2d(45, 12, 180), toRadians(165))
                .splineToLinearHeading(new Pose2d(36, 36, toRadians(90)), toRadians(90))
                .splineToConstantHeading(new Vector2d(63, 48), toRadians(90))
                .splineToConstantHeading(new Vector2d(63, 60), toRadians(90))
                .build();

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(blueRight);
//        Actions.runBlocking(blueLeft);
//        Actions.runBlocking(redLeft);
//        Actions.runBlocking(redRight);
    }
}