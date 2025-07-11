package net.systemvi.configurator.data

import net.systemvi.configurator.model.Key

private val alphabetSmall="q w e r t y u i o p a s d f g h j k l z x c v b n m"
private val alphabetBig=alphabetSmall.uppercase()
private val numbers="1 2 3 4 5 6 7 8 9 0"

val passKey=Key(0.toByte(),"Pass")

val alphabetKeys: List<Key> = alphabetSmall.split(" ").map { Key(it[0].code.toByte(),it,it[0].code.toLong()) }
    .plus(Key(' '.code.toByte(),"Space",' '.code.toLong()))
    .plus(passKey)
//    .plus(Key('\n'.code.toByte(),"Enter"))

val alphabetBigKeys=alphabetBig.split(" ").map { Key(it[0].code.toByte(),it,it[0].code.toLong()) }

val numberKeys: List<Key> = numbers.split(" ").map { Key(it[0].code.toByte(),it,it[0].code.toLong()) }

val symbolKeys: List<Key> = emptyList<Key>()
    .plus(Key('`' .code.toByte(),"`",192))
    .plus(Key('-' .code.toByte(),"-",45))
    .plus(Key('=' .code.toByte(),"=",61))
    .plus(Key('\\' .code.toByte(),"\\",92))
    .plus(Key('.' .code.toByte(),".",46))
    .plus(Key('/' .code.toByte(),"/",47))
    .plus(Key(',' .code.toByte(),",",44))
    .plus(Key('[' .code.toByte(),"[",91))
    .plus(Key(']' .code.toByte(),"]",93))
    .plus(Key(';' .code.toByte(),";",59))
    .plus(Key('\'' .code.toByte(),"'",222))

val fKeys:List<Key> = listOf(
    Key(0xC2.toByte(),"F1", 112),
    Key(0xC3.toByte(),"F2", 113),
    Key(0xC4.toByte(),"F3", 114),
    Key(0xC5.toByte(),"F4", 115),
    Key(0xC6.toByte(),"F5", 116),
    Key(0xC7.toByte(),"F6", 117),
    Key(0xC8.toByte(),"F7",118),
    Key(0xC9.toByte(),"F8", 119),
    Key(0xCA.toByte(),"F9", 120),
    Key(0xCB.toByte(),"F10",121),
    Key(0xCC.toByte(),"F11", 122),
    Key(0xCD.toByte(),"F12", 123),
    Key(0xF0.toByte(),"F13", 124),
    Key(0xF1.toByte(),"F14", 125),
    Key(0xF2.toByte(),"F15", 126),
    Key(0xF3.toByte(),"F16", 127),
    Key(0xF4.toByte(),"F17", 128),
    Key(0xF5.toByte(),"F18", 129),
    Key(0xF6.toByte(),"F19", 130),
    Key(0xF7.toByte(),"F20", 131),
    Key(0xF8.toByte(),"F21", 132),
    Key(0xF9.toByte(),"F22", 133),
    Key(0xFA.toByte(),"F23", 134),
    Key(0xFB.toByte(),"F24", 135),
)
val numpadKeys:List<Key> = listOf(
    Key(0xDB.toByte(),"Num. Lock", 144),
    Key(0xDC.toByte(),"Num. /", 111),
    Key(0xDD.toByte(),"Num. *", 106),
    Key(0xDE.toByte(),"Num. -", 109),
    Key(0xDF.toByte(),"Num. +", 107),
    Key(0xE0.toByte(),"Num. Enter", 10),
    Key(0xE1.toByte(),"Num. 1", 35),
    Key(0xE2.toByte(),"Num. 2", 225),
    Key(0xE3.toByte(),"Num. 3", 34),
    Key(0xE4.toByte(),"Num. 4", 226),
    Key(0xE5.toByte(),"Num. 5", 65368),
    Key(0xE6.toByte(),"Num. 6", 227),
    Key(0xE7.toByte(),"Num. 7", 36),
    Key(0xE8.toByte(),"Num. 8", 224),
    Key(0xE9.toByte(),"Num. 9", 33),
    Key(0xEA.toByte(),"Num. 0", 155),
    Key(0xEB.toByte(),"Num. .", 127),
)
val modifierKeys:List<Key> = listOf(
    Key(0x80.toByte(),"Left Ctrl", 17),
    Key(0x81.toByte(),"Left Shift", 16),
    Key(0x82.toByte(),"Left Alt", 18),
    Key(0x83.toByte(),"Left Win", 524),
    Key(0x84.toByte(),"Right Ctrl", 17),
    Key(0x85.toByte(),"Right Shift", 16),
    Key(0x86.toByte(),"Right Alt", 18),
    Key(0x87.toByte(),"Right Win"),
)
val miscKeys:List<Key> = listOf(
    Key(0xDA.toByte(),"Up", 38),
    Key(0xD9.toByte(),"Down", 40),
    Key(0xD8.toByte(),"Left", 37),
    Key(0xD7.toByte(),"Right", 39),
    Key(0xB2.toByte(),"Backspace", 8),
    Key(0xB3.toByte(),"Tab", 9),
    Key(0xB0.toByte(),"Enter", 10),
    Key(0xED.toByte(),"Menu", 525),
    Key(0xB1.toByte(),"Esc", 27),
    Key(0xD1.toByte(),"Insert", 155),
    Key(0xD4.toByte(),"Delete", 127),
    Key(0xD3.toByte(),"Pg. Up", 33),
    Key(0xD6.toByte(),"Pg. Down", 34),
    Key(0xD2.toByte(),"Home", 36),
    Key(0xD5.toByte(),"End", 35),
    Key(0xC1.toByte(),"Caps Lock", 20),
//    Key(0xCE.toByte(),"Print Scr.", 44),
    Key(0xCE.toByte(),"Print Scr."),
    Key(0xCF.toByte(),"Scroll Lock", 145),
    Key(0xD0.toByte(),"Pause", 19),
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
    alphabetBigKeys,
    numberKeys,
    symbolKeys,
    fKeys,
    numpadKeys,
    modifierKeys,
    miscKeys,
    mediaKeys,
    alphabetKeys,
).flatten()