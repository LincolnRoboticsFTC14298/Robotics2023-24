package org.firstinspires.ftc.teamcode.teleops.auto

import android.util.Log
import androidx.annotation.NonNull
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.InstantAction
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.SleepAction
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.subsystems.DualClaw
import org.firstinspires.ftc.teamcode.subsystems.Passthrough
import org.firstinspires.ftc.teamcode.subsystems.Vision
import org.firstinspires.ftc.teamcode.subsystems.actions.*
import org.firstinspires.ftc.teamcode.subsystems.localization.StartingPose
import org.firstinspires.ftc.teamcode.subsystems.localization.StartingPoseStorage
import java.lang.Math.toRadians


//TODO test and double check tangents (I'm pretty sure I screwed some of them up (polarity) also some them I found were screwed up)

@Autonomous
class MainAuto : LinearOpMode() {
    private var spikePosition: Vision.SpikeDirection = Vision.SpikeDirection.LEFT
    val autoTimer = ElapsedTime()

    override fun runOpMode() {
        val drive = MecanumDriveRR( //TODO check with normal drive subsystem
            hardwareMap,
            StartingPoseStorage.startingPose.pose
        )
        val vision = Vision(hardwareMap)
        val passthrough = Passthrough(hardwareMap)
        val claw = DualClaw(hardwareMap)

        autoTimer.reset()

        // Trajectories

        //Blue Right - DONE
        val blueRightPartOne = drive.actionBuilder(drive.pose) 
            .setTangent(toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-57.0, -42.0), toRadians(0.0))
            .splineToLinearHeading(Pose2d(-45.0, -36.0, toRadians(0.0)), toRadians(180.0))
            .build()


        val blueRightPixelLeft = drive.actionBuilder(Pose2d(-45.0, -36.0, toRadians(0.0))) 
            .setTangent(toRadians(-90.0))
            .splineToLinearHeading(Pose2d(-32.0, -39.5, toRadians(90.0)), toRadians(-90.0))
            .build()

        val blueRightPixelCenter = drive.actionBuilder(Pose2d(-45.0, -36.0, toRadians(0.0))) 
            .setTangent(toRadians(0.0))
            .splineToLinearHeading(Pose2d(-42.0, -36.0, toRadians(0.0)), toRadians(0.0))
            .build()

        val blueRightPixelRight = drive.actionBuilder(Pose2d(-45.0, -36.0, toRadians(0.0))) 
            .setTangent(toRadians(90.0))
            .splineToLinearHeading(Pose2d(-45.0, -40.5, toRadians(-30.0)), toRadians(90.0))
            .build()


