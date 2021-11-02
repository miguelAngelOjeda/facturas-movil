package com.example.palermo.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.palermo.R
import kotlinx.android.synthetic.main.search_widget.view.*


class SearchWidget : FrameLayout {

    var label: CharSequence
        get() = textViewLabel.text
        set(value) {
            textViewLabel.text = value
        }

    var result: CharSequence?
        get() = textviewResult.text
        set(value) {
            textviewResult.text = value
        }

    var readOnly: Boolean
        get() = buttonSearch.visibility == View.GONE
        set(value) { buttonSearch.visibility = if (value) View.GONE else View.VISIBLE }

    private var callback: (() -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let {
            val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SearchWidget)
            init()
            label = obtainStyledAttributes.getString(R.styleable.SearchWidget_label).toString()
            obtainStyledAttributes.recycle()
        }
    }

    private fun setupView() {
        LayoutInflater.from(context).inflate(R.layout.search_widget, this)
    }

    private fun init() {
        setupView()
        textViewLabel.text = label
        textViewLabel.setOnClickListener { if (!readOnly) callback?.invoke() }
        buttonSearch.setOnClickListener { if (!readOnly) callback?.invoke() }
    }


    fun setOnSearch(searchCallback: () -> Unit) {
        callback = searchCallback
    }

    fun setString(value: String?) {
        if (value != null) {
            textviewResult.text = value
        }
    }
}