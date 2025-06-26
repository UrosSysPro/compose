package net.systemvi.configurator.components.tester

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.sound.midi.MidiChannel
import javax.sound.midi.MidiSystem
import javax.sound.midi.Synthesizer


@Composable fun SoundTest() {

    val scope = rememberCoroutineScope()
    var synth: Synthesizer? by remember { mutableStateOf(null) }
    var channels:Array<MidiChannel?>? by remember { mutableStateOf(null) }
    var down by remember { mutableStateOf(false) }
    LaunchedEffect(Unit){
        synth = MidiSystem.getSynthesizer()
        synth?.open()
        channels = synth?.getChannels()
    }
    OutlinedButton(
        onClick = {scope.launch {
            if(down){
                down = false
                channels?.get(0)!!.noteOn(60, 93)
            }else{
                down=true
                channels?.get(0)!!.noteOff(60)
            }
        }}
    ){
        Text("click")
    }
}