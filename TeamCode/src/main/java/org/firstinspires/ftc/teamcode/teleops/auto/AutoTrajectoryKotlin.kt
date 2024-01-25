package org.firstinspires.ftc.teamcode.teleops.auto

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import java.lang.Math.toRadians

@Autonomous
class AutoTrajectoryKotlin : LinearOpMode() {
    override fun runOpMode() {
        val blueRightStartingPose = Pose2d(-63.0, -36.0, toRadians(-180.0))
        val blueLeftStartingPose = Pose2d(-63.0, 12.0, toRadians(-180.0))
        val redLeftStartingPose = Pose2d(63.0, -36.0, toRadians(0.0))
        val redRightStartingPose = Pose2d(63.0, 12.0, toRadians(0.0))

        val drive = MecanumDrive(hardwareMap, blueRightStartingPose)

        val blueRight = drive.actionBuilder(blueRightStartingPose)
                .setTangent(toRadians(-90.0))
                .splineToConstantHeading(Vector2d(-57.0, -42.0), toRadians(0.0))
                .splineToLinearHeading(Pose2d(-45.0, -36.0, toRadians(0.0)), toRadians(180.0))
                .splineToLinearHeading(Pose2d(-60.0, 12.0, toRadians(-90.0)), toRadians(90.0))
                .splineToLinearHeading(Pose2d(-36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 60.0), toRadians(90.0))
                .build()

        val blueLeft = drive.actionBuilder(blueLeftStartingPose)
                .setTangent(toRadians(-90.0))
                .splineToConstantHeading(Vector2d(-57.0, 18.0), toRadians(0.0))
                .splineToLinearHeading(Pose2d(-45.0, 12.0, 0.0), toRadians(-15.0))
                .splineToLinearHeading(Pose2d(-36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 60.0), toRadians(90.0))
                .build()

        val redLeft = drive.actionBuilder(redLeftStartingPose)
                .setTangent(toRadians(-90.0))
                .splineToConstantHeading(Vector2d(57.0, -42.0), toRadians(180.0))
                .splineToLinearHeading(Pose2d(45.0, -36.0, toRadians(180.0)), toRadians(0.0))
                .splineToLinearHeading(Pose2d(60.0, 12.0, toRadians(-90.0)), toRadians(90.0))
                .splineToLinearHeading(Pose2d(36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 60.0), toRadians(90.0))
                .build()

        val redRight = drive.actionBuilder(redRightStartingPose)
                .setTangent(toRadians(-90.0))
                .splineToConstantHeading(Vector2d(57.0, 18.0), toRadians(180.0))
                .splineToLinearHeading(Pose2d(45.0, 12.0, 180.0), toRadians(165.0))
                .splineToLinearHeading(Pose2d(36.0, 36.0, toRadians(90.0)), toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 60.0), toRadians(90.0))
                .build()

        waitForStart()

        if (isStopRequested) return

        runBlocking(blueRight)
//        runBlocking(blueLeft)
//        runBlocking(redLeft)
//        runBlocking(redRight)
    }
}