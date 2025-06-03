package net.systemvi.configurator.components.keyboard_layout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Key(val value:String,var size:Float)

@Composable fun Keycap(key:Key,size1U:Float=50f){
    Box(
        modifier = Modifier
            .size((size1U*key.size).dp,50.dp)
            .padding(2.dp)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .border(
                    BorderStroke(2.dp,MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(10.dp)
                )
        ){
            Text(key.value)
        }
    }
}

@Composable
fun KeyboardLayoutGrid(){
    val row0="` 1 2 3 4 5 6 7 8 9 0 - = Back"
    val row1="Tab q w e r t y u i o p [ ] \\"
    val row2="Caps a s d f g h j k l ; ' Enter"
    val row3="Shift z x c v b n m , . / Shift"
    val row4="Ctrl Win Alt Space Fn Win Alt Ctrl"
    val rows=listOf(row0,row1,row2,row3,row4)

    val keys= rows.map { row->
        row.split(" ").map { key->
            Key(key,1.0f)
        }
    }

    keys[0].last().size=2f

    keys[1][0].size=1.25f // tab
    keys[1].last().size=1.75f // tab

    keys[2][0].size=1.5f // caps
    keys[2].last().size=2.5f // enter

    keys[3][0].size=2f  // left shift
    keys[3].last().size=3f  // left shift

    keys[4][0].size=1.25f //ctrl
    keys[4][1].size=1.25f //win
    keys[4][2].size=1.25f //alt
    keys[4][3].size=6.25f //space
    keys[4][4].size=1.25f //fn
    keys[4][5].size=1.25f //win
    keys[4][6].size=1.25f //alt
    keys[4][7].size=1.25f //ctrl


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column{
            keys.forEach { row->
                Row{
                    row.forEach { key->
                        Keycap(key)
                    }
                }
            }
        }
    }
}