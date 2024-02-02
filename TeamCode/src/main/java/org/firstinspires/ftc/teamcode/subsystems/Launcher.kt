package org.firstinspires.ftc.teamcode.subsystems

import android.graphics.Color
import android.util.Log
import com.acmerobotics.dashboard.canvas.Canvas
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.TimeProfile
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.constantProfile
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.hardware.SimpleServo
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import com.qualcomm.robotcore.util.ElapsedTime
import com.qualcomm.robotcore.util.Range.clip
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.FieldConfig
import java.util.Timer

/**
 * Claw subsystem consists of a servo, potentiometer and a color sensor.
 * @param hwMap HardwareMap.
 */
@Config
class Launcher(hwMap: HardwareMap) : SubsystemBase() {

    private val servo = SimpleServo(hwMap, launcherServoName, 0.0, 360.0)
    //private var colorSensor = hwMap.get(NormalizedColorSensor::class.java, colorSensorName)

    //private var timer = ElapsedTime()
    //private lateinit var motionProfile: TimeProfile

    val timer = ElapsedTime()

    private var setpoint: Double = 0.0
        set(position) {
            field = clip(position, 0.0, 1.0)
            //timer.reset()
            //motionProfile = TimeProfile(constantProfile(position - getPositionEstimate(), 0.0, clawMaxVel, -clawMaxAccel, clawMaxAccel).baseProfile)
            Log.i("Launcher desired position", setpoint.toString())
        }

    init {
        timer.reset()
        setpoint = launcherStartingPosition
        servo.position = launcherStartingPosition
    }

    override fun periodic() {
        Log.v("Launcher estimated angle", getPositionEstimate().toString())
        //servo.position = motionProfile[timer.seconds()].value()
        servo.position = setpoint
    }

    /**
     * Open to pick up the cone
     */
    fun launch() {
        if (timer.seconds() > 120.0) {
            setpoint = launchingPosition
        }
    }

    private fun getPositionEstimate() : Double {
        return servo.position
    }


    /**
     * For debugging/tuning purposes
     */
    fun fetchTelemetry(packet: TelemetryPacket) {
        packet.put("Position Estimate", getPositionEstimate())
        packet.put("Desired position", setpoint)
    }

    fun drawClaw(canvas: Canvas, clawOffset: Vector2d, pose: Pose2d) {
        canvas.setStroke("#8CA231")
        val (x, y) = pose.position.plus(
            pose.heading.inverse().times(
                clawOffset
            )
        )
        canvas.fillCircle(x, y, FieldConfig.spikeDiameter / 2.0 * 1.2)
    }

    companion object {
        const val launcherServoName = "launcher"

        @JvmField
        var launcherStartingPosition = 0.9
        @JvmField
        var launchingPosition = 0.4


    }

}