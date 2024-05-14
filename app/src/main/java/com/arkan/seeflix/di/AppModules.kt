package com.arkan.seeflix.di

import com.arkan.seeflix.data.source.local.database.AppDatabase
import com.arkan.seeflix.data.source.local.database.dao.BookmarkDao
import org.koin.dsl.module

object AppModules {
    private val localModule = module {
        single<BookmarkDao> { get<AppDatabase>().bookmarkDao() }
    }

    val modules = listOf(
        localModule
    )
}