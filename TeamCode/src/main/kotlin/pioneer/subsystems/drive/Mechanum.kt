package pioneer.subsystems.drive

import pioneer.core.motor.Motor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.abs

class Mechanum(hardwareMap: HardwareMap) {

    enum class State { IDLE, MOVING }
    enum class Mode { FIELD_CENTRIC, ROBOT_CENTRIC }

    private var _state = State.IDLE
    private var _mode = Mode.ROBOT_CENTRIC

    val state: State get() = _state
    val mode: Mode get() = _mode

    fun setMode(newMode: Mode) {
        _mode = newMode
    }

    private val motors = arrayOf(
        Motor(hardwareMap, "FL"),
        Motor(hardwareMap, "FR"),
        Motor(hardwareMap, "BL"),
        Motor(hardwareMap, "BR")
    )

    private val directions = arrayOf(
        DcMotor.Direction.REVERSE,  // frontLeft
        DcMotor.Direction.FORWARD,  // frontRight
        DcMotor.Direction.REVERSE,  // backLeft
        DcMotor.Direction.FORWARD   // backRight
    )

    init {
        motors.forEachIndexed { index, motor ->
            motor.apply {
                setDirection(directions[index])
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

        val powers = arrayOf(
            adjustedY + adjustedX + rotation,  // frontLeft
            adjustedY - adjustedX - rotation,  // frontRight
            adjustedY - adjustedX + rotation,  // backLeft
            adjustedY + adjustedX - rotation   // backRight
        )

        normalizeAndApplyPower(powers)
    }

    fun stop() {
        setState(State.IDLE)
        motors.forEach { it.setPower(0.0) }
    }

    private fun normalizeAndApplyPower(powers: Array<Double>) {
        val maxPower = powers.maxOf { abs(it) }
        val scale = if (maxPower > 1.0) 1.0 / maxPower else 1.0

        motors.forEachIndexed { index, motor ->
            motor.setPower(powers[index] * scale)
        }
    }
}
