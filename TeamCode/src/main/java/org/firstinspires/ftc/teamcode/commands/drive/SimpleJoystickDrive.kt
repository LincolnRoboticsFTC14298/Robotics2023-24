package org.firstinspires.ftc.teamcode.commands.drive

import com.acmerobotics.roadrunner.PoseVelocity2d
import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive

class SimpleJoystickDrive(
    private val mecanum: MecanumDrive,
    private val input: () -> PoseVelocity2d,
    private val fieldCentric: () -> Boolean,
) : CommandBase() {

    init{
        addRequirements(mecanum)
    }

    override fun execute() {
        val power =
            if (fieldCentric.invoke())
                mecanum.pose.inverse() * input.invoke()
            else
                input.invoke()
        mecanum.setDrivePowers(power)
    }

}