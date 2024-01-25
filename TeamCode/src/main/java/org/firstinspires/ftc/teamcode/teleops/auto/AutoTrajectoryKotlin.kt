package org.firstinspires.ftc.teamcode.teleops.auto

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class AutoTrajectoryKotlin : LinearOpMode() {
    enum class StartingPose(val pose: Pose2d) {
        BLUE_RIGHT(Pose2d(-63.0, -36.0, Math.toRadians(-180.0))),
        BLUE_LEFT(Pose2d(-63.0, 12.0, Math.toRadians(-180.0))),
        RED_LEFT(Pose2d(63.0, -36.0, Math.toRadians(0.0))),
        RED_RIGHT(Pose2d(63.0, 12.0, Math.toRadians(0.0)))
    }

    override fun runOpMode() {
        val drive = MecanumDrive(hardwareMap, StartingPose.BLUE_LEFT.pose)

        val blueRight = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(-90.0))
                .splineToConstantHeading(Vector2d(-57.0, -42.0), Math.toRadians(0.0))
                .splineToLinearHeading(Pose2d(-45.0, -36.0, Math.toRadians(0.0)), Math.toRadians(180.0))
                .splineToLinearHeading(Pose2d(-60.0, 12.0, Math.toRadians(-90.0)), Math.toRadians(90.0))
                .splineToLinearHeading(Pose2d(-36.0, 36.0, Math.toRadians(-90.0)), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 48.0), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 60.0), Math.toRadians(90.0))
                .build()

        val blueLeft = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(-57.0, 18.0), Math.toRadians(0.0))
                .splineToLinearHeading(Pose2d(-45.0, 12.0, 0.0), Math.toRadians(-15.0))
                .splineToLinearHeading(Pose2d(-36.0, 36.0, Math.toRadians(-90.0)), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 48.0), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(-63.0, 60.0), Math.toRadians(90.0))
                .build()

        val redLeft = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(-90.0))
                .splineToConstantHeading(Vector2d(57.0, -42.0), Math.toRadians(180.0))
                .splineToLinearHeading(Pose2d(45.0, -36.0, Math.toRadians(180.0)), Math.toRadians(0.0))
                .splineToLinearHeading(Pose2d(60.0, 12.0, Math.toRadians(-90.0)), Math.toRadians(90.0))
                .splineToLinearHeading(Pose2d(36.0, 36.0, Math.toRadians(-90.0)), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 48.0), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 60.0), Math.toRadians(90.0))
                .build()

        val redRight = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(57.0, 18.0), Math.toRadians(180.0))
                .splineToLinearHeading(Pose2d(45.0, 12.0, 180.0), Math.toRadians(165.0))
                .splineToLinearHeading(Pose2d(36.0, 36.0, Math.toRadians(90.0)), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 48.0), Math.toRadians(90.0))
                .splineToConstantHeading(Vector2d(63.0, 60.0), Math.toRadians(90.0))
                .build()

        waitForStart()

        if (isStopRequested) return

        runBlocking(blueRight)
//        runBlocking(blueLeft)
//        runBlocking(redLeft)
//        runBlocking(redRight)
    }
}