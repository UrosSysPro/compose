package net.systemvi.configurator.utils.annotations

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import kotlin.sequences.forEach

class ComposablesGalleryItemProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
): SymbolProcessor{
    override fun process(resolver: Resolver): List<KSAnnotated> {

        logger.warn("hello from ksp")
        println("hello from ksp")

        val symbols = resolver
            .getSymbolsWithAnnotation(ComposablesGalleryItem::class.qualifiedName!!)
            .filterIsInstance<KSFunctionDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        val file = codeGenerator.createNewFile(
            Dependencies(false),
            packageName = "net.systemvi.configurator.utils.annotations",
            fileName = "GeneratedComposableRegistry"
        )

        file.bufferedWriter().use { writer ->
            writer.appendLine("package net.systemvi.configurator.utils.annotations")
            writer.appendLine()
            writer.appendLine("import androidx.compose.runtime.Composable")
            writer.appendLine()
            writer.appendLine("val galleryComponents: Map<String, @Composable () -> Unit> = mapOf(")
            symbols.forEach { func ->
                val name = func.annotations
                    .first { it.shortName.asString() == "ComposablesGalleryItem" }
                    .arguments
                    .first { it.name?.asString() == "name" }
                    .value as String

                val fqName = func.qualifiedName!!.asString()
                writer.appendLine("""    "$name" to { $fqName() },""")
            }

            writer.appendLine(")")
        }

        return emptyList()
    }
}
