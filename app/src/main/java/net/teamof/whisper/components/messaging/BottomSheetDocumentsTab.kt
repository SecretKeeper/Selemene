package net.teamof.whisper.components.messaging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.Document

@ExperimentalFoundationApi
@Composable
fun BottomSheetDocumentsTab(documents: List<Document>) {
    LazyColumn(modifier = Modifier.padding(horizontal = 15.dp)) {
        itemsIndexed(documents) { _, document ->

            val mime: String
            val mimeColor: Color
            when (document.type) {
                "application/pdf" -> {
                    mime = "PDF"
                    mimeColor = Color(0xffef5350)
                }
                "application/doc" -> {
                    mime = "DOC"
                    mimeColor = Color(0xff2979ff)
                }
                "application/docx" -> {
                    mime = "DOCX"
                    mimeColor = Color(0xff2979ff)
                }
                "application/txt" -> {
                    mime = "TXT"
                    mimeColor = Color(0xff5153ff)
                }
                "application/xls" -> {
                    mime = "XLS"
                    mimeColor = Color(0xff26a69a)
                }
                "application/xlsx" -> {
                    mime = "XLSX"
                    mimeColor = Color(0xff26a69a)
                }
                "application/html" -> {
                    mime = "HTML"
                    mimeColor = Color(0xffff5722)
                }
                else -> {
                    mime = "Unknown"
                    mimeColor = Color.Gray
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 15.dp)
            ) {
                Text(
                    text = mime,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = mimeColor,
                    modifier = Modifier.padding(end = 24.dp),
                    letterSpacing = 2.sp
                )
                Column(Modifier.weight(1f)) {
                    Text(
                        text = document.name,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "276 KB",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
                Text(
                    text = "1th September 2012",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}