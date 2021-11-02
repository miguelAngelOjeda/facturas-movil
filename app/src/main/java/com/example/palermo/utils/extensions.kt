package com.example.palermo.utils

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.*
import java.util.*


typealias CallBack = () -> Unit
typealias ItemCallback<T> = (T) -> Unit
fun ViewGroup.inflate(@LayoutRes layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)

operator fun CompositeDisposable.plusAssign(subscription: Disposable): Unit {
    add(subscription)
}


fun FragmentManager.removeFragment(frameId: Int) {
    val selected = findFragmentById(frameId)
    if (selected != null) {
        inTransaction{
            remove(selected)
        }
    }
}

fun FragmentManager.replaceFragment(fragment: Fragment, frameId: Int) {
    inTransaction{replace(frameId, fragment)}
}

var Switch.isDisabled: Boolean
    get() = !isEnabled
    set(value) { isEnabled = !value }


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun Any.info(message: String) = Log.i(javaClass.simpleName, message)
fun Any.d(message: String, exception: Exception? = null) {
    Log.d(javaClass.simpleName, message, exception)
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}

fun String.toLongOCero(): Long {
    try {
        return toLong()
    } catch (e: Exception) {
        return 0
    }
}

fun Number.toCurrency() = NumberFormat.getNumberInstance().format(this)

fun String.toIntOCero(): Int {
    try {
        return replace("[,\\.]".toRegex(), "").toInt()
    } catch (e: Exception) {
       return 0
    }
}

fun RecyclerView.setup(activity: FragmentActivity?) {
    this.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
    if (activity != null) {
        this.addItemDecoration(SimpleDividerItemDecoration(activity.baseContext))
    }
    this.setHasFixedSize(true)
}

fun Double?.toStringOCero(): String {
    return (this ?: 0).toString()
}

fun Editable.toLongOCero() = this.toString().toLongOCero()
fun Editable.toIntOCero() = this.toString().toIntOCero()
fun Editable.toDoubleOrNull() = this.toString().toDoubleOrNull()
fun Editable.toDate() = this.toString().toDate()
fun Long.toFecha() = Date(this)

fun EditText.setEntero(entero: Int?) { this.setText((entero ?: "").toString()) }
fun EditText.setEnteroLargo(entero: Long?) { this.setText((entero ?: "").toString()) }

fun EditText.setDecimal(valor: Double?) { this.setText((valor ?: "").toString()) }
fun EditText.onEnterFocus(function: CallBack) {
    setOnFocusChangeListener { _, has ->
        if (has) {
            function()
        }
    }
}
fun EditText.onLostFocus(function: (valor: String) -> Unit) {
    setOnFocusChangeListener { _, has ->
        if (!has) {
            function(text.toString())
        }
    }
}

fun EditText.validate(validator: (String) -> Boolean, message: String) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged(editable.toString())
        }
    })
}

fun Spinner.onItemChanged(callback: (position: Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            callback(position)
        }
    }
}

fun Date.formatoHumano(): String {
    return DateFormat.getDateInstance().format(this)
}

fun Date.toFechaCorta(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(this)
}
fun Date.toFormato(formato: String): String {
    val dateFormat = SimpleDateFormat(formato)
    return dateFormat.format(this)
}

fun String.toFecha() = toDate(format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")

fun String.toDate(format: String = "dd/MM/yyyy"): Date {
    try {
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.parse(this)
    } catch (  e: ParseException) {
        d("Error al parsear fecha", e)
        return Date()
    }
}

fun String.fromSNtoBoolean() = this == "S"

fun String.fromTimestampToCalendar(): Calendar {
    val date = toDate("yyyy-MM-dd HH:mm:ss")
    val cal =  Calendar.getInstance()
    cal.setTime(date)
    return cal
}

fun Date.toHora(): String {
    val dateFormat = SimpleDateFormat("HH:mm")
    return dateFormat.format(this)
}

inline fun EditText.doOnEditorAction(
        vararg actionIds: Int,
        crossinline action: (text: String) -> Unit
) {
    setOnEditorActionListener { _, id, event ->
        if (id in actionIds) {
            action(this.text.toString())
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}



val format = DecimalFormat("#.##");
val MiB = 1024 * 1024;
val KiB = 1024;

fun getFileSize(file: File): String {

    if (!file.isFile()) {
        return "no existe"
    }
    val length = file.length();

    if (length > MiB) {
        return format.format(length / MiB) + " MiB";
    }
    if (length > KiB) {
        return format.format(length / KiB) + " KiB";
    }
    return format.format(length) + " B"
}

//funcion que agrega un datepicker a un EditText
fun EditText.addDatePicker(callback: (String) -> Unit) {
    this.setOnClickListener {
        // Get Current Date
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { vista, year, monthOfYear, dayOfMonth ->
                    val fecha = Calendar.getInstance().makeDate(dayOfMonth, monthOfYear, year).toFechaCorta()
                    callback(fecha)
                    this.setText(fecha)
                }, mYear, mMonth, mDay)
        datePickerDialog.show()
    }
}

fun Calendar.makeDate(day: Int, month: Int, year: Int): Date {
    this.set(Calendar.YEAR, year)
    this.set(Calendar.MONTH, month)
    this.set(Calendar.DAY_OF_MONTH, day)
    return this.getTime()
}

fun getBitmap(uri: Uri?, context: Context): Bitmap? {
    val mContentResolver = context.getContentResolver()

    var stream: InputStream? = null
    try {
        val IMAGE_MAX_SIZE = 1200000 // 1.2MP
        stream = mContentResolver.openInputStream(uri!!)

        // Decode image size
        var o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        BitmapFactory.decodeStream(stream, null, o)
        stream!!.close()


        var scale = 1
        while (o.outWidth * o.outHeight * (1 / Math.pow(scale.toDouble(), 2.0)) > IMAGE_MAX_SIZE) {
            scale++
        }

        var b: Bitmap? = null
        stream = mContentResolver.openInputStream(uri)
        if (scale > 1) {
            scale--
            // scale to max possible inSampleSize that still yields an image
            // larger than target
            o = BitmapFactory.Options()
            o.inSampleSize = scale
            b = BitmapFactory.decodeStream(stream, null, o)

            // resize to desired dimensions
            val height = b!!.height
            val width = b.width

            val y = Math.sqrt(IMAGE_MAX_SIZE / (width.toDouble() / height))
            val x = y / height * width

            val scaledBitmap = Bitmap.createScaledBitmap(b, x.toInt(),
                    y.toInt(), true)
            b.recycle()
            b = scaledBitmap

            System.gc()
        } else {
            b = BitmapFactory.decodeStream(stream)
        }
        stream!!.close()

        return b
    } catch (e: IOException) {
        Log.e("BitmapUtils", e.message, e)
        return null
    }
}


