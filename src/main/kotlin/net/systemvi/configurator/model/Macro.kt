package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable


@Serializable
enum class MacroActionType(val id:Int){
    KEY_UP(0),KEY_DOWN(1);
}
@Serializable
@optics data class MacroAction(val key:Key,val action:MacroActionType){companion object}
@Serializable
@optics data class Macro(val name:String,val actions:List<MacroAction>){companion object}

fun Macro.setName(name:String): Macro = Macro.name.set(this,name)
