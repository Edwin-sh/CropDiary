package com.example.cropdiary.di.viewModel

import com.example.cropdiary.ui.viewmodel.WorksViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {
    @Provides
    fun provideWorksViewModel():WorksViewModel{
        return WorksViewModel()
    }
}