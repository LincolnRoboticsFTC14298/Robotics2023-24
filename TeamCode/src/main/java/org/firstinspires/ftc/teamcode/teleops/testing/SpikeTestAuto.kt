package org.firstinspires.ftc.teamcode.teleops.testing

import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.subsystems.Vision


@Autonomous
class SpikeTestAuto : OpMode() {

    private var spikePosition: Vision.SpikeDirection = Vision.SpikeDirection.LEFT
    val autoTimer = ElapsedTime()
    override fun init() {
        val vision = Vision(hardwareMap)

        autoTimer.reset()

        //Start streaming camera
        //vision.startStreamingFrontCamera()

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
            telemetry.addData("xCoord", vision.spikeLocation.toString())
            telemetry.update()
        }

        // Set spikePosition to the highest voted position
        spikePosition = voteCount.entries.maxByOrNull {it.value}?.key ?: Vision.SpikeDirection.LEFT

        // Stop streaming
        //vision.stopStreamingFrontCamera()
    }

    override fun loop() {
        telemetry.addData("Spike Position", spikePosition.name)
        telemetry.update()
    }
}