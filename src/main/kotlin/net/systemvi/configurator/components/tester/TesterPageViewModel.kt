package net.systemvi.configurator.components.tester

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapNameComponent
import net.systemvi.configurator.components.common.keycaps.ElevatedKeycap
import net.systemvi.configurator.components.common.keycaps.ElevatedKeycapName
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.common.keycaps.FlatKeycapName
import net.systemvi.configurator.components.common.keycaps.RGBWaveKeycap
import net.systemvi.configurator.components.common.keycaps.RGBWaveKeycapName
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

    val allKeycaps: List<Pair<KeycapNameComponent, KeycapComponent>> = listOf(
        Pair(FlatKeycapName, FlatKeycap),
        Pair(ElevatedKeycapName,ElevatedKeycap),
        Pair(RGBWaveKeycapName, RGBWaveKeycap)
    )

}