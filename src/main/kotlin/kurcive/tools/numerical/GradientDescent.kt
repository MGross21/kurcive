package robotics.tools.numerical

object GradientDescent {
    // Gradient Descent Method
    fun optimize(f: (Double) -> Double, gradF: (Double) -> Double, start: Double, learningRate: Double = 0.1, tolerance: Double = 1e-6): Double {
        var x = start
        var diff = Double.MAX_VALUE
        while (diff > tolerance) {
            val newX = x - learningRate * gradF(x)
            diff = Math.abs(newX - x)
            x = newX
        }
        return x
    }
}
