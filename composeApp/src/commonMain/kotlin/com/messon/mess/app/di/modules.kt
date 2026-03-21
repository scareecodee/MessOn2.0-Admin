package com.messon.mess.app.di

import com.messon.mess.app.core.AppDispatchers
import com.messon.mess.app.data.remote.network.Auth
import com.messon.mess.app.data.remote.network.CollegeApi
import com.messon.mess.app.data.remote.network.CouponVerification
import com.messon.mess.app.data.remote.network.MessApi
import com.messon.mess.app.domain.repository.AuthRepository
import com.messon.mess.app.domain.repository.CollegeRepository
import com.messon.mess.app.domain.repository.CouponVerificationRepo
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.viewmodel.AuthViewModel
import com.messon.mess.app.presentation.viewmodel.CollegeScreenVM
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import com.messon.mess.app.presentation.viewmodel.MenuCreateVM
import com.messon.mess.app.presentation.viewmodel.MenuFetchVM
import com.messon.mess.app.presentation.viewmodel.MessRuleVM
import com.messon.mess.app.presentation.viewmodel.MessTimingVM
import com.messon.mess.app.presentation.viewmodel.PrefWindowVM
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {

    single { CouponVerification(get()) }
    single { Auth(get()) }
    single { CollegeApi(get()) }
    single { MessApi(get()) }





    single { AuthRepository(get()) }
    single { CollegeRepository(get()) }
    single{ CouponVerificationRepo(get()) }
    single{ MessRepository(get()) }


    viewModel {
        AuthViewModel(
            secureStorage = get(),
            dispatcher = get<AppDispatchers>().io,
            authRepository = get(),
            collegeRepository = get()
        )
    }

    viewModel {
        CouponViewModel(
            secureStorage = get(),
            dispatcher =get<AppDispatchers>().io,
            couponVerificationRepo = get()
        )
    }

    viewModel {
        CollegeScreenVM(
            collegeRepository = get()
        )
    }

    viewModel {
        MenuCreateVM(
            secureStorage = get(),
            messRepository =get()
        )
    }

    viewModel {
        MenuFetchVM(
            secureStorage = get(),
            messRepository = get()
        )
    }

    viewModel {
        MessRuleVM(
            secureStorage = get(),
            messRepository = get()
        )
    }

    viewModel {
        PrefWindowVM(
           get(),get()
        )
    }

    viewModel {
        MessTimingVM(
            get(),get()
        )
    }

}


expect val platformModule: Module