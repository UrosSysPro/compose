package net.systemvi.configurator.components.tester

import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapNameComponent
import net.systemvi.configurator.components.common.keycaps.*
import net.systemvi.configurator.model.Key
import javax.sound.midi.MidiChannel
import javax.sound.midi.MidiSystem
import javax.sound.midi.Synthesizer

class TesterPageViewModel : ViewModel() {

    var currentlyDownKeys by mutableStateOf(emptySet<Key>())
    var wasDownKeys by mutableStateOf(emptySet<Key>())
    val focusRequester by mutableStateOf(FocusRequester())
    var selectedKeycap by mutableStateOf(FlatKeycap)
    var muteOn by mutableStateOf(false)
    var selectedInstrument by mutableStateOf(0)
    var showBottomSheet by mutableStateOf(false)

    var instruments: List<Pair<String, Int>> = listOf(
        Pair("Acoustic Grand Piano", 0),
        Pair("Nylon Acoustic Guitar", 24),
        Pair("Violin", 40),
        Pair("Trumpet", 56),
        Pair("Flute", 73),
        Pair("Synth Drum", 118)
    )

    fun resetKeys(){
        currentlyDownKeys = emptySet()
        wasDownKeys = emptySet()
        channels?.get(0)?.allNotesOff()
        focusRequester.requestFocus()
    }
    val synth:Synthesizer? = MidiSystem.getSynthesizer().apply { open()}

    val channels:Array<MidiChannel>? = synth?.getChannels()

    val allKeycaps: List<Pair<KeycapNameComponent, KeycapComponent>> = listOf(
        Pair(FlatKeycapName, FlatKeycap),
        Pair(ElevatedKeycapName,ElevatedKeycap),
        Pair(RGBWaveKeycapName, RGBWaveKeycap)
    )

    @Composable
    fun noteEffect(currentlyDown: Boolean, note: Int) {
        val channel = channels?.get(0)
        channel?.programChange(selectedInstrument)
        if (!muteOn) {
            LaunchedEffect(currentlyDown) {
                val velocity = 1000
                if (currentlyDown) {
                    println(note)
                    channel?.noteOn(note, velocity)
                } else {
                    channel?.noteOff(note, velocity)
                }
            }
        }
    }

}