package net.systemvi.configurator.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Page
@Serializable
object ConfigurePage:Page()
@Serializable
object TesterPage:Page()
@Serializable
object DesignPage:Page()
@Serializable
object SettingsPage:Page()
@Serializable
object ComponentPage:Page()
@Serializable
object SerialApiTestPage:Page()
