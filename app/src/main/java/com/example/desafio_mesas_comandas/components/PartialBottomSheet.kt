import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PartialBottomSheet(onDismiss: () -> Unit) {
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
//
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        sheetState = sheetState
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("Mesa/Comanda")
//            Text("Balcão")
//        }
//    }
//}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewOrderBottomSheet(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    //val scope = rememberCoroutineScope()

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                content = content
            )
        }
    }
}