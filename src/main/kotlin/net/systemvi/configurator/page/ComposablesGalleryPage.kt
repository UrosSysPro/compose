package net.systemvi.configurator.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.getOrElse
import arrow.core.toOption
import io.github.classgraph.ClassGraph
import net.systemvi.configurator.utils.annotations.ComposablesGalleryItem
import java.lang.reflect.Method
import kotlin.time.measureTime


@Composable
fun ComposablesGalleryPage (){

    var components by remember { mutableStateOf(emptyList<Method>()) }

    LaunchedEffect(Unit) {
        val scanResults = ClassGraph()
            .enableAllInfo()
            .scan()
        val methods = scanResults
            .getClassesWithMethodAnnotation(ComposablesGalleryItem::class.java)
            .flatMap { it.loadClass().declaredMethods.toList() }
            .filter { method -> method.getAnnotation(ComposablesGalleryItem::class.java) != null }
        methods.forEach{
            println(it.declaringClass.name)
            println(it.name)
        }
        components = methods
    }
    Column {
        components.forEach {
            Text("class name ${it.declaringClass.name}")
            Text("method name ${it.name}")
            Text("annotation content ${it.getAnnotation(ComposablesGalleryItem::class.java).toOption().map { it.name }.getOrElse { "not found" }}")
            HorizontalDivider()
        }
    }
}