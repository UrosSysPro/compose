package net.systemvi.configurator.data

import arrow.core.getOrElse
import arrow.core.right
import arrow.core.toOption
import net.systemvi.configurator.model.*

@OptIn(ExperimentalStdlibApi::class)
fun defaultKeymaps()=listOf(
    {
        val row0 = "` 1 2 3 4 5 6 7 8 9 0 - = 8:3:0 44"
        val row1 = "Tab q w e r t y u i o p [ ] \\"
        val row2 = "Caps a s d f g h j k l ; ' Enter"
        val row3 = "Shift z x c v b n m , . / Shift"
        val row4 = "Ctrl Win Alt Space Fn Win Alt Ctrl"
        val rows = listOf(row0, row1, row2, row3, row4).map { it.uppercase() }

        val keymap = KeyMap("Keyboard 60", rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).flatMap { (key, i) ->
                if(key.isEmpty())
                    emptyList<Keycap>()
                else
                listOf(Keycap(listOf(
                    Key(key[0].code.toByte(),key).right()
                )))
            }
        })
        keymap
            .setKeyWidth(0,keymap.keycaps[0].size-1, KeycapWidth.SIZE_2U)   //backspace

            .setKeyWidth(1,0, KeycapWidth.SIZE_15U)                        //tab
            .setKeyWidth(1,keymap.keycaps[1].size-1, KeycapWidth.SIZE_15U) //backslash

            .setKeyWidth(2,0, KeycapWidth.SIZE_175U)                        //caps lock
            .setKeyWidth(2,keymap.keycaps[2].size-1, KeycapWidth.SIZE_225U) //enter

            .setKeyWidth(3,0, KeycapWidth.SIZE_225U)                        //left shift
            .setKeyWidth(3,keymap.keycaps[3].size-1, KeycapWidth.SIZE_275U) //right shift

            .setKeyWidth(4,0, KeycapWidth.SIZE_125U)                        //left ctrl
            .setKeyWidth(4,1, KeycapWidth.SIZE_125U)                        //left win
            .setKeyWidth(4,2, KeycapWidth.SIZE_125U)                        //left alt
            .setKeyWidth(4,3, KeycapWidth.SIZE_625U)                        //space
            .setKeyWidth(4,4, KeycapWidth.SIZE_125U)                        //fn
            .setKeyWidth(4,5, KeycapWidth.SIZE_125U)                        //right alt
            .setKeyWidth(4,6, KeycapWidth.SIZE_125U)                        //right win
            .setKeyWidth(4,7, KeycapWidth.SIZE_125U)                        //right ctrl
    }(),
    {
        val rows = listOf(
            "Esc F1 F2 F3 F4 F5 F6 F7 F8 F9 F10 F11 F12",
            "` 1 2 3 4 5 6 7 8 9 0 - = Back Pg.Up",
            "Tab q w e r t y u i o p [ ] \\ Pg.Down",
            "Caps a s d f g h j k l ; ' Enter Home",
            "Shift z x c v b n m , . / Shift Pause",
            "Ctrl Win Alt Space Fn Win Alt Ctrl Idk",
        ).map { it.uppercase() }

        val keymap = KeyMap("Keyboard 75", rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).flatMap { (key, i) ->
                if(key.isEmpty())
                    emptyList<Keycap>()
                else
                    listOf(Keycap(listOf(
                        Key(key[0].code.toByte(),key).right()
                    )))
            }
        })
        keymap
            .setKeyWidth(1,keymap.keycaps[1].size-2, KeycapWidth.SIZE_2U)   //backspace

            .setKeyWidth(2,0, KeycapWidth.SIZE_15U)                        //tab
            .setKeyWidth(2,keymap.keycaps[2].size-2, KeycapWidth.SIZE_15U) //backslash

            .setKeyWidth(3,0, KeycapWidth.SIZE_175U)                        //caps lock
            .setKeyWidth(3,keymap.keycaps[3].size-2, KeycapWidth.SIZE_225U) //enter

            .setKeyWidth(4,0, KeycapWidth.SIZE_225U)                        //left shift
            .setKeyWidth(4,keymap.keycaps[4].size-2, KeycapWidth.SIZE_275U) //right shift

            .setKeyWidth(5,0, KeycapWidth.SIZE_125U)                        //left ctrl
            .setKeyWidth(5,1, KeycapWidth.SIZE_125U)                        //left win
            .setKeyWidth(5,2, KeycapWidth.SIZE_125U)                        //left alt
            .setKeyWidth(5,3, KeycapWidth.SIZE_625U)                        //space
            .setKeyWidth(5,4, KeycapWidth.SIZE_125U)                        //fn
            .setKeyWidth(5,5, KeycapWidth.SIZE_125U)                        //right alt
            .setKeyWidth(5,6, KeycapWidth.SIZE_125U)                        //right win
            .setKeyWidth(5,7, KeycapWidth.SIZE_125U)                        //right ctrl
    }(),
    {
        val rows = listOf(
            "B1:0:0 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE CF D0",
            "` 1 2 3 4 5 6 7 8 9 0 - = B2:4:0 D3 DB DC DD DE",
            "B3:2:0 q w e r t y u i o p [ ] 5C:2:0 D4 D5 D6 E7 E8 E9 DF",
            "C1:3:0 a s d f g h j k l ; ' B0:5:0 E4 E5 E6",
            "81:5:0 z x c v b n m , . / 85:6:0 DA E1 E2 E3 E0:0:1",
            "80:1:0 83:1:0 82:1:0 20:7:0 86:1:0 87:1:0 ED:1:0 84:1:0 D8 D9 D7 EA:4:0 .",
        ).map{it.uppercase()}

        val keymap = KeyMap("Keyboard 100", rows.map { row ->
            row.split(" ").map { key ->
                when{
                    key.split(":").size == 1 ->
                        if(key.length == 1){
                            Keycap(
                                listOf(
                                    allKeys.find{it.value == key[0].code.toByte()}.toOption().getOrElse{allKeys[0]}.right(),
                                )
                            )
                        }else {
                            Keycap(
                                listOf(
                                    allKeys.find { it.value == key.hexToByte() }.toOption().getOrElse { allKeys[0] }
                                        .right(),
                                )
                            )
                        }
                    key.split(":").size == 3 ->
                        Keycap(
                        listOf(
                            allKeys.find{it.value == key.split(":")[0].hexToByte()}.toOption().getOrElse{allKeys[0]}.right(),
                        ),
                        width = KeycapWidth.entries[key.split(":")[1].toInt()],
                        height = KeycapHeight.entries[key.split(":")[2].toInt()]
                    )
                    else -> Keycap(
                        listOf(allKeys[0].right())
                    )
                }
            }
        }
        )
        keymap
    }(),
)