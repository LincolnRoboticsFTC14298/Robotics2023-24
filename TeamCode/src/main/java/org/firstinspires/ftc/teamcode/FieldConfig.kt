package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.Vector2d

object FieldConfig {

    const val tileSize = 23.5 // unnecessary?

    const val spikeDiameter = 4.0 //in
    const val spikeHeight = 3.5 //in

    const val backdropAprilTagHeight = 2.0 //in
    const val audienceSmallAprilTagHeight = 2.0 //in
    const val audienceBigAprilTagHeight = 5.0 //in



    /**
     * All of the apriltags on the field and their respective ids.
     */
    enum class PoleType(val height: Int) {
        BLUE_BACKDROP_LEFT(1),
        BLUE_BACKDROP_CENTER(2),
        BLUE_BACKDROP_RIGHT(3),

        RED_BACKDROP_LEFT(4),
        RED_BACKDROP_CENTER(5),
        RED_BACKDROP_RIGHT(6),

        BLUE_FRONT_SMALL(9),
        BLUE_FRONT_LARGE(10),

        RED_FRONT_SMALL(8),
        RED_FRONT_LARGE(7)
    }


}