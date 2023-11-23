package org.firstinspires.ftc.teamcode.teleops

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Twist2d
import com.acmerobotics.roadrunner.Vector2d
import com.arcrobotics.ftclib.command.*
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.gamepad.TriggerReader
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.FieldConfig
import org.firstinspires.ftc.teamcode.commands.*
import org.firstinspires.ftc.teamcode.commands.drive.MotionProfiledJoystickDrive
import org.firstinspires.ftc.teamcode.subsystems.localization.OdometryLocalizer
import org.firstinspires.ftc.teamcode.subsystems.*

@TeleOp
class BasicTeleOp : CommandOpMode() {

    override fun initialize() {

        /****************************************************
         * Initialize hardware                              *
         ****************************************************/

        val voltageSensor = VoltageSensor(hardwareMap)
        val localizer = OdometryLocalizer(hardwareMap)
        val mecanum = MecanumDrive(hardwareMap, Pose2d(0.0, 0.0, Math.toRadians(90.0)), localizer, voltageSensor, telemetry)

        register(mecanum)

        val driver1 = GamepadEx(gamepad1)

        /**
         * Drive
         */
        val input = { Twist2d(Vector2d(driver1.leftY, -driver1.leftX), -driver1.rightX) }

        var fieldCentric = false

        mecanum.defaultCommand = MotionProfiledJoystickDrive(mecanum, input, { fieldCentric }, telemetry = telemetry)
    }

    override fun run() {
        super.run()
        telemetry.update()
    }

}
