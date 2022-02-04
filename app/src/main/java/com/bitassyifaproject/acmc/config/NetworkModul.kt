package com.bitassyifaproject.acmc.config


import com.bitassyifaproject.acmc.auth.data.LoginAPI
import com.bitassyifaproject.acmc.extra.data.ExtraAPI
import com.bitassyifaproject.acmc.order.data.OrderAPI
import dagger.Module
import dagger.Provides


@Module
class NetworkModul {
    @Provides
    fun provideLoginAPI(): LoginAPI {
        return Connection.urlLogin().create(LoginAPI::class.java)
    }
    @Provides
    fun provideExtraAPI(): ExtraAPI {
        return Connection.urlCon().create(ExtraAPI::class.java)
    }
    @Provides
    fun provideOrderAPI(): OrderAPI {
        return Connection.urlCon().create(OrderAPI::class.java)
    }

}