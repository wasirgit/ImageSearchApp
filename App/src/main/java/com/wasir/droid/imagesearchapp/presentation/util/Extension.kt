package com.wasir.droid.imagesearchapp.presentation.util


import android.icu.text.CompactDecimalFormat
import androidx.appcompat.widget.SearchView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })
    return query
}


fun MaterialTextView.prettyText(number: Int?) {
    val compactDecimalFormat: CompactDecimalFormat =
        CompactDecimalFormat.getInstance(
            Locale.getDefault(),
            CompactDecimalFormat.CompactStyle.SHORT
        )
    this.text = compactDecimalFormat.format(number)
}