package net.systemvi.configurator.utils.annotations


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ComposablesGalleryItem(
    val name:String,
)