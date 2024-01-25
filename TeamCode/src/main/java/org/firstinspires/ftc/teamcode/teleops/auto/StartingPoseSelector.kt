package org.firstinspires.ftc.teamcode.teleops.auto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.localization.StartingPoseStorage


@TeleOp
class StartingPoseSelector() : OpMode() {
    override fun init() {
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
    }

    override fun loop() {
        if (gamepad1.a) {
            StartingPoseStorage.startingPose = AutoTrajectoryKotlin.StartingPose.BLUE_RIGHT
        }

        if (gamepad1.b) {
            StartingPoseStorage.startingPose = AutoTrajectoryKotlin.StartingPose.BLUE_LEFT
        }

        if (gamepad1.x) {
            StartingPoseStorage.startingPose = AutoTrajectoryKotlin.StartingPose.RED_RIGHT
        }

        if (gamepad1.y) {
            StartingPoseStorage.startingPose = AutoTrajectoryKotlin.StartingPose.RED_LEFT
        }

        if (gamepad1.right_bumper) {
            return
        }

        telemetry.addData("Starting Pose", StartingPoseStorage.startingPose.toString())
        telemetry.update()
    }

}