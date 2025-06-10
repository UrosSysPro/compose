package net.systemvi.configurator.data

import net.systemvi.configurator.model.Key

private val alphabet="q w e r t y u i o p a s d f g h j k l z x c v b n m"
private val numbers="1 2 3 4 5 6 7 8 9 0"
private val symbols="` - = [ ] ; ' \\ , . /"
val alphabetKeys: List<Key> = alphabet.split(" ").map { Key(it[0].code.toByte(),it) }.plus(Key(' '.code.toByte(),"Space"))
val numberKeys: List<Key> = numbers.split(" ").map { Key(it[0].code.toByte(),it) }
val symbolKeys: List<Key> = symbols.split(" ").map { Key(it[0].code.toByte(),it) } + Key('\n'.code.toByte(),"Enter")
val fKeys:List<Key> = listOf(
    Key(0xC2.toByte(),"F1"),
    Key(0xC3.toByte(),"F2"),
    Key(0xC4.toByte(),"F3"),
    Key(0xC5.toByte(),"F4"),
    Key(0xC6.toByte(),"F5"),
    Key(0xC7.toByte(),"F6"),
    Key(0xC8.toByte(),"F7"),
    Key(0xC9.toByte(),"F8"),
    Key(0xCA.toByte(),"F9"),
    Key(0xCB.toByte(),"F10"),
    Key(0xCC.toByte(),"F11"),
    Key(0xCD.toByte(),"F12"),
    Key(0xF0.toByte(),"F13"),
    Key(0xF1.toByte(),"F14"),
    Key(0xF2.toByte(),"F15"),
    Key(0xF3.toByte(),"F16"),
    Key(0xF4.toByte(),"F17"),
    Key(0xF5.toByte(),"F18"),
    Key(0xF6.toByte(),"F19"),
    Key(0xF7.toByte(),"F20"),
    Key(0xF8.toByte(),"F21"),
    Key(0xF9.toByte(),"F22"),
    Key(0xFA.toByte(),"F23"),
    Key(0xFB.toByte(),"F24"),
)
val numpadKeys:List<Key> = listOf(
    Key(0xDB.toByte(),"KEY_NUM_LOCK"),
    Key(0xDC.toByte(),"KEY_KP_SLASH"),
    Key(0xDD.toByte(),"KEY_KP_ASTERISK"),
    Key(0xDE.toByte(),"KEY_KP_MINUS"),
    Key(0xDF.toByte(),"KEY_KP_PLUS"),
    Key(0xE0.toByte(),"KEY_KP_ENTER"),
    Key(0xE1.toByte(),"KEY_KP_1"),
    Key(0xE2.toByte(),"KEY_KP_2"),
    Key(0xE3.toByte(),"KEY_KP_3"),
    Key(0xE4.toByte(),"KEY_KP_4"),
    Key(0xE5.toByte(),"KEY_KP_5"),
    Key(0xE6.toByte(),"KEY_KP_6"),
    Key(0xE7.toByte(),"KEY_KP_7"),
    Key(0xE8.toByte(),"KEY_KP_8"),
    Key(0xE9.toByte(),"KEY_KP_9"),
    Key(0xEA.toByte(),"KEY_KP_0"),
    Key(0xEB.toByte(),"KEY_KP_DOT"),
)
val modifierKeys:List<Key> = listOf(
    Key(0x80.toByte(),"KEY_LEFT_CTRL"),
    Key(0x81.toByte(),"KEY_LEFT_SHIFT"),
    Key(0x82.toByte(),"KEY_LEFT_ALT"),
    Key(0x83.toByte(),"KEY_LEFT_GUI"),
    Key(0x84.toByte(),"KEY_RIGHT_CTRL"),
    Key(0x85.toByte(),"KEY_RIGHT_SHIFT"),
    Key(0x86.toByte(),"KEY_RIGHT_ALT"),
    Key(0x87.toByte(),"KEY_RIGHT_GUI"),
)
val miscKeys:List<Key> = listOf(
    Key(0xDA.toByte(),"KEY_UP_ARROW"),
    Key(0xD9.toByte(),"KEY_DOWN_ARROW"),
    Key(0xD8.toByte(),"KEY_LEFT_ARROW"),
    Key(0xD7.toByte(),"KEY_RIGHT_ARROW"),
    Key(0xB2.toByte(),"KEY_BACKSPACE"),
    Key(0xB3.toByte(),"KEY_TAB"),
    Key(0xB0.toByte(),"KEY_RETURN"),
    Key(0xED.toByte(),"KEY_MENU"),
    Key(0xB1.toByte(),"KEY_ESC"),
    Key(0xD1.toByte(),"KEY_INSERT"),
    Key(0xD4.toByte(),"KEY_DELETE"),
    Key(0xD3.toByte(),"KEY_PAGE_UP"),
    Key(0xD6.toByte(),"KEY_PAGE_DOWN"),
    Key(0xD2.toByte(),"KEY_HOME"),
    Key(0xD5.toByte(),"KEY_END"),
    Key(0xC1.toByte(),"KEY_CAPS_LOCK"),
    Key(0xCE.toByte(),"KEY_PRINT_SCREEN"),
    Key(0xCF.toByte(),"KEY_SCROLL_LOCK"),
    Key(0xD0.toByte(),"KEY_PAUSE"),
)
val mediaKeys:List<Key> = listOf(
    Key(0x00CD.toByte(),"KEY_PLAY_PAUSE"),
    Key(0x00B5.toByte(),"KEY_SCAN_NEXT"),
    Key(0x00B6.toByte(),"KEY_SCAN_PREVIOUS"),
    Key(0x00B7.toByte(),"KEY_STOP"),
    Key(0x00E0.toByte(),"KEY_VOLUME"),
    Key(0x00E2.toByte(),"KEY_MUTE"),
    Key(0x00E3.toByte(),"KEY_BASS"),
    Key(0x00E4.toByte(),"KEY_TREBLE"),
    Key(0x00E5.toByte(),"KEY_BASS_BOOST"),
    Key(0x00E9.toByte(),"KEY_VOLUME_INCREMENT"),
    Key(0x00EA.toByte(),"KEY_VOLUME_DECREMENT"),
    Key(0x0152.toByte(),"KEY_BASS_INCREMENT"),
    Key(0x0153.toByte(),"KEY_BASS_DECREMENT"),
    Key(0x0154.toByte(),"KEY_TREBLE_INCREMENT"),
    Key(0x0155.toByte(),"KEY_TREBLE_DECREMENT"),
)

val allKeys:List<Key> = listOf(
    alphabetKeys,
    numberKeys,
    symbolKeys,
    fKeys,
    numpadKeys,
    modifierKeys,
    miscKeys,
    mediaKeys,
).flatten()