package com.hermanowicz.pantry.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.NotificationRepository
import com.hermanowicz.pantry.domain.product.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.settings.FetchDaysBeforeNotificationUseCase
import com.hermanowicz.pantry.receivers.NotificationBroadcastReceiver
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchDaysBeforeNotificationUseCase: FetchDaysBeforeNotificationUseCase,
    private val observeAllProductsUseCase: ObserveAllProductsUseCase
) : NotificationRepository {
    override suspend fun createNotification(products: List<Product>) {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        products.forEach { product ->
            intent.putExtra("product_name", product.name)
            intent.putExtra("product_id", product.id)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                product.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (product.expirationDate.isNotEmpty() || product.expirationDate == "-") {
                val calendar: Calendar? = createCalendar(product.expirationDate)
                if (calendar != null) {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            }
        }
    }

    override fun cancelNotification(productIdList: List<Int>) {
        productIdList.forEach { productId ->
            cancelNotification(productId)
        }
    }

    private fun cancelNotification(productId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            productId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    override suspend fun createNotificationForAllProducts(databaseMode: DatabaseMode) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val products = if (databaseMode == DatabaseMode.ONLINE && userId.isNotEmpty()) {
            observeAllProductsUseCase(databaseMode).first()
        } else {
            observeAllProductsUseCase(DatabaseMode.LOCAL).first()
        }
        createNotification(products)
    }

    override suspend fun cancelNotificationForAllProducts(databaseMode: DatabaseMode) {
        val products = observeAllProductsUseCase(databaseMode).first()
        products.forEach { product ->
            cancelNotification(products.map { product.id })
        }
    }

    private suspend fun createCalendar(expirationDate: String): Calendar? {
        var dateArray = expirationDate.split("\\.")
        if (dateArray.size < 2) {
            dateArray = expirationDate.split("-")
        }
        return if (dateArray.size > 2) {
            val daysBeforeNotification = fetchDaysBeforeNotificationUseCase().first()
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = dateArray[0].toInt()
            calendar[Calendar.MONTH] = dateArray[1].toInt() - 1
            calendar[Calendar.DAY_OF_MONTH] = dateArray[2].toInt()
            calendar[Calendar.HOUR_OF_DAY] = 12
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar.add(Calendar.DAY_OF_YEAR, -daysBeforeNotification)
            calendar
        } else {
            null
        }
    }
}
