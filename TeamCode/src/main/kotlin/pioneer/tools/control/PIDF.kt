package pioneer.tools.control

/**
 * PIDF controller class for calculating control signals with proportional (P), integral (I),
 * derivative (D), and feed-forward (F) components.
 */
class PIDF(
    private var _kP: Double = 1.0,
    private var _kI: Double = 0.0,
    private var _kD: Double = 0.0,
    private var _FF: Double = 0.0,
    initialError: Double = 0.0,
//    private val threshold: Double = 0.0,
    private var mode: Mode = Mode.SIGN
) {
    private var sumError: Double = 0.0
    private var prevError: Double = initialError

    // Enum class for different types of control (Position or Velocity)
    enum class Type {
        POSITION, VELOCITY
    }

    // Enum class for modes (SIGN or ABSOLUTE)
    enum class Mode {
        SIGN, ABSOLUTE
    }

    enum class ValueType {
        WRAPPED,  // Used for values like angles (angles wrap at specific bounds, such as -π to π)
        UNWRAPPED // Used for values like distance, velocity (they do not wrap, they can grow indefinitely)
    }

    enum class
    /**
     * Gets the proportional (P) constant.
     */
    var kP: Double
        get() = _kP
        set(value) {
            _kP = value
        }

    /**
     * Gets the integral (I) constant.
     */
    var kI: Double
        get() = _kI
        set(value) {
            _kI = value
        }

    /**
     * Gets the derivative (D) constant.
     */
    var kD: Double
        get() = _kD
        set(value) {
            _kD = value
        }

    /**
     * Gets the feed-forward (F) constant.
     */
    var FF: Double
        get() = _FF
        set(value) {
            _FF = value
        }

    /**
     * Gets or sets the mode for the feed-forward component (SIGN or ABSOLUTE).
     */
    var controlMode: Mode
        get() = mode
        set(value) {
            mode = value
        }

    /**
     * Calculates the control signal (move value) based on current and target values.
     *
     * @param current Current value (e.g., current position, velocity)
     * @param target Target value (e.g., desired position, velocity)
     * @param normalizeError Flag to normalize error for wrapping angles (e.g., -pi to pi)
     * @return The calculated move value
     */
    fun calculate(current: Double, target: Double, normalizeError: Boolean = false): Double {
        var error = target - current

        // Normalize error if requested (useful for angle wrapping)
        if (normalizeError) {
            error = AngleUtils.normalizeRadians(error)
        }

        // Derivative (rate of change of error)
        val derivative = error - prevError

        // Integral (sum of errors over time)
        sumError += error

        // Save current error for next iteration
        prevError = error

        // Calculate PIDF output
        return kP * error + kI * sumError + kD * derivative + FF * when (mode) {
            Mode.SIGN -> error.sign
            Mode.ABSOLUTE -> 1.0
        }
    }

    /**
     * Returns the previous error value.
     *
     * @return Previous error value
     */
    fun getError(): Double {
        return prevError
    }

    /**
     * Resets the controller's integral and previous error values to initial conditions.
     */
    fun reset() {
        sumError = 0.0
        prevError = 0.0
    }

    /**
     * Returns the sum of errors (integral term).
     *
     * @return Sum of errors (integral)
     */
    fun getSumError(): Double {
        return sumError
    }

    /**
     * Returns the current threshold value.
     *
     * @return Threshold value
     */
    fun getThreshold(): Double {
        return threshold
    }
}
