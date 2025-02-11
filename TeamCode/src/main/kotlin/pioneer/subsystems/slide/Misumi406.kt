package pioneer.subsystems.slide

class Misumi406(
    hardwareMap: HardwareMap,
    motor1: Motor,
    motor2: Motor? = null,
    type: Type = Type.HORIZONTAL,
    pid: PID? = null,
) : Slide(hardwareMap, motor1, motor2, type, pid){

    override val maxExtension: Double =  500 // 406 Stroke Length Converted to Ticks  // Misumi-specific limit (NOT ACCURATE)
    override val minExtension: Double = 0.0

}
