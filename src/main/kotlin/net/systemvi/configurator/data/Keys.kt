package net.systemvi.configurator.data

import net.systemvi.configurator.model.Key

private val alphabet="q w e r t y u i o p a s d f g h j k l z x c v b n m"
private val numbers="1 2 3 4 5 6 7 8 9 0"
private val symbols="` - = [ ] ; ' \\ , . /"
val alphabetKeys: List<Key> = alphabet.split(" ").map { Key(it[0].code.toByte(),it) }.plus(Key(' '.code.toByte(),"Space"))
val numberKeys: List<Key> = numbers.split(" ").map { Key(it[0].code.toByte(),it) }
val symbolKeys: List<Key> = symbols.split(" ").map { Key(it[0].code.toByte(),it) }
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
//val numpadKeys:List<Key> = TODO("implement numpad keys")
//val uiKeys:List<Key> = TODO("implement ui keys")
//val mediaControlKeys:List<Key> = TODO("implement media keys")
//val movementKeys:List<Key> =TODO("implement movement keys")
val allKeys:List<Key> = listOf(
    alphabetKeys,
    numberKeys,
    symbolKeys,
    fKeys,
//    numpadKeys,
//    uiKeys,
//    mediaControlKeys,
//    movementKeys
).flatten()
