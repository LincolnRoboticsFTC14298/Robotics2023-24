package org.firstinspires.ftc.teamcode.teleops

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.PoseVelocity2d
import com.acmerobotics.roadrunner.Twist2d
import com.acmerobotics.roadrunner.Vector2d
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitUntilCommand
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.commands.drive.MotionProfiledJoystickDrive
import org.firstinspires.ftc.teamcode.commands.drive.SimpleJoystickDrive
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.Passthrough
import org.firstinspires.ftc.teamcode.subsystems.Vision
import org.firstinspires.ftc.teamcode.subsystems.VoltageSensor
import org.firstinspires.ftc.teamcode.subsystems.localization.OdometryLocalizer

@TeleOp
class TestTeleOp  : CommandOpMode() {
    override fun initialize() {
        /****************************************************
         * Initialize hardware                              *
         ****************************************************/

        val voltageSensor = VoltageSensor(hardwareMap)
        val lift = Lift(hardwareMap, voltageSensor)
        val claw = Claw(hardwareMap)
        val passthrough = Passthrough(hardwareMap)
        //val vision = Vision(hardwareMap)
        //val localizer = MecanumMonteCarloLocalizer(hardwareMap, vision, Pose2d(), arrayToRowMatrix(doubleArrayOf()))
        val localizer = OdometryLocalizer(hardwareMap)
        val mecanum = MecanumDrive(hardwareMap, Pose2d(0.0, 0.0, Math.toRadians(90.0)), localizer, voltageSensor)

        //register(lift, claw, passthrough, mecanum, vision)
        register(mecanum, claw, passthrough, lift)

        /****************************************************
         * Driver 1 Controls                                *
         * Driving and semi autonomous control              *
         ****************************************************/

        val driver1 = GamepadEx(gamepad1)

        /**
         * Drive
         */
        val input = { PoseVelocity2d(Vector2d(driver1.leftY, -driver1.leftX), -driver1.rightX) }

        var fieldCentric = true
        val fieldCentricProvider = { fieldCentric }

        mecanum.defaultCommand = SimpleJoystickDrive(mecanum, input, fieldCentricProvider)

        /**
         * Claw
         */
        driver1
            .getGamepadButton(GamepadKeys.Button.A)
            .whenPressed(
                InstantCommand(claw::close, claw)
            )

        driver1
            .getGamepadButton(GamepadKeys.Button.B)
            .whenPressed(
                InstantCommand(claw::open, claw) //TODO make version for dual claw where pressing B once opens primary claw, pressing it a second time opens secondary claw (make new InstantCommand, see ChatGPT answer)
            )

        //TEST
        driver1
            .getGamepadButton(GamepadKeys.Button.X)
            .whenPressed(
                InstantCommand(passthrough::deposit, passthrough)
            )

        driver1
            .getGamepadButton(GamepadKeys.Button.Y)
            .whenPressed(
                InstantCommand(passthrough::pickUp, passthrough)
            )


//        driver1
//            .getGamepadButton(GamepadKeys.Button.DPAD_UP)
//            .whenPressed(
//                InstantCommand(lift::pickUp, lift)
//            )
//
//        driver1
//            .getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
//            .whenPressed(
//                InstantCommand(lift::pickUp, lift)
//            )


    }

}