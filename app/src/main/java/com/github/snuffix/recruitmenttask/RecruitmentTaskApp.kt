package com.github.snuffix.recruitmenttask

import android.app.Application
import com.github.snuffix.recruitmenttask.cache.di.cacheModule
import com.github.snuffix.recruitmenttask.data.di.dataModule
import com.github.snuffix.recruitmenttask.domain.di.domainModule
import com.github.snuffix.recruitmenttask.domain.usecase.CoroutinesDispatcherProvider
import com.github.snuffix.recruitmenttask.mapper.DocumentsMapper
import com.github.snuffix.recruitmenttask.presentation.di.presentationModule
import com.github.snuffix.recruitmenttask.remote.di.remoteModule
import kotlinx.coroutines.Dispatchers
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
open class RecruitmentTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)

        startKoin {
            // Android context
            androidContext(this@RecruitmentTaskApp)
            // modules

            val productionModules = listOf(
                cacheModule,
                remoteModule,
                dataModule,
                domainModule,
                presentationModule,
                applicationModule,
                uiModule
            )

            modules(productionModules)
        }
    }

    private val applicationModule = module {
        single {
            CoroutinesDispatcherProvider(
                main = Dispatchers.Main,
                computation = Dispatchers.Default,
                io = Dispatchers.IO
            )
        }
    }

    private val uiModule = module {
        single { DocumentsMapper() }
    }
}
