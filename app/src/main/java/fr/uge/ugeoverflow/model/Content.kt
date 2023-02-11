package fr.uge.ugeoverflow.model

import java.util.*

data class Content(
    private val imagesReferences: MutableSet<Image>? = mutableSetOf(),
    private val text : String?,
    ){

    val getImageReferences: MutableSet<Image>? get() = imagesReferences
    val getText: String? get() = text

}

class ContentBuilder{

    private var imagesReferences : MutableSet<Image> ?= mutableSetOf()
    private var text : String ?= ""


    fun imagesReferences(imagesReferences: MutableSet<Image>) = apply { this.imagesReferences = imagesReferences }
    fun text(text: String) = apply { this.text = text }


    fun build(): Content {
        return Content(imagesReferences = imagesReferences, text = text)
    }

    companion object {
        fun builder() = ContentBuilder()
    }
}