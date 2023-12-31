package org.firstinspires.ftc.teamcode.vision

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.FieldConfig.poleBaseHeight
import org.firstinspires.ftc.teamcode.FieldConfig.poleDiameter
import org.firstinspires.ftc.teamcode.subsystems.Vision
import org.firstinspires.ftc.teamcode.vision.modulelib.InputModule
import org.firstinspires.ftc.teamcode.vision.modulelib.ModularPipeline
import org.firstinspires.ftc.teamcode.vision.modules.*
import org.firstinspires.ftc.teamcode.vision.modules.features.*
import org.firstinspires.ftc.teamcode.vision.modules.scorers.*
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.drawContours


open class SpikeDetectionPipeline(
    private var displayMode: DisplayMode = DisplayMode.ALL_CONTOURS,
    camera: Vision.Companion.CameraData,
    isRedAlliance: Boolean,
    var telemetry: Telemetry?
) : ModularPipeline() {

    //private val camMat = Mat()
    //private val distCoeffs = Mat()

    enum class DisplayMode {
        RAW_CAMERA_INPUT,
        RAW_SPIKE_MASK,
        DENOISED_SPIKE_MASK,
        UNFILTERED_CONTOURS,
        FILTERED_CONTOURS,
        ALL_CONTOURS
    }

    // Modules //
    private val inputModule = InputModule()
    //private val undistort = UndistortLens(inputModule, camMat, distCoeffs)
    private val labColorSpace = ColorConverter(inputModule, Imgproc.COLOR_RGB2Lab)
    private val spikeMask: Filter = if(isRedAlliance){Filter(labColorSpace, Scalar(0.0, 110.0, 150.0), Scalar(255.0, 150.0, 200.0))} else {Filter(labColorSpace, Scalar(0.0, 110.0, 150.0), Scalar(255.0, 150.0, 200.0))}
    private val denoisedSpikeMask = Denoise(spikeMask, 5, 5, 3, 3)
    private val rawSpikeContours = Contours(denoisedSpikeMask)

    // Spike Mark Scorer //
    private val spikeConvexityScorer = DiffSquaredScorer(Convexity(), 0.988, 11.09)
    private val spikeExtentScorer = DiffSquaredScorer(Extent(), 0.661, 0.036)
    private val spikeSolidityScorer = DiffSquaredScorer(Solidity(), 0.909, 0.227)
    private val spikeAspectRatioScorer = DiffSquaredScorer(AspectRatio(), 1.369, 0.23)
    private val spikeContours = FilterContours(rawSpikeContours, 0.05, spikeConvexityScorer + spikeExtentScorer + spikeSolidityScorer + spikeAspectRatioScorer)

    // Results Modules //
    private val spikeResultsModule = ContourResults(spikeContours, camera, poleDiameter, poleBaseHeight) //TODO MEASURE AND CHANGE OFFSETS

    // Data we care about and wish to access
    var spikeResults = listOf<ContourResults.AnalysisResult>()

    init {
        addEndModules(spikeResultsModule)
    }

    override fun processFrameForCache(input: Mat) : Mat {

        // Get the data we want (yipee)
        spikeResults = spikeResultsModule.processFrame(input)


        // Telemetry for Testing //
        telemetry?.addData("displaymode", displayMode)

        telemetry?.addLine("mean, variance")
        telemetry?.addData("aspectRatio", spikeAspectRatioScorer.feature.mean().toString() + ", " + spikeAspectRatioScorer.feature.variance().toString())
        telemetry?.addData("convexity", spikeConvexityScorer.feature.mean().toString() + ", " + spikeConvexityScorer.feature.variance().toString())
        telemetry?.addData("extent", spikeExtentScorer.feature.mean().toString() + ", " + spikeExtentScorer.feature.variance().toString())
        telemetry?.addData("solidity", spikeSolidityScorer.feature.mean().toString() + ", " + spikeSolidityScorer.feature.variance().toString())
        telemetry?.addData("aspectRatio min, max", (spikeAspectRatioScorer.feature as AspectRatio).min().toString() + ", " + spikeAspectRatioScorer.feature.max().toString())
        //telemetry.addData("areaResults", poleArea.areaResultsList().sorted().toString())

        telemetry?.update()


        // Display //
        return when (displayMode) {
            DisplayMode.RAW_CAMERA_INPUT -> input
            DisplayMode.RAW_SPIKE_MASK -> spikeMask.processFrame(input)
            DisplayMode.DENOISED_SPIKE_MASK -> denoisedSpikeMask.processFrame(input)
            DisplayMode.UNFILTERED_CONTOURS ->{
                drawContours(input, rawSpikeContours.processFrame(input), -1, Scalar(255.0, 0.0, 0.0), 1) //Red for raw stack contours
                input
            }
            DisplayMode.FILTERED_CONTOURS ->{
                drawContours(input, spikeContours.processFrame(input), -1, Scalar(0.0, 0.0, 255.0), 2) //Blue for filtered stack contours
                input
            }
            DisplayMode.ALL_CONTOURS -> {
                drawContours(input, rawSpikeContours.processFrame(input), -1, Scalar(255.0, 0.0, 0.0), 1) //Red for raw stack contours
                drawContours(input, spikeContours.processFrame(input), -1, Scalar(0.0, 0.0, 255.0), 2) //Blue for filtered stack contours
                input
            }
        }
    }

    override fun onViewportTapped() {
        val modes = enumValues<DisplayMode>()
        val nextOrdinal = (displayMode.ordinal + 1) % modes.size
        displayMode = modes[nextOrdinal]
    }

}