package kz.nomad.mobile.android.utils.composeUI

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


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


