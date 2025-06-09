package net.systemvi.configurator.data

import net.systemvi.configurator.model.Key

private val alphabet="q w e r t y u i o p a s d f g h j k l z x c v b n m"
private val numbers="1 2 3 4 5 6 7 8 9 0"
private val symbols="` - = [ ] ; ' \\ , . /"
val alphabetKeys: List<Key> = alphabet.split(" ").map { Key(it[0].code.toByte(),it) }.plus(Key(' '.code.toByte(),"Space"))
val numberKeys: List<Key> = numbers.split(" ").map { Key(it[0].code.toByte(),it) }
val symbolKeys: List<Key> = symbols.split(" ").map { Key(it[0].code.toByte(),it) }
val fKeys:List<Key> = TODO("implement f keys")
val numpadKeys:List<Key> = TODO("implement numpad keys")
val uiKeys:List<Key> = TODO("implement ui keys")
val mediaControlKeys:List<Key> = TODO("implement media keys")
val movementKeys:List<Key> =TODO("implement movement keys")
val allKeys:List<Key> = listOf(
    alphabetKeys,
    numberKeys,
    symbolKeys,
//    fKeys,
//    numpadKeys,
//    uiKeys,
//    mediaControlKeys,
//    movementKeys
).flatten()
