package com.example.financesapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(context: Context): Context {
        return context.applicationContext
    }
}
