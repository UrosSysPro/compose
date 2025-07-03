package net.systemvi.configurator.components.tester

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keycaps.ElevatedKeycap
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.common.keycaps.RGBWaveKeycap
import net.systemvi.configurator.model.Key
import javax.sound.midi.MidiChannel
import javax.sound.midi.MidiSystem
import javax.sound.midi.Synthesizer

class TesterPageViewModel : ViewModel() {

    var currentlyDownKeys by mutableStateOf(emptySet<Key>())
    var wasDownKeys by mutableStateOf(emptySet<Key>())
    val focusRequester by mutableStateOf(FocusRequester())
    var selectedKeycap by mutableStateOf(FlatKeycap)

    fun resetKeys(){
        currentlyDownKeys = emptySet()
        wasDownKeys = emptySet()
        focusRequester.requestFocus()
    }
    val synth:Synthesizer? = MidiSystem.getSynthesizer().apply { open()}

    val channels:Array<MidiChannel>? = synth?.getChannels()

    val allKeycaps: List<Pair<String, KeycapComponent>> = listOf(
        Pair("Flat Keycap", FlatKeycap),
        Pair("Elevated Keycap",ElevatedKeycap),
        Pair("RGB Wave Keycap", RGBWaveKeycap)
    )

}