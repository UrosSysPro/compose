package net.systemvi.configurator.components.tester

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.systemvi.configurator.components.tester.Grid

@Composable fun TesterPage(modifier: Modifier) {
    Column(
        modifier = Modifier.fillMaxSize().then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box() {
            Grid(
                listOf(
                    "Esc %,1.0,1.0 F1 F2 F3 F4 %,0.5,1.0 F5 F6 F7 F8 %,0.5,1.0 F9 F10 F11 F12 %,0.25,1.0 Print Scroll Pause %,0.25,1.0 Sleep Mute Vol- Vol+",
                    "%,1.0,0.25",
                    "` 1 2 3 4 5 6 7 8 9 0 - = %Backspace,2.0,1.0 %,0.25,1.0 Ins Home PgUp %,0.25,1.0 N.Lock / * -",
                    "%Tab,1.5,1.0 q w e r t y u i o p [ ] %\\,1.5,1.0 %,0.25,1.0 Del End PgDn %,0.25,1.0 7 8 9 %+,1.0,2.0",
                    "%Caps\\sLock,1.75,1.0 a s d f g h j k l ; ' %Enter,2.25,1.0 %,3.5,1.0 4 5 6",
                    "%Left\\sShift,2.25,1.0 z x c v b n m , . / %Right\\sShift,2.75,1.0 %,1.25,1.0 ↑ %,1.25,1.0 1 2 3 %N.Enter,1.0,2.0",
                    "%Left\\sCtrl,1.25,1.0 %Left\\sWin,1.25,1.0 %Left\\sAlt,1.25,1.0 %Space,6.25,1.0 %Right\\sAlt,1.25,1.0 %Right\\sWin,1.25,1.0 %Menu,1.25,1.0 %Right\\sCtrl,1.25,1.0 %,0.25,1.0 ← ↓ → %,0.25,1.0 %0,2.0,1.0 .",
                )
            )
        }
    }

}