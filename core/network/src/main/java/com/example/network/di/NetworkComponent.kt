package com.example.network.di

import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent : NetworkApi
