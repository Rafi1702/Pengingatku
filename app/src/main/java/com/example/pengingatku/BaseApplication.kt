package com.example.pengingatku

import android.app.Application
import androidx.room.Room
import com.example.pengingatku.data.datasource.local.AppDatabase
import com.example.pengingatku.data.repository.AlarmRepository
import com.example.pengingatku.worker.AlarmScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module



val alarmModules = module{
    single{
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,  "AlarmApp"
        ).build()
    }
    single{
        AlarmScheduler(androidContext())
    }
    single{
        AlarmRepository(appDatabase = get<AppDatabase>(), alarmScheduler = get<AlarmScheduler>())
    }
}
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(alarmModules)
        }
    }

}