package com.example.laletin.picturerbog

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("photos")
class JSONImages {
    @JsonProperty("photos")
    @get:JsonProperty("photos")
    @set:JsonProperty("photos")
    var photos: Photos? = null
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("photo")
class Photos {
    @JsonProperty("photo")
    @get:JsonProperty("photo")
    @set:JsonProperty("photo")
    var photo: Array<Photo>? = null
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "url_s", "url_m", "url_l", "title")
class Photo {
    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null
    @JsonProperty("url_s")
    @get:JsonProperty("url_s")
    @set:JsonProperty("url_s")
    var url_s: String? = null
    @JsonProperty("url_m")
    @get:JsonProperty("url_m")
    @set:JsonProperty("url_m")
    var url_m: String? = null
    @JsonProperty("url_l")
    @get:JsonProperty("url_l")
    @set:JsonProperty("url_l")
    var url_l: String? = null
    @JsonProperty("title")
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String? = null
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }
}