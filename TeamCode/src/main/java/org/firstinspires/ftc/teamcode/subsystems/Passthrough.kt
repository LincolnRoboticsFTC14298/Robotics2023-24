package org.firstinspires.ftc.teamcode.subsystems

import android.util.Log
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.*
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.hardware.ServoEx
import com.arcrobotics.ftclib.hardware.SimpleServo
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.util.epsilonEquals
import java.lang.Math.random
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sign


/**
 * Passthrough subsystem consist of a servo that rotates
 * the mechanism containing the claw from pick up to drop off.
 * The angle is that made with the heading vector (i.e. w/ the front of the intake side)
 * @param hwMap             HardwareMap
 */
@Config
class Passthrough(hwMap: HardwareMap, startingPosition: Double = passthroughHalfwayPosition) : SubsystemBase() {

    /**
     * @see <a href="https://docs.ftclib.org/ftclib/features/hardware">FTCLib Docs: Hardware</a>
     */
    private val servoLeft: ServoEx = SimpleServo(
        hwMap,
        leftPassthroughName,
        0.0,
        360.0
    )
    private val servoRight: ServoEx = SimpleServo(
        hwMap,
        rightPassthroughName,
        0.0,
        360.0
    )

    private var timer = ElapsedTime()
    private lateinit var motionProfile: TimeProfile

    private var displacement: Double = 0.0

    var setpoint: Double = 0.0 //random() / 50.0 //TODO fucking cursed fix later
        set(position) {
            Log.v("setpoint attempt", setpoint.toString())
            displacement = position - getPositionEstimate()
            if (!(displacement epsilonEquals 0.0)) {
                field = position //TODO Range.clip like in claw & lift
                timer.reset()
                motionProfile = TimeProfile(constantProfile(kotlin.math.abs(displacement), 0.0, passthroughMaxVel, -passthroughMaxAccel, passthroughMaxAccel).baseProfile)
                Log.i("Passthrough desired position", setpoint.toString())
            } else {
                field = position //TODO Range.clip like in claw & lift
                timer.reset()
                motionProfile = TimeProfile(constantProfile(kotlin.math.abs(0.01), 0.0, passthroughMaxVel, -passthroughMaxAccel, passthroughMaxAccel).baseProfile)
            }
        }

    init {
        servoRight.inverted = true

        setpoint = startingPosition
        setPosition(startingPosition)  // TODO investigate why passthrough position goes up and down after init
    }

    // TODO: check direction of servo

    override fun periodic() {
        val targetPosition = (setpoint - displacement) + sign(displacement) * motionProfile[timer.seconds()].value()
        setPosition(targetPosition)
        Log.v("Passthrough position estimate", getPositionEstimate().toString())
    }

    fun junctionDeposit() {
        setpoint = passthroughJunctionAngle
    }

    /**
     * Rotate servo to drop off position.
     */
    fun deposit() {
        setpoint = passthroughDepositPosition
    }

    /**
     * Rotate servo to pick up cone.
     */
    fun pickUp() {
        setpoint = passthroughPickUpPosition
    }

    fun halfway() {
        setpoint = passthroughHalfwayPosition
    }

    fun setPosition(position: Double) {
        servoRight.position = position
        servoLeft.position = position
    }

    /**
     * @return The position of the passthrough relative to where it is attached on the lift.
     */
    fun getRelativePosition(): Pose2d {
        val currAngle = getPositionEstimate()
        val heading = if (currAngle <= 90.0) -180.0 else 0.0
        return Pose2d(-cos(toRadians(currAngle)) + passthroughOffsetDistanceFromLift, 0.0, heading)
    }

    fun getFutureRelativePosition(): Pose2d {
        val desiredAngle = setpoint
        val heading = if (desiredAngle <= 90.0) -180.0 else 0.0
        return Pose2d(-cos(toRadians(desiredAngle)) + passthroughOffsetDistanceFromLift, 0.0, heading)
    }

    /**
     * @return Angle estimate based on motion profiling in degrees.
     */
    fun getPositionEstimate() : Double {
        return servoRight.position
    }

    /**
     * @return If at desired angle based on time.
     */
    fun atTarget(): Boolean {
        return timer.seconds() - motionProfile.duration > passthroughTimeTolerance
    }

    fun timeToTarget(position: Double) =
        TimeProfile(
            constantProfile(kotlin.math.abs(position - getPositionEstimate()), 0.0, passthroughMaxVel, -passthroughMaxAccel, passthroughMaxAccel).baseProfile
        ).duration

    /**
     * For debugging/tuning purposes
     */
    fun fetchTelemetry(packet: TelemetryPacket) {
        packet.put("Passthrough desired angle", setpoint)
    }

    companion object {
        const val leftPassthroughName = "left"
        const val rightPassthroughName = "right"

        const val passthroughMinPosition = 0.0 // empirically determined servo units
        const val passthroughMaxPosition = 0.6 // empirically determined servo units

        const val passthroughOffsetDistanceFromLift = 0.0

        @JvmField
        var passthroughPickUpPosition = passthroughMinPosition
        @JvmField
        var passthroughDepositPosition = passthroughMaxPosition
        @JvmField
        var passthroughHalfwayPosition = 0.14
        @JvmField
        var passthroughJunctionAngle = -15.0

        @JvmField
        var passthroughMaxVel = 25.0
        @JvmField
        var passthroughMaxAccel = 1.0

        @JvmField
        var passthroughTimeTolerance = 0.2 // Seconds to wait after motion profile supposedly complete
    }

}