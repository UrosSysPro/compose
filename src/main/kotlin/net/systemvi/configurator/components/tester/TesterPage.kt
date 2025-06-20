package net.systemvi.configurator.components.tester

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.nativeKeyLocation
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import arrow.core.toOption
import net.systemvi.configurator.data.allKeys

@Composable fun TesterPage(modifier: Modifier) {
    //    Column(
//        modifier = Modifier.fillMaxSize().then(modifier),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
    fun key125(name: String) = "%$name,1.25,1.0".replace(" ", "\\s")
    fun key15(name: String) = "%$name,1.5,1.0"
    fun key175(name: String) = "%$name,1.75,1.0"
    val one = "%,1.0,1.0"
    val half = "%,0.5,1.0"
    val quarter = "%,0.25,1.0"
    val halfRow = "%,1.0,0.25"
    val focusRequester by remember{ mutableStateOf(FocusRequester()) }

    Box(Modifier.onKeyEvent{
        if(it.type!= KeyEventType.Unknown){
            val key= allKeys.find { key-> key.value.toInt() == it.key.nativeKeyCode.toInt() }.toOption()
            key.onSome { key->
                println("type: ${it.type} key: ${key.name}")
            }
//            println("""
//                |native code: ${it.key.nativeKeyCode}
//                |key code: ${it.key.keyCode}
//                |native location: ${it.key.nativeKeyLocation}
//            """.trimIndent())
            true
        }else false
    }
        .focusRequester(focusRequester)
        .focusable()) {
        Column(
            modifier = Modifier.fillMaxSize().then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Grid(
                listOf(
                    "Esc $one F1 F2 F3 F4 $half F5 F6 F7 F8 $half F9 F10 F11 F12 $quarter Print Scroll Pause $quarter Sleep Mute Vol- Vol+",
                    halfRow,
                    "` 1 2 3 4 5 6 7 8 9 0 - = %Backspace,2.0,1.0 $quarter Ins Home PgUp $quarter N.Lock / * -",
                    "%Tab,1.5,1.0 q w e r t y u i o p [ ] %\\,1.5,1.0 %,0.25,1.0 Del End PgDn %,0.25,1.0 7 8 9 %+,1.0,2.0",
                    "%Caps\\sLock,1.75,1.0 a s d f g h j k l ; ' %Enter,2.25,1.0 %,3.5,1.0 4 5 6",
                    "%Left\\sShift,2.25,1.0 z x c v b n m , . / %Right\\sShift,2.75,1.0 %,1.25,1.0 ↑ %,1.25,1.0 1 2 3 %N.Enter,1.0,2.0",
                    "${key125("Left Ctrl")} ${key125("Left Win")} ${key125("Left Alt")} %Space,6.25,1.0 ${key125("Right Alt")} ${key125("Right Win")} ${key125("Menu")} %Right\\sCtrl,1.25,1.0 %,0.25,1.0 ← ↓ → %,0.25,1.0 %0,2.0,1.0 .",
                ),
                Keycap
            )
        }
    }
    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }
}