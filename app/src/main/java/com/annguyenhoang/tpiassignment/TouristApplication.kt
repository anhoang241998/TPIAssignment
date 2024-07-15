package com.annguyenhoang.tpiassignment

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.annguyenhoang.tpiassignment.di.coroutineModule
import com.annguyenhoang.tpiassignment.di.networkModules
import com.annguyenhoang.tpiassignment.di.repositoryModule
import com.annguyenhoang.tpiassignment.di.viewModelModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class TouristApplication : Application(), ImageLoaderFactory {

    private val modules = listOf(
        networkModules,
        repositoryModule,
        viewModelModule,
        coroutineModule
    )

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLogger()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TouristApplication)
            modules(modules)
        }
    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(0)
            .methodOffset(0)
            .tag("")
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, "-$tag", message, t)
                }
            })
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this@TouristApplication)
            .memoryCache {
                MemoryCache.Builder(this@TouristApplication)
                    .maxSizePercent(1.toDouble())
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this@TouristApplication.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }

}