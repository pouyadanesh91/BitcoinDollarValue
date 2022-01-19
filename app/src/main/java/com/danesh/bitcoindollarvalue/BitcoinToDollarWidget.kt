package com.danesh.bitcoindollarvalue

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.danesh.bitcoindollarvalue.repository.retrofit.GetDataService
import com.danesh.bitcoindollarvalue.repository.retrofit.RetrofitServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Implementation of App Widget functionality.
 */
class BitcoinToDollarWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.bitcoin_to_dollar_widget)

    val request = RetrofitServiceBuilder.buildService(GetDataService::class.java)
    val call = request.getBitcoinValue("USD", 1)

    call.enqueue(object : Callback<Double> {
        override fun onResponse(call: Call<Double>, response: Response<Double>) {
            if (response.isSuccessful) {
                val widgetText =
                    context.getString(R.string.txtBitcoinValue, 1 / (response.body()!!.toFloat()))
                views.setTextViewText(R.id.appwidget_text, widgetText)
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }

        override fun onFailure(call: Call<Double>, t: Throwable) {
            Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
        }
    })


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)


}