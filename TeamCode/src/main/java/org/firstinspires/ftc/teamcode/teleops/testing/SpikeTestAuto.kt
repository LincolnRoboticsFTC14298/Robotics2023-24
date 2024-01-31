package org.firstinspires.ftc.teamcode.teleops.testing

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.subsystems.Vision


@Autonomous
class SpikeTestAuto : LinearOpMode() {
    override fun runOpMode() {

        val vision = Vision(hardwareMap, telemetry = telemetry)



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

        while (opModeIsActive()) {
            telemetry.addData("Spike Position", spikePosition.name)
            telemetry.update()
        }
    }
}