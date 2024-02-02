package org.firstinspires.ftc.teamcode.teleops.testing

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.PoseVelocity2d
import com.acmerobotics.roadrunner.Vector2d
import com.arcrobotics.ftclib.command.*
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.commands.drive.MotionProfiledJoystickDrive2
import org.firstinspires.ftc.teamcode.commands.drive.SimpleJoystickDrive
import org.firstinspires.ftc.teamcode.subsystems.localization.OdometryLocalizer
import org.firstinspires.ftc.teamcode.subsystems.*

@TeleOp
class DrivebaseTestTeleOp : CommandOpMode() {

    override fun initialize() {

        /****************************************************
         * Initialize hardware                              *
         ****************************************************/

        val voltageSensor = VoltageSensor(hardwareMap)
        val localizer = OdometryLocalizer(hardwareMap)
        val mecanum = MecanumDrive(hardwareMap, Pose2d(0.0, 0.0, Math.toRadians(0.0)), localizer, voltageSensor, telemetry)

        register(mecanum)

        val driver1 = GamepadEx(gamepad1)

        /**
         * Drive
         */
        val input = { PoseVelocity2d(Vector2d(driver1.leftY, -driver1.leftX), -driver1.rightX) }

        var fieldCentric = true

        mecanum.defaultCommand = SimpleJoystickDrive(mecanum, input, { fieldCentric })
    }

    override fun run() {
        super.run()
        telemetry.update()
    }

}