        val blueRightPartTwo = drive.actionBuilder(Pose2d(-59.0, -48.0, toRadians(-90.0)))
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(-60.0, 36.0), toRadians(90.0))
            .splineToConstantHeading(Vector2d(-36.0, 36.0), toRadians(0.0))
            .build()


        val blueRightBackdropLeft = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0))) 
            .setTangent(toRadians(180.0))
            .splineToConstantHeading(Vector2d(-40.0, 40.0), toRadians(90.0))
            .build()

        val blueRightBackdropCenter = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
            .setTangent(toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-36.0, 40.0), toRadians(90.0))
            .build()

        val blueRightBackdropRight = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0))) 
            .setTangent(toRadians(0.0))
            .splineToConstantHeading(Vector2d(-32.0, 40.0), toRadians(90.0))
            .build()



        //Blue Left - DONE
        val blueLeftPartOne = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(-54.0, 24.0), toRadians(45.0))
            .splineToLinearHeading(Pose2d(-54.0, 18.0, toRadians(0.0)), toRadians(-90.0))
            .splineToConstantHeading(Vector2d(-45.0, 12.0), toRadians(90.0))
            .build()

            val blueLeftPixelLeft = drive.actionBuilder(Pose2d(-45.0, 12.0, toRadians(0.0)))
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(-29.0, 15.5, toRadians(90.0)), toRadians(30.0))
                .build()

            val blueLeftPixelCenter = drive.actionBuilder(Pose2d(-45.0, 12.0, toRadians(0.0)))
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(-39.0, 12.0, toRadians(0.0)), toRadians(0.0))
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
                .setTangent(toRadians(-90.0))
                .splineToConstantHeading(Vector2d(-36.0, 40.0), toRadians(90.0))
                .build()

        val blueLeftBackdropRight = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(0.0))
                .splineToConstantHeading(Vector2d(-32.0, 40.0), toRadians(90.0))
                .build()


        //Blue Part Three Left
        val bluePartThreeLeft = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))  //Idk if you need this
                .splineToConstantHeading(Vector2d(-60.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(-60.0, 60.0), toRadians(90.0))
                .build()


        //Blue Part Three Right
        val bluePartThreeRight = drive.actionBuilder(Pose2d(-36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))  //Idk if you need this
                .splineToConstantHeading(Vector2d(-12.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(-12.0, 60.0), toRadians(90.0))
                .build()


        //Red Left
        val redLeftPartOne = drive.actionBuilder(drive.pose)
                .setTangent(toRadians(-90.0))
                .splineToConstantHeading(Vector2d(57.0, -42.0), toRadians(180.0))
                .splineToLinearHeading(Pose2d(45.0, -36.0, toRadians(180.0)), toRadians(0.0))
                .build()


        val redLeftPixelLeft = drive.actionBuilder(Pose2d(45.0, -36.0, toRadians(180.0)))
                .setTangent(toRadians(-90.0))
                .splineToLinearHeading(Pose2d(45.0, -32.5, toRadians(150.0)), toRadians(-90.0))
                .build()

        val redLeftPixelCenter = drive.actionBuilder(Pose2d(45.0, -36.0, toRadians(180.0)))
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(42.0, -36.0, toRadians(0.0)), toRadians(0.0))
                .build()

        val redLeftPixelRight = drive.actionBuilder(Pose2d(45.0, -36.0, toRadians(180.0)))
                .setTangent(toRadians(90.0))
                .splineToLinearHeading(Pose2d(45.0, -39.5, toRadians(-150.0)), toRadians(-90.0))
                .build()


        val redLeftPartTwo = drive.actionBuilder(Pose2d(60.0, -48.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))
                .splineToConstantHeading(Vector2d(60.0, 36.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(36.0, 36.0), toRadians(180.0))
                .build()


        val redLeftBackdropLeft = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(90.0)))
                .setTangent(toRadians(180.0))
                .splineToConstantHeading(Vector2d(32.0, 40.0), toRadians(-90.0))
                .build()

        val redLeftBackdropCenter = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(90.0)))
                .setTangent(toRadians(-90.0))
                .splineToConstantHeading(Vector2d(36.0, 40.0), toRadians(-90.0))
                .build()

        val redLeftBackdropRight = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(90.0)))
                .setTangent(toRadians(0.0))
                .splineToConstantHeading(Vector2d(40.0, 40.0), toRadians(-90.0))
                .build()

        
        //Red Right
        val redRightPartOne = drive.actionBuilder(drive.pose)
            .setTangent(toRadians(90.0))
            .splineToConstantHeading(Vector2d(54.0, 24.0), toRadians(135.0))
            .splineToLinearHeading(Pose2d(54.0, 18.0, toRadians(180.0)), toRadians(-90.0))
            .splineToConstantHeading(Vector2d(45.0, 12.0), toRadians(90.0))
            .build()

        val redRightPixelLeft = drive.actionBuilder(Pose2d(45.0, 12.0, toRadians(-180.0)))
                .setTangent(toRadians(90.0))
                .splineToLinearHeading(Pose2d(45.0, 15.5, toRadians(150.0)), toRadians(90.0))
                .build()

        val redRightPixelCenter = drive.actionBuilder(Pose2d(45.0, 12.0, toRadians(-180.0)))
                .setTangent(toRadians(0.0))
                .splineToLinearHeading(Pose2d(42.0, 12.0, toRadians(0.0)), toRadians(0.0))
                .build()

        val redRightPixelRight = drive.actionBuilder(Pose2d(45.0, 12.0, toRadians(-180.0)))
                .setTangent(toRadians(-90.0))
                .splineToLinearHeading(Pose2d(45.0, 8.5, toRadians(-150.0)), toRadians(90.0))
                .build()

        val redRightPartTwo = drive.actionBuilder(Pose2d(54.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))
                .splineToConstantHeading(Vector2d(36.0, 36.0), toRadians(90.0))
                .build()


        val redRightBackdropLeft = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(180.0))
                .splineToConstantHeading(Vector2d(32.0, 40.0), toRadians(90.0))
                .build()

        val redRightBackdropCenter = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))
                .splineToConstantHeading(Vector2d(36.0, 40.0), toRadians(90.0))
                .build()

        val redRightBackdropRight = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(180.0))
                .splineToConstantHeading(Vector2d(32.0, 40.0), toRadians(90.0))
                .build()


        //Red Part Three Left
        val redPartThreeLeft = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))  //Idk if you need this
                .splineToConstantHeading(Vector2d(60.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(60.0, 60.0), toRadians(90.0))
                .build()



        //Red Part Three Right
        val redPartThreeRight = drive.actionBuilder(Pose2d(36.0, 36.0, toRadians(-90.0)))
                .setTangent(toRadians(90.0))  //Idk if you need this
                .splineToConstantHeading(Vector2d(12.0, 48.0), toRadians(90.0))
                .splineToConstantHeading(Vector2d(12.0, 60.0), toRadians(90.0))
                .build()


        waitForStart()

        if (isStopRequested) return

        // Initialize variable to store spike position reads
        val voteCount = mutableMapOf<Vision.SpikeDirection, Int>()

        voteCount[Vision.SpikeDirection.LEFT] = 0
        voteCount[Vision.SpikeDirection.CENTER] = 0
        voteCount[Vision.SpikeDirection.RIGHT] = 0

        // Store each spike position read
        val maxReads = 25
        while (voteCount.values.sum() < maxReads && autoTimer.seconds() < 5.0) { //TODO make this a safe loop
            val spikeDirectionUpdate = vision.getSpikeMarkDirectionUpdate() ?: continue
            voteCount[spikeDirectionUpdate] = voteCount[spikeDirectionUpdate]!! + 1
            Log.i("Votes", voteCount.toString())
            telemetry.addData("Votes", voteCount.toString())
            telemetry.update()
        }

        // Set spikePosition to the highest voted position
        spikePosition = voteCount.entries.maxByOrNull {it.value}?.key ?: Vision.SpikeDirection.LEFT

        telemetry.addData("Spike Position", spikePosition.name)
        telemetry.update()

        // Initialize claw and passthrough position
        runBlocking(
            SequentialAction(
                claw.clawClose(),
                passthrough.passthroughDeposit()
            )
        )

        // Trajectory Decision Tree
        when (StartingPoseStorage.startingPose) {
            StartingPose.BLUE_RIGHT -> {
                // Drives to pixel placement branching node
                runBlocking(blueRightPartOne)

                // Extend passthrough
                runBlocking(
                    SequentialAction(
                        passthrough.passthroughHalfway(),
                        SleepAction(1.5)
                    )
                )

                // Drives to 1 of 3 pixel placement spots depending on vision data
                when (spikePosition) {
                    Vision.SpikeDirection.LEFT -> runBlocking(blueRightPixelLeft)
                    Vision.SpikeDirection.CENTER -> runBlocking(blueRightPixelCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueRightPixelRight)
                }

                // Pixel placement sequence
                runBlocking(
                    SequentialAction(
                        passthrough.passthroughPickup(), // Place pixel
                        SleepAction(1.0),
                        claw.clawPartial(), // Release pixel
                        SleepAction(1.0),
                        passthrough.passthroughHalfway(), // Lift passthrough up
                        SleepAction(0.5),
                        claw.clawClose(), // Close claw
                        passthrough.passthroughDeposit(), // Retract Passthrough
                        SleepAction(2.0)
                    )
                )

                // Converges branched nodes
                runBlocking(drive.actionBuilder(drive.pose)
                    .setTangent(toRadians(-135.0))
                    .splineToLinearHeading(Pose2d(-59.0, -48.0, toRadians(-90.0)), toRadians(-90.0))
                    .build()
                )

                // Continues from node convergence to backdrop branch
                runBlocking(blueRightPartTwo)

                // Drives to 1 of 3 backdrop placement spots depending on vision data
                when (spikePosition) {
                    Vision.SpikeDirection.LEFT -> runBlocking(blueRightBackdropLeft)
                    Vision.SpikeDirection.CENTER -> runBlocking(blueRightBackdropCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueRightBackdropRight)
                }

                //TODO add actions, lift actions, vision auto align?

                // Converges to parking node
                runBlocking(drive.actionBuilder(drive.pose)
                    .setTangent(toRadians(90.0))
                    .splineToLinearHeading(Pose2d(-36.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                    .build()
                )

                // Finish path and park
                runBlocking(bluePartThreeLeft) //TODO make option in starting pose selector to pick parking location
            }

            StartingPose.BLUE_LEFT -> {
                runBlocking(blueLeftPartOne) // Drives to pixel placement branch

                runBlocking(passthrough.passthroughHalfway())
                runBlocking(drive.actionBuilder(drive.pose).waitSeconds(2.0).build()) // Wait for passthrough to extend


                when (spikePosition) { // Drives to 1 of 3 pixel placement spots depending on vision data
                    Vision.SpikeDirection.LEFT -> runBlocking(blueLeftPixelLeft) //TODO make and add pixel placement command
                    Vision.SpikeDirection.CENTER -> runBlocking(blueLeftPixelCenter)
                    Vision.SpikeDirection.RIGHT -> runBlocking(blueLeftPixelRight)
                }

                runBlocking(passthrough.passthroughPickup())
                runBlocking(drive.actionBuilder(drive.pose).waitSeconds(1.0).build()) // Wait for passthrough to extend
                runBlocking(claw.clawPartial())
                runBlocking(passthrough.passthroughHalfway())
                runBlocking(drive.actionBuilder(drive.pose).waitSeconds(1.0).build()) // Wait for passthrough to extend


                runBlocking(drive.actionBuilder(drive.pose) // Drives to next node
                        .setTangent(toRadians(135.0))
                        .splineToLinearHeading(Pose2d(-54.0, 36.0, toRadians(-90.0)), toRadians(90.0))
                        .build()
                )

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

                runBlocking(bluePartThreeLeft) // Finish path

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