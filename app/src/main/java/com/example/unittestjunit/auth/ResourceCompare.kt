package com.example.unittestjunit.auth

import android.content.Context

class ResourceCompare {

    fun compareString(context: Context,str:Int,string:String):Boolean{
        return context.getString(str) == string
    }
}