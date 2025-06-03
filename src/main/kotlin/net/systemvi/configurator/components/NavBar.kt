package net.systemvi.configurator.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(){
    TopAppBar(
        title={
            Row{
                TextButton(
                    onClick = { println("Configure")}
                ) {
                    Text("Configure")
                }
                TextButton(
                    onClick = { println("Keytester")}
                ) {
                    Text("KeyTester")
                }
                TextButton(
                    onClick = { println("Design")}
                ) {
                    Text("Design")
                }
            }

        }
    )
}