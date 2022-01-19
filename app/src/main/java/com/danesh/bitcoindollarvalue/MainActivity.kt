package com.danesh.bitcoindollarvalue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.danesh.bitcoindollarvalue.repository.retrofit.GetDataService
import com.danesh.bitcoindollarvalue.repository.retrofit.RetrofitServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = RetrofitServiceBuilder.buildService(GetDataService::class.java)
        val call = request.getBitcoinValue("USD",1)

        call.enqueue(object : Callback<Double>{
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful){
                    Toast.makeText(this@MainActivity, "model is ${response.body()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Double>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}