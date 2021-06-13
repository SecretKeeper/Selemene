package net.teamof.whisper

import android.content.Context
import io.objectbox.BoxStore
import net.teamof.whisper.models.MyObjectBox

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}