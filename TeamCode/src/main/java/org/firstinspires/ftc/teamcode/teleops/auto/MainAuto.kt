package org.firstinspires.ftc.teamcode.teleops.auto

import android.util.Log
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
    private var spikePosition: Vision.SpikeDirection = Vision.SpikeDirection.CENTER

    override fun runOpMode() {
        val drive = MecanumDriveRR( //TODO check with normal drive subsystem
            hardwareMap,
            StartingPoseStorage.startingPose.pose
        )
        val vision = Vision(hardwareMap)

        // Trajectories

        //Blue Right - DONE
        val blueRightPartOne = drive.actionBuilder(drive.pose) 
            .setTangent(toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-57.0, -42.0), toRadians(0.0))
            .splineToLinearHeading(Pose2d(-45.0, -36.0, toRadians(0.0)), toRadians(180.0))
            .build()


        val blueRightPixelLeft = drive.actionBuilder(Pose2d(-45.0, -36.0, toRadians(0.0))) 
            .setTangent(toRadians(-90.0))
            .splineToLinearHeading(Pose2d(-45.0, -32.5, toRadians(30.0)), toRadians(-90.0))
            .build()

        val blueRightPixelCenter = drive.actionBuilder(Pose2d(-45.0, -36.0, toRadians(0.0))) 
            .setTangent(toRadians(0.0))
            .splineToLinearHeading(Pose2d(-42.0, -36.0, toRadians(0.0)), toRadians(0.0))
            .build()

        val blueRightPixelRight = drive.actionBuilder(Pose2d(-45.0, -36.0, toRadians(0.0))) 
            .setTangent(toRadians(90.0))
            .splineToLinearHeading(Pose2d(-45.0, -39.5, toRadians(-30.0)), toRadians(90.0))
            .build()


        val blueRightPartTwo = drive.actionBuilder(Pose2d(-60.0, -48.0, toRadians(-90.0))) 
            .setTangent(toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-60.0, 36.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-36.0, 36.0), toRadians(0.0))
            .build()


        val blueRightBackdropLeft = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0))) 
            .setTangent(toRadians(180.0))
            .splineToConstantHeading(Vector2d(-40.0, 40.0), toRadians(90.0))
            .build()

        val blueRightBackdropCenter = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(-36.0, 40.0), toRadians(90.0))
            .build()

        val blueRightBackdropRight = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0))) 
            .setTangent(toRadians(0.0))
            .splineToConstantHeading(Vector2d(-32.0, 40.0), toRadians(90.0))
            .build()


        val blueRightPartThree = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 60.0), toRadians(90.0))
            .build()


        //Blue Left
        val blueLeftPartOne = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(-54.0, 24.0), toRadians(45.0))
            .splineToLinearHeading(Pose2d(-54.0, 18.0, toRadians(0.0)), toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-45.0, 12.0), toRadians(90.0))
            .build()

            val blueLeftPixelLeft = drive.actionBuilder(Pose2d(-45.0, 12.0, toRadians(0.0)))
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(-45.0, 15.5, toRadians(-90.0)), toRadians(30.0))
                .build()

            val blueLeftPixelCenter = drive.actionBuilder(Pose2d(-45.0, 12.0, toRadians(0.0)))
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(-42.0, 12.0, toRadians(0.0)), toRadians(0.0))
                .build()

            val blueLeftPixelRight = drive.actionBuilder(Pose2d(-45.0, 12.0, toRadians(0.0)))
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(-45.0, 8.5, toRadians(90.0)), toRadians(-30.0))
                .build()

        val blueLeftPartTwo = drive.actionBuilder(Pose2d(-54.0, 36.0, toRadians(-90.0)))
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(-36.0, 36.0), toRadians(0.0))
            .build()


        val blueLeftBackdropLeft = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(180.0))
                .splineToConstantHeading(Vector2d(-40.0, 40.0), toRadians(90.0))
                .build()

        val blueLeftBackdropCenter = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))
                .splineToConstantHeading(Vector2d(-36.0, 40.0), toRadians(90.0))
                .build()

        val blueLeftBackdropRight = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(0.0))
                .splineToConstantHeading(Vector2d(-32.0, 40.0), toRadians(90.0))
                .build()


        val blueLeftPartThree = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
            .splineToConstantHeading(Vector2d(-60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 60.0), toRadians(90.0))
            .build()


        //Red Left
        val redLeftPartOne = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(-90.0))
            .splineToConstantHeading(Vector2d(57.0, -42.0), toRadians(180.0))
            .splineToLinearHeading(Pose2d(45.0, -36.0, toRadians(180.0)), toRadians(0.0))
            .build()

            val redLeftPixelLeft = drive.actionBuilder(drive.pose)
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(30.0, -39.5, toRadians(90.0)), toRadians(0.0))
                .build()

            val redLeftPixelCenter = drive.actionBuilder(drive.pose)
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(39.5, -36.0, toRadians(180.0)), toRadians(0.0))
                .build()

            val redLeftPixelRight = drive.actionBuilder(drive.pose)
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(30.0, -36.0, toRadians(-90.0)), toRadians(0.0))
                .splineToConstantHeading(Vector2d(30.0, -32.50), toRadians(-90.0))
                .build()

        val redLeftPartTwo = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(180.0))
            .splineToLinearHeading(Pose2d(48.0, -48.0, toRadians(-90.0)), toRadians(180.0))
            .splineToConstantHeading(Vector2d(60.0, -48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 12.0), toRadians(90.0))
            .splineToLinearHeading(Pose2d(36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
            .build()

        val redLeftPartThree = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 60.0), toRadians(90.0))
            .build()


        //Red Right
        val redRightPartOne = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(54.0, 24.0), toRadians(135.0))
            .splineToLinearHeading(Pose2d(54.0, 18.0, toRadians(180.0)), toRadians(-90.0))
            .splineToConstantHeading(Vector2d(45.0, 12.0), toRadians(90.0))
            .build()

            val redRightPixelLeft = drive.actionBuilder(drive.pose)
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(30.0, 15.5, toRadians(-90.0)), toRadians(180.0))
                .build()

            val redRightPixelCenter = drive.actionBuilder(drive.pose)
                .setTangent(toRadians(90.0))
                .splineToLinearHeading(Pose2d(39.5, 12.0, toRadians(180.0)), toRadians(180.0))
                .build()

            val redRightPixelRight = drive.actionBuilder(drive.pose)
                .setTangent(toRadians(180.0))
                .splineToLinearHeading(Pose2d(30.0, 12.0, toRadians(90.0)), toRadians(180.0))
                .splineToConstantHeading(Vector2d(30.0, 8.50), toRadians(0.0))
                .build()

        val redRightPartTwo = drive.actionBuilder(drive.pose)
            .splineToLinearHeading(Pose2d(54.0, 36.0, toRadians(-90.0)), toRadians(90.0))
            .splineToConstantHeading(Vector2d(36.0, 36.0), toRadians(90.0))
            .build()

        val redRightPartThree = drive.actionBuilder(drive.pose)
            .splineToConstantHeading(Vector2d(60.0, 48.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(60.0, 60.0), toRadians(90.0))
            .build()

        waitForStart()

        if (isStopRequested) return

        // Initialize variable to store spike position reads
        val voteCount = mutableMapOf<Vision.SpikeDirection, Int>()

        voteCount[Vision.SpikeDirection.LEFT] = 0
        voteCount[Vision.SpikeDirection.CENTER] = 0
        voteCount[Vision.SpikeDirection.RIGHT] = 0

        // Store each spike position read
        val maxReads = 7
        while (voteCount.values.sum() < maxReads) { //TODO make this a safe loop
            val spikeDirectionUpdate = vision.getSpikeMarkDirectionUpdate() ?: continue
            voteCount[spikeDirectionUpdate] = voteCount[spikeDirectionUpdate]!! + 1
            Log.i("Votes", voteCount.toString())
            telemetry.addData("Votes", voteCount.toString())
            telemetry.update()
        }

        // Set spikePosition to the highest voted position
        spikePosition = voteCount.entries.maxByOrNull {it.value}?.key ?: Vision.SpikeDirection.CENTER




        // Trajectory Decision Tree
        when (StartingPoseStorage.startingPose) {
            StartingPose.BLUE_RIGHT -> {
                runBlocking(blueRightPartOne) // Drives to pixel placement branch

                when (spikePosition) { // Drives to 1 of 3 pixel placement spots depending on vision data
                    Vision.SpikeDirection.LEFT -> runBlocking(blueRightPixelLeft) //TODO make and add pixel placement command
                    Vision.SpikeDirection.CENTER -> runBlocking(blueRightPixelCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueRightPixelRight)
                }

                runBlocking(drive.actionBuilder(drive.pose) // Drives to next node
                    .setTangent(toRadians(-135.0))
                    .splineToLinearHeading(Pose2d(-60.0, -48.0, toRadians(-90.0)), toRadians(-90.0))
                    .build()
                )

                runBlocking(blueRightPartTwo) // Continues rest of path up to the backdrop

                when (spikePosition) { // Drives to 1 of 3 backdrop placement spots depending on vision data
                    Vision.SpikeDirection.LEFT -> runBlocking(blueRightBackdropLeft) //TODO make and add pixel placement command, make vision auto align code(?)
                    Vision.SpikeDirection.CENTER -> runBlocking(blueRightBackdropCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueRightBackdropRight)
                }

                runBlocking(drive.actionBuilder(drive.pose) // Drives to next node
                    .setTangent(toRadians(90.0))
                    .splineToLinearHeading(Pose2d(-36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                    .build()
                )

                runBlocking(blueRightPartThree) // Finish path

            }

//            Should work???

            StartingPose.BLUE_LEFT -> {
                runBlocking(blueLeftPartOne) // Drives to pixel placement branch

                when (spikePosition) { // Drives to 1 of 3 pixel placement spots depending on vision data
                    Vision.SpikeDirection.LEFT -> runBlocking(blueLeftPixelLeft) //TODO make and add pixel placement command
                    Vision.SpikeDirection.CENTER -> runBlocking(blueLeftPixelCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueLeftPixelRight)
                }

                runBlocking(drive.actionBuilder(drive.pose) // Drives to next node
                        .setTangent(toRadians(135.0))
                        .splineToLinearHeading(Pose2d(-54.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                        .build()
                )
                //TODO
                runBlocking(blueLeftPartTwo) // Continues rest of path up to the backdrop

                when (spikePosition) { // Drives to 1 of 3 backdrop placement spots depending on vision data
                    Vision.SpikeDirection.LEFT -> runBlocking(blueLeftBackdropLeft) //TODO make and add pixel placement command, make vision auto align code(?)
                    Vision.SpikeDirection.CENTER -> runBlocking(blueLeftBackdropCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueLeftBackdropRight)
                }

                runBlocking(drive.actionBuilder(drive.pose) // Drives to next node
                    .setTangent(toRadians(90.0))
                    .splineToLinearHeading(Pose2d(-36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                    .build()
                )

                runBlocking(blueLeftPartThree) // Finish path

            }

            StartingPose.RED_RIGHT -> {
                runBlocking(redRightPartOne)
            }

            StartingPose.RED_LEFT -> {
                runBlocking(redLeftPartOne)
            }
        }
    }
}