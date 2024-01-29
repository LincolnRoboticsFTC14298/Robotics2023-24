package org.firstinspires.ftc.teamcode.teleops.auto

import java.lang.Math.toRadians

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.subsystems.Vision
import org.firstinspires.ftc.teamcode.subsystems.localization.StartingPose
import org.firstinspires.ftc.teamcode.subsystems.localization.StartingPoseStorage


@Autonomous
class MainAuto : LinearOpMode() {
    override fun runOpMode() {
        val drive = MecanumDrive(hardwareMap, StartingPoseStorage.startingPose.pose)
        val vision = Vision(hardwareMap, telemetry = telemetry)


        // Trajectories
        val blueRight = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-57.0, -42.0), toRadians(0.0))
            .splineToLinearHeading(Pose2d(-45.0, -36.0, toRadians(0.0)), toRadians(180.0))
            .splineToLinearHeading(Pose2d(-60.0, -48.0, toRadians(-90.0)), toRadians(-90.0))
            .splineToLinearHeading(Pose2d(-60.0, 12.0, toRadians(-90.0)), toRadians(90.0))
            .splineToLinearHeading(Pose2d(-36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 60.0), toRadians(90.0))
            .build()

        val blueLeft = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(-54.0, 24.0), toRadians(45.0))
            .splineToLinearHeading(Pose2d(-54.0, 18.0, toRadians(0.0)), toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-45.0, 12.0), toRadians(90.0))
            .splineToLinearHeading(Pose2d(-54.0, 36.0, toRadians(-90.0)), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-36.0, 36.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 60.0), toRadians(90.0))
            .build()

        val redLeft = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(-90.0))
            .splineToConstantHeading(Vector2d(57.0, -42.0), toRadians(180.0))
            .splineToLinearHeading(Pose2d(45.0, -36.0, toRadians(180.0)), toRadians(0.0))
            .splineToLinearHeading(Pose2d(60.0, -48.0, toRadians(-90.0)), toRadians(-90.0))
            .splineToLinearHeading(Pose2d(60.0, 12.0, toRadians(-90.0)), toRadians(90.0))
            .splineToLinearHeading(Pose2d(36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 60.0), toRadians(90.0))
            .build()

        val redRight = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(54.0, 24.0), toRadians(135.0))
            .splineToLinearHeading(Pose2d(54.0, 18.0, toRadians(180.0)), toRadians(-90.0))
            .splineToConstantHeading(Vector2d(45.0, 12.0), toRadians(90.0))
            .splineToLinearHeading(Pose2d(54.0, 36.0, toRadians(-90.0)), toRadians(90.0))
            .splineToConstantHeading(Vector2d(36.0, 36.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 60.0), toRadians(90.0))
            .build()

        waitForStart()

        if (isStopRequested) return

        //Start streaming camera
        vision.startStreamingFrontCamera()

        // Initialize variable to store spike position reads
        val voteCount = mutableMapOf<Vision.SpikeDirection, Int>()

        voteCount[Vision.SpikeDirection.LEFT] = 0
        voteCount[Vision.SpikeDirection.CENTER] = 0
        voteCount[Vision.SpikeDirection.RIGHT] = 0

        // Store each spike position read
        val maxReads = 15
        while (voteCount.size < maxReads) {
            val spikeDirectionUpdate = vision.getSpikeMarkDirectionUpdate() ?: continue
            voteCount[spikeDirectionUpdate] = voteCount[spikeDirectionUpdate]!! + 1
        }

        // Set spikePosition to the highest voted position
        val spikePosition = voteCount.entries.maxByOrNull {it.value}?.key ?: Vision.SpikeDirection.CENTER

        // Stop streaming
        vision.stopStreamingFrontCamera()


        // Trajectory Decision Tree
        when (StartingPoseStorage.startingPose) {
            StartingPose.BLUE_RIGHT -> {
                runBlocking(blueRight) // Old all-in-one trajectory


                /*
                runBlocking(blueRight1) // Drives to pixel placement branch

                when (spikePosition) { // Drives to 1 of 3 pixel placement spots depending on vision data
                    Vision.SpikeDirection.LEFT -> runBlocking(blueRightPixelLeft) //TODO make and add pixel placement command
                    Vision.SpikeDirection.CENTER -> runBlocking(blueRightPixelCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueRightPixelRight)
                }

                runBlocking(drive.actionBuilder(drive.pose) // Drives to next node
                    .splineToLinearHeading()
                    .build()
                )

                runBlocking(blueRight2) // Continues rest of path up to the backdrop

                when (spikePosition) { // Drives to 1 of 3 backdrop placement spots depending on vision data
                    Vision.SpikeDirection.LEFT -> runBlocking(blueRightBackdropLeft) //TODO make and add pixel placement command, make vision auto align code
                    Vision.SpikeDirection.CENTER -> runBlocking(blueRightBackdropCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueRightBackdropRight)
                }
                 */
            }

            StartingPose.BLUE_LEFT -> {
                runBlocking(blueLeft)
            }

            StartingPose.RED_RIGHT -> {
                runBlocking(redRight)
            }

            StartingPose.RED_LEFT -> {
                runBlocking(redLeft)
            }
        }
    }
}