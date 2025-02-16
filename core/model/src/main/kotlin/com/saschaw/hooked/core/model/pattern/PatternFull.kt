package com.saschaw.hooked.core.model.pattern

import com.saschaw.hooked.core.model.components.PatternAttribute
import com.saschaw.hooked.core.model.components.PatternCategory
import com.saschaw.hooked.core.model.components.PatternCraft
import com.saschaw.hooked.core.model.components.PatternDownloadLocation
import com.saschaw.hooked.core.model.components.PatternPack
import com.saschaw.hooked.core.model.components.PatternType
import com.saschaw.hooked.core.model.components.Printing
import com.saschaw.hooked.core.model.components.RavelryPhoto
import com.saschaw.hooked.core.model.components.YarnWeight
import com.saschaw.hooked.core.model.user.PatternAuthor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class PatternFull(
    val id: Int,
    @SerialName("comments_count") val commentsCount: Int,
    val craft: PatternCraft,
    @SerialName("created_at") val creationDateString: String,
    val currency: String,
    @SerialName("currency_symbol") val currencySymbol: String,
    @SerialName("difficulty_average") val difficultyAverage: Float,
    @SerialName("difficulty_count") val difficultyCount: Int? = null,
    @SerialName("download_location") val downloadLocation: PatternDownloadLocation,
    val downloadable: Boolean,
    @SerialName("favorites_count") val favoritesCount: Int,
    val free: Boolean,
    val gauge: Float,
    @SerialName("gauge_description") val gaugeDescription: String,
    @SerialName("gauge_divisor") val gaugeDivisor: Int,
    @SerialName("gauge_pattern") val gaugePattern: String,
    @SerialName("generally_available") private val generalAvailabilityDateString: String,
    @SerialName("has_uk_terminology") val hasUkTerminology: Boolean? = null,
    @SerialName("has_us_terminology") val hasUsTerminology: Boolean? = null,
    val languages: List<PatternLanguage>,
    val name: String,
    @SerialName("notes_html") val notesHtml: String,
    val notes: String?,
    val packs: List<PatternPack>,
    @SerialName("pattern_attributes") val patternAttributes: List<PatternAttribute>,
    @SerialName("pattern_author") val patternAuthor: PatternAuthor,
    @SerialName("pattern_categories") val patternCategories: List<PatternCategory>,
    @SerialName("pattern_needle_sizes") val patternNeedleSizes: List<PatternNeedleSize>,
    @SerialName("pattern_type") val patternType: PatternType,
    @SerialName("pdf_in_library") val pdfInLibrary: Boolean,
    @SerialName("pdf_url") val pdfUrl: String,
    val permalink: String,
    @SerialName("personal_attributes") val personalAttributes: PatternPersonalAttributes,
    val photos: List<RavelryPhoto>,
    val price: Float? = null,
    val printings: List<Printing>,
    @SerialName("product_id") val productId: Int,
    @SerialName("projects_count") val projectsCount: Int,
    @SerialName("published") private val publicationDateString: String,
    @SerialName("queued_projects_count") val queuedProjectsCount: Int,
    @SerialName("rating_average") val ratingAverage: Float,
    @SerialName("rating_count") val ratingCount: Int,
    @SerialName("ravelry_download") val availableAsRavelryDownload: Boolean,
    @SerialName("row_gauge") val rowGauge: Float,
    @SerialName("sizes_available") val sizesAvailable: String,
    @SerialName("unlisted_product_ids") val unlistedProductIds: List<Int>? = null,
    @SerialName("updated_at") private val lastUpdatedDateString: String,
    val url: String,
    @SerialName("volumes_in_library") val volumesInLibrary: List<Int>,
    val yardage: Int,
    @SerialName("yardage_description") val yardageDescription: String,
    @SerialName("yardage_max") val yardageMax: Int,
    @SerialName("yarn_list_type") val yarnListType: Int,
    @SerialName("yarn_weight") val yarnWeight: YarnWeight,
    @SerialName("yarn_weight_description") val yarnWeightDescription: String
) {
    private val datePattern = "yyyy/MM/dd HH:mm:ss Z"

    val generalAvailabilityDate: ZonedDateTime
        get() = ZonedDateTime.parse(
            generalAvailabilityDateString,
            DateTimeFormatter.ofPattern(datePattern)
        )

    val creationDate: ZonedDateTime
        get() = ZonedDateTime.parse(
            creationDateString,
            DateTimeFormatter.ofPattern(datePattern)
        )

    val publicationDate: ZonedDateTime
        get() = ZonedDateTime.parse(
            publicationDateString,
            DateTimeFormatter.ofPattern(datePattern)
        )

    val lastUpdatedDate: ZonedDateTime
        get() = ZonedDateTime.parse(
            lastUpdatedDateString,
            DateTimeFormatter.ofPattern(datePattern)
        )

    val displayPrice: String
        get() {
            return price?.let {
                val priceString = "%.2f".format(price)
                currencySymbol + priceString
            } ?: "Free"
        }
}

@Serializable
data class PatternLanguage(
    val code: String,
    val id: Int,
    val name: String,
    val permalink: String,
    @SerialName("short_name") val shortName: String,
    val universal: Boolean
)

@Serializable
data class PatternNeedleSize(
    val hook: String,
    val id: Int,
    val metric: Float,
    val name: String,
    @SerialName("pretty_metric") val prettyMetric: String,
    val us: String? = null,
)

@Serializable
data class PatternPersonalAttributes(
    val favorited: Boolean,
    @SerialName("bookmark_id") val bookmarkId: Int?,
    val queued: Boolean,
    @SerialName("in_library") val inLibrary: Boolean,
)