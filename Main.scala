import com.sun.jna.{Library, Pointer, Native}
import java.util.concurrent.TimeUnit

object testlib {
  Native.register("/home/marcelo/Documents/Programacion/scala-jna-perf-test/libtestlib.so")
  
  @native def doSum(a: Int, b: Int): Int
}

object Main {
  def main(args: Array[String]) {
    
    def benchmark(what: String)(f: => Unit) = {
      val t0 = System.nanoTime()
      f
      val t1 = System.nanoTime()
      val elapsed = TimeUnit.MILLISECONDS.convert(t1-t0, TimeUnit.NANOSECONDS)
      println(s"$what: elapsed $elapsed ms.")
    }
    
    var acum: Int = 0
    
    // Warmup
    benchmark("warmup") {
      for (x <- 0 until 1) {
        testlib.doSum(1,1)
      }
    }

    benchmark("Execute") {
      for (x <- 0 until 20000) {
        acum = testlib.doSum(acum, x)
      }
    }

    println(s"Result: $acum")
  }
}