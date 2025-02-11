package pioneer.subsystems.drive

import pioneer.core.motor.Motor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.abs

class DifferentialSwerve(hardwareMap: HardwareMap) {

    enum class State { IDLE, MOVING }
    enum class Mode { FIELD_CENTRIC, ROBOT_CENTRIC }

    private var _state = State.IDLE
    private var _mode = Mode.ROBOT_CENTRIC

    val state: State get() = _state
    val mode: Mode get() = _mode

    fun setMode(newMode: Mode) {
        _mode = newMode
    }

    // Motor setup for left and right side
    private val leftMotors = arrayOf(
        Motor(hardwareMap, "LF"),
        Motor(hardwareMap, "LB")
    )

    private val rightMotors = arrayOf(
        Motor(hardwareMap, "RF"),
        Motor(hardwareMap, "RB")
    )

    private val leftDirections = arrayOf(
        DcMotor.Direction.REVERSE,  // Left Front
        DcMotor.Direction.FORWARD   // Left Back
    )

    private val rightDirections = arrayOf(
        DcMotor.Direction.FORWARD,  // Right Front
        DcMotor.Direction.REVERSE   // Right Back
    )

    init {
        // Initialize left side motors
        leftMotors.forEachIndexed { index, motor ->
            motor.apply {
                setDirection(leftDirections[index])
                setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
            }
        }

        // Initialize right side motors
        rightMotors.forEachIndexed { index, motor ->
            motor.apply {
                setDirection(rightDirections[index])
                setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
            }
        }
    }

    private fun setState(newState: State) {
        _state = newState
    }

    fun move(x: Double, y: Double, rotation: Double, headingRadians: Double = 0.0) {
        setState(State.MOVING)

        val (adjustedX, adjustedY) = if (_mode == Mode.FIELD_CENTRIC) {
            Pair(
                x * cos(headingRadians) - y * sin(headingRadians),
                x * sin(headingRadians) + y * cos(headingRadians)
            )
        } else {
            Pair(x, y)
        }

        // For Differential Swerve, the control is split into left and right sets
        val leftPower = adjustedY + adjustedX + rotation  // left side motors power
        val rightPower = adjustedY - adjustedX - rotation  // right side motors power

        // Normalize power values to ensure they don't exceed motor limits
        val maxPower = maxOf(abs(leftPower), abs(rightPower))
        val scale = if (maxPower > 1.0) 1.0 / maxPower else 1.0

        // Apply normalized power to left and right motors
        leftMotors.forEach { it.setPower(leftPower * scale) }
        rightMotors.forEach { it.setPower(rightPower * scale) }
    }

    fun stop() {
        setState(State.IDLE)
        leftMotors.forEach { it.setPower(0.0) }
        rightMotors.forEach { it.setPower(0.0) }
    }
}
