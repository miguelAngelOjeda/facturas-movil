package com.example.palermo.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import kotlin.reflect.KProperty

class Prefs(context: Context) {
    fun fechaComoTexto(): String {
        return Calendar.getInstance().time.toFormato(Prefs.FORMAT_SYNC)
    }

    val PREFS_FILENAME = "py.coop.coomecipar.prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);
    private val LAST_SYNC_FIELD = "LAST_SYNC"

    companion object {
        val FORMAT_SYNC = "dd-MM-yyyy HH:mm"
    }

    internal var syncRunningMemory: Boolean? = null

    var syncRunning: Boolean
        get() {
            if (syncRunningMemory == null) {
                syncRunningMemory = prefs.getBoolean("syncRunning", false)
            }
            return syncRunningMemory!!
        }
        set(value) {
            syncRunningMemory = value
            prefs.edit().putBoolean("syncRunning", value).commit()
        }
    var crashedOutOfMemory: Boolean
        get() = prefs.getBoolean("crashedOutOfMemory", false)
        set(value) {
            prefs.edit().putBoolean("crashedOutOfMemory", value).commit()
        }

    var shouldDestroyDb: Boolean
        get() = prefs.getBoolean("shouldDestroyDb", false)
        set(value) {
            prefs.edit().putBoolean("shouldDestroyDb", value).commit()
        }

    var debugMode: Boolean
        get() = prefs.getBoolean("debug", false)
        set(value) {
            prefs.edit().putBoolean("debug", value).apply()
        }


}

