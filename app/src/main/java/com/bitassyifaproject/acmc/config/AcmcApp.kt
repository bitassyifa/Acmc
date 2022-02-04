package com.bitassyifaproject.acmc.config

import android.app.Application

class AcmcApp : Application() {
    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.create()
}