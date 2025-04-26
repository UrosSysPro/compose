import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

data class Link(val url:String)

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
	var checked by remember{ mutableStateOf(false) }
	
	val links=listOf(
		Link(url="link 1"),
		Link(url="link 2"),
		Link(url="link 3"),
		Link(url="link 4"),
		Link(url="link 5"),
	)

    MaterialTheme {

		Scaffold(
			topBar={
				TopAppBar(
					title={
						Text("this is a title")
					}
				)
			},
			content={padding->
				/*Switch(
					checked=checked,
					onCheckedChange={
						checked=it
					}
				)*/
			}
		)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
