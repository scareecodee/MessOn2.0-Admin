package com.messon.mess.app.di

import com.messon.mess.app.core.AppDispatchers
import com.messon.mess.app.data.remote.network.Auth
import com.messon.mess.app.data.remote.network.CouponVerification
import com.messon.mess.app.domain.repository.AuthRepository
import com.messon.mess.app.domain.repository.CouponVerificationRepo
import com.messon.mess.app.presentation.viewmodel.AuthViewModel
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {

    single { CouponVerification(get()) }

    single { Auth(get()) }

    single { AuthRepository(get()) }

    single{ CouponVerificationRepo(get()) }

    viewModel {
        AuthViewModel(
            secureStorage = get(),
            dispatcher = get<AppDispatchers>().io,
            authRepository = get()
        )
    }

    viewModel {
        CouponViewModel(
            secureStorage = get(),
            dispatcher =get<AppDispatchers>().io,
            couponVerificationRepo = get()
        )
    }

}


expect val platformModule: Module