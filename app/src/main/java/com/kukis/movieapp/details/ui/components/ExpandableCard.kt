package com.kukis.movieapp.details.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kukis.movieapp.ui.theme.whiteTitle

@Composable
fun ExpandableCard(title: String, content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { isExpanded = !isExpanded }
        .padding(16.dp)) {
        Row {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = whiteTitle,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow indicate Expandable",
                tint = whiteTitle
            )
        }
        AnimatedVisibility(visible = isExpanded) {
            content()
        }
    }
}