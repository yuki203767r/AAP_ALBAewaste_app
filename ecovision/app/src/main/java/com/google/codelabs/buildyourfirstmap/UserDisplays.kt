package com.google.codelabs.buildyourfirstmap

import android.media.Rating


class UserDisplays (var name: String, var password: String, var pfp: ByteArray)
{

    fun getname(): String{
        return name
    }
    fun getpass(): String{
        return password
    }
    fun getpfp(): ByteArray {
        return pfp
    }

}