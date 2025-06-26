package net.systemvi.configurator.data

import arrow.core.right
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapWidth
import net.systemvi.configurator.model.setKeyWidth

val defaultKeymaps=listOf(
    {
        val row0 = "` 1 2 3 4 5 6 7 8 9 0 - = Back"
        val row1 = "Tab q w e r t y u i o p [ ] \\"
        val row2 = "Caps a s d f g h j k l ; ' Enter"
        val row3 = "Shift z x c v b n m , . / Shift"
        val row4 = "Ctrl Win Alt Space Fn Win Alt Ctrl"
        val rows = listOf(row0, row1, row2, row3, row4)

        var keymap = KeyMap("keyboard 60", rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).map { (key, i) ->
                Keycap(listOf(
                    Key(key[0].code.toByte(),key).right()
                ))
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
        )

        var keymap = KeyMap("keyboard 75", rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).map { (key, i) ->
                Keycap(listOf(
                    Key(key[0].code.toByte(),key).right()
                ))
            }
        })
        keymap
            .setKeyWidth(1,keymap.keycaps[0].size-1, KeycapWidth.SIZE_2U)   //backspace

            .setKeyWidth(2,0, KeycapWidth.SIZE_15U)                        //tab
            .setKeyWidth(2,keymap.keycaps[1].size-1, KeycapWidth.SIZE_15U) //backslash

            .setKeyWidth(3,0, KeycapWidth.SIZE_175U)                        //caps lock
            .setKeyWidth(3,keymap.keycaps[2].size-1, KeycapWidth.SIZE_225U) //enter

            .setKeyWidth(4,0, KeycapWidth.SIZE_225U)                        //left shift
            .setKeyWidth(4,keymap.keycaps[3].size-1, KeycapWidth.SIZE_275U) //right shift

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
            "Esc F1 F2 F3 F4 F5 F6 F7 F8 F9 F10 F11 F12",
            "` 1 2 3 4 5 6 7 8 9 0 - = Back Pg.Up Num.Lock / * -",
            "Tab q w e r t y u i o p [ ] \\ Pg.Down 7 8 9  +",
            "Caps a s d f g h j k l ; ' Enter Home 4 5 6 ",
            "Shift z x c v b n m , . / Shift Pause 1 2 3 Num.Enter",
            "Ctrl Win Alt Space Fn Win Alt Ctrl Idk 0 Num.Del",
        )

        var keymap = KeyMap("keyboard 75", rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).map { (key, i) ->
                Keycap(listOf(
                    Key(key[0].code.toByte(),key).right()
                ))
            }
        })
        keymap
            .setKeyWidth(1,keymap.keycaps[0].size-1, KeycapWidth.SIZE_2U)   //backspace

            .setKeyWidth(2,0, KeycapWidth.SIZE_15U)                        //tab
            .setKeyWidth(2,keymap.keycaps[1].size-1, KeycapWidth.SIZE_15U) //backslash

            .setKeyWidth(3,0, KeycapWidth.SIZE_175U)                        //caps lock
            .setKeyWidth(3,keymap.keycaps[2].size-1, KeycapWidth.SIZE_225U) //enter

            .setKeyWidth(4,0, KeycapWidth.SIZE_225U)                        //left shift
            .setKeyWidth(4,keymap.keycaps[3].size-1, KeycapWidth.SIZE_275U) //right shift

            .setKeyWidth(5,0, KeycapWidth.SIZE_125U)                        //left ctrl
            .setKeyWidth(5,1, KeycapWidth.SIZE_125U)                        //left win
            .setKeyWidth(5,2, KeycapWidth.SIZE_125U)                        //left alt
            .setKeyWidth(5,3, KeycapWidth.SIZE_625U)                        //space
            .setKeyWidth(5,4, KeycapWidth.SIZE_125U)                        //fn
            .setKeyWidth(5,5, KeycapWidth.SIZE_125U)                        //right alt
            .setKeyWidth(5,6, KeycapWidth.SIZE_125U)                        //right win
            .setKeyWidth(5,7, KeycapWidth.SIZE_125U)                        //right ctrl
    }(),
)