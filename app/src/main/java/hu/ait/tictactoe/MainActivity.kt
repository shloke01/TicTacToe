package hu.ait.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import hu.ait.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var chronometer: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener {
            binding.tttview.resetGame()
        }

        chronometer = binding.chronometer
    }

    fun showText(message: String) {
        binding.tvMessage.text = message
    }

    fun startCounter(){
        binding.chronometer.base = SystemClock.elapsedRealtime()
        binding.chronometer.start()
    }

    fun stopCounter() {
        binding.chronometer.stop()
    }

}