package ru.korobeynikov.p04basiclayoutsrowcolumnbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource

@Composable
fun HomeScreen() =
    Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.large_padding))) {
        Text(text = stringResource(id = R.string.home_screen_title))
        Text(text = stringResource(id = R.string.home_screen_description))
    }