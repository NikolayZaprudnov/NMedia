package ru.netology.nmedia

import android.app.Application


class App: Application (){
    override fun onCreate() {
        super.onCreate()
//        AppAuth.init(this)
    }
}