package com.arkan.seeflix.di

import com.arkan.seeflix.data.datasource.BookmarkDataSource
import com.arkan.seeflix.data.datasource.BookmarkDataSourceImpl
import com.arkan.seeflix.data.repository.BookmarkRepository
import com.arkan.seeflix.data.repository.BookmarkRepositoryImpl
import com.arkan.seeflix.data.source.local.database.AppDatabase
import com.arkan.seeflix.data.source.local.database.dao.BookmarkDao
import com.arkan.seeflix.presentation.bookmark.BookmarkViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
            single<BookmarkDao> { get<AppDatabase>().bookmarkDao() }
        }

    private val datasource =
        module {
            single<BookmarkDataSource> { BookmarkDataSourceImpl(get()) }
        }

    private val repository =
        module {
            single<BookmarkRepository> { BookmarkRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::BookmarkViewModel)
        }

    val modules =
        listOf(
            localModule,
            datasource,
            repository,
            viewModelModule,
        )
}
