package org.firstinspires.ftc.teamcode.teleops.auto;

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
        Pose2d blueRightStartingPose = new Pose2d(-45, -36, 0);
        Pose2d blueLeftStartingPose = new Pose2d(-45, 12, 0);
        Pose2d redLeftStartingPose = new Pose2d(45, -36, 180);
        Pose2d redRightStartingPose = new Pose2d(45, 12, 180);
        MecanumDrive drive = new MecanumDrive(hardwareMap, blueRightStartingPose);

        Action blueRight = drive.actionBuilder(blueRightStartingPose)
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-60, 12, -90), 90)
                .splineToLinearHeading(new Pose2d(-36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(-60, 48), 90)
                .splineToConstantHeading(new Vector2d(-60, 60), 90)
                .build();

        Action blueLeft = drive.actionBuilder(blueLeftStartingPose)
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(-60, 48), 90)
                .splineToConstantHeading(new Vector2d(-60, 60), 90)
                .build();

        Action redLeft = drive.actionBuilder(redLeftStartingPose)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(60, 12, -90), 90)
                .splineToLinearHeading(new Pose2d(36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(60, 48), 90)
                .splineToConstantHeading(new Vector2d(60, 60), 90)
                .build();

        Action redRight = drive.actionBuilder(redRightStartingPose)
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(60, 48), 90)
                .splineToConstantHeading(new Vector2d(60, 60), 90)
                .build();

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(blueRight);
//        Actions.runBlocking(blueLeft);
//        Actions.runBlocking(redLeft);
//        Actions.runBlocking(redRight);
    }
}