package com.example.database.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent : DatabaseApi {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): DatabaseComponent
    }
}
