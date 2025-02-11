package pioneer.tools.numerical

object NewtonRaphson {
    // Newton-Raphson Method
    fun optimize(func: (Double) -> Double, derivative: (Double) -> Double, initialGuess: Double, tolerance: Double = 1e-6): Double {
        var x = initialGuess
        while (Math.abs(func(x)) > tolerance) {
            x -= func(x) / derivative(x)
        }
        return x
    }
}
