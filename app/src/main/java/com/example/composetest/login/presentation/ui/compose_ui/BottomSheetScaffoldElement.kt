package kz.nomad.mobile.android.utils.composeUI

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun BottomSheetScaffoldElement(
    modifier: Modifier = Modifier,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContent: @Composable () -> Unit,
    backGroundContent: @Composable () -> Unit
) {
    BottomSheetScaffold(
        sheetShape = MaterialTheme.shapes.medium,
        modifier = modifier.wrapContentHeight(),
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor=sheetBackgroundColor,
        sheetContent = {
            sheetContent()
        }, sheetPeekHeight = 0.dp
    ) {
       backGroundContent()
    }
}


