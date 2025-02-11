package pioneer.core.servo

class ServoExtensionExample(minPos: Double, maxPos: Double, hardwareMap: HardwareMap, servoName: String) : Servo(minPos, maxPos, hardwareMap, servoName) {

    // Custom states for the servo (applies to position-controlled mode)
    enum class State(val position: Double) {
        OPEN(1.0),
        CLOSE(0.0),
        HALF(0.5),
        CUSTOM_OTHER(0.25)
    }

    // Override setPosition to use custom states for position-controlled servos
    /**
     * Set the position of the servo based on a custom state.
     * @param state The custom state to set the servo to
     * Example usage:
     * ```
     * servo.setState(OPEN)
     */
    override fun setState(state: State) {
        super.setState(state.position)
    }
}
