package net.systemvi.configurator.data

import arrow.core.getOrElse
import arrow.core.right
import arrow.core.toOption
import net.systemvi.configurator.model.*

@OptIn(ExperimentalStdlibApi::class)
private fun stringToKeyMap(name:String, rows:List<String>): KeyMap{
    val keymap = KeyMap(name, rows.map { row ->
        row.split(" ").map { key ->
            val splitKey = key.split(":")
            val keyItem = allKeys
                .find { it.value == if (splitKey[0].length == 1) splitKey[0][0].code.toByte() else splitKey[0].hexToByte() }
                .toOption()
                .getOrElse { allKeys[0] }.right()
            val width = KeycapWidth.entries[splitKey.getOrNull(1)?.toInt().toOption().getOrElse { 0 }]
            val height = KeycapHeight.entries[splitKey.getOrNull(2)?.toInt().toOption().getOrElse { 0 }]
            val padding = KeycapPadding(
                left = splitKey.getOrNull(3)?.toFloat().toOption().getOrElse { 0f },
                bottom = splitKey.getOrNull(4)?.toFloat().toOption().getOrElse { 0f }
            )
            Keycap(
                listOf(keyItem),
                width = width,
                height = height,
                padding = padding,
            )
        }
    })
    return keymap
}

fun defaultKeymaps()=listOf(
    stringToKeyMap("60% Keymap", listOf(
        "` 1 2 3 4 5 6 7 8 9 0 - = B2:4:0",
        "B3:2 q w e r t y u i o p [ ] 5C:2",
        "C1:3 a s d f g h j k l ; ' B0:5",
        "81:5 z x c v b n m , . / 85:6",
        "80:1 83:1 82:1 20:7 86:1 87:1 ED:1 84:1",
    ).map { it.uppercase() }),

    stringToKeyMap("75% Keymap", listOf(
        "B1 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE D1 D4",
        "` 1 2 3 4 5 6 7 8 9 0 - = B2:4 D2",
        "B3:2 q w e r t y u i o p [ ] 5C:2 D5",
        "C1:3 a s d f g h j k l ; ' B0:5 D3",
        "81:5 z x c v b n m , . / 85:3 DA D6",
        "80:1 83:1 82:1 20:7 86 87 84 D8 D9 D7",
    ).map { it.uppercase() }),

    stringToKeyMap("100% Keymap", listOf(
        "B1 C2:0:0:1:0.5 C3 C4 C5 C6:0:0:0.5 C7 C8 C9 CA:0:0:0.5 CB CC CD CE:0:0:0.25 CF D0",
        "` 1 2 3 4 5 6 7 8 9 0 - = B2:4:0 D1:0:0:0.25 D2 D3 DB:0:0:0.25 DC DD DE",
        "B3:2 q w e r t y u i o p [ ] 5C:2 D4:0:0:0.25 D5 D6 E7:0:0:0.25 E8 E9 DF:0:1",
        "C1:3 a s d f g h j k l ; ' B0:5 E4:0:0:3.5 E5 E6",
        "81:5 z x c v b n m , . / 85:6 DA:0:0:1.25 E1:0:0:1.25 E2 E3 E0:0:1",
        "80:1 83:1 82:1 20:7 86:1 87:1 ED:1 84:1 D8:0:0:0.25 D9 D7 EA:4:0:0.25 .",
    ).map { it.uppercase() })
)