package mohamedali.hamza.shoppingc24.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mohamedali.hamza.shoppingc24.R
import mohamedali.hamza.shoppingc24.ui.pages.ProductDetail

@Composable
fun SpacerHeight(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun SpacerWidth(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun EmptyBox() {
    Box {}
}

@Composable
fun EmptyData(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Default.Warning,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.then(modifier)
    ) {
        Icon(icon, contentDescription = "", tint = Color.Gray, modifier = Modifier.size(32.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
fun Center(content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        content()
    }
}
@Composable
fun ErrorProduct(modifier:Modifier = Modifier,retryAgain:()->Unit){
    Row(modifier = Modifier.then(modifier)
        .padding(12.dp)
        .height(96.dp)) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = "Error")
        SpacerWidth(width = 4.dp)
       Column {
           Text(text = "Probleme")
           Text(text = "Die Daten konnten nicht geladen werden")
           Button(onClick = retryAgain) {
               Text(text = "Neuladen")
           }
       }
    }
}
@Composable
fun Loading() {
    Column(
        modifier = Modifier
            .testTag("loading")
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(Modifier.testTag("progressIndicator"))
        Text(stringResource(id = R.string.loadingText), modifier = Modifier.testTag("loadingText"))

    }
}
@Composable
fun FooterProductOver(modifier: Modifier = Modifier,clickFooter:()->Unit) {
    Text(
        text = "„© 2016Check24",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.then(modifier).clickable {
            clickFooter()
        })

}