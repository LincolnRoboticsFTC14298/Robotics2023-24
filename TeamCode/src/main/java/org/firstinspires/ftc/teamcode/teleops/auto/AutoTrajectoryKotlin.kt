package org.firstinspires.ftc.teamcode.teleops.auto

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.subsystems.localization.StartingPose
import org.firstinspires.ftc.teamcode.subsystems.localization.StartingPoseStorage

@Autonomous
class AutoTrajectoryKotlin : LinearOpMode() {
    override fun runOpMode() {
        val drive = MecanumDrive(hardwareMap, StartingPoseStorage.startingPose.pose)

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

        when (StartingPoseStorage.startingPose) {
            StartingPose.BLUE_RIGHT -> runBlocking(blueRight)
            StartingPose.BLUE_LEFT -> runBlocking(blueLeft)
            StartingPose.RED_RIGHT -> runBlocking(redRight)
            StartingPose.RED_LEFT -> runBlocking(redLeft)
        }
    }
}