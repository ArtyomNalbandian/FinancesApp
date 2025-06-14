package com.example.financesapp.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financesapp.ui.theme.Green

@Composable
fun AddButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Green,
        contentColor = Color.White,
        shape = CircleShape,
        modifier = modifier.padding(end = 16.dp, bottom = 14.dp)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}


@Preview
@Composable
fun AddButtonPreview() {
    AddButton(onClick = {})
}