package com.bitassyifaproject.acmc.config

import com.bitassyifaproject.acmc.auth.ui.LoginActivity
import com.bitassyifaproject.acmc.auth.ui.LoginFragment
import com.bitassyifaproject.acmc.home.ui.HomeFragment
import com.bitassyifaproject.acmc.order.ui.OrderActivity
import dagger.Component

@Component(modules = [NetworkModul::class])
interface ApplicationComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(orderActivity: OrderActivity)
}