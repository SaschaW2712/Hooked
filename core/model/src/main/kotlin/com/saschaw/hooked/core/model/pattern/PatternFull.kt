package com.saschaw.hooked.core.model.pattern

import com.saschaw.hooked.core.model.components.PatternAttribute
import com.saschaw.hooked.core.model.components.PatternCategory
import com.saschaw.hooked.core.model.components.PatternCraftType
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
    val craft: List<PatternCraftType>,
    @SerialName("created_at") val creationDateString: String,
    val currency: String,
    @SerialName("currency_symbol") val currencySymbol: String,
    @SerialName("difficulty_average") val difficultyAverage: Float,
    @SerialName("difficulty_count") val difficultyCount: Int,
    @SerialName("download_location") val downloadLocation: PatternDownloadLocation,
    val downloadable: Boolean,
    @SerialName("favorites_count") val favoritesCount: Int,
    val free: Boolean,
    val gauge: String,
    @SerialName("gauge_description") val gaugeDescription: String,
    @SerialName("gauge_divisor") val gaugeDivisor: Int,
    @SerialName("gauge_pattern") val gaugePattern: String,
    @SerialName("generally_available") private val generalAvailabilityDateString: String,
    @SerialName("has_uk_terminology") val hasUkTerminology: Boolean,
    @SerialName("has_us_terminology") val hasUsTerminology: Boolean,
    val languages: List<String>,
    val name: String,
    @SerialName("notes_html") val notesHtml: String,
    val notes: String?,
    val packs: List<PatternPack>,
    @SerialName("pattern_attributes") val patternAttributes: List<PatternAttribute>,
    @SerialName("pattern_author") val patternAuthor: PatternAuthor,
    @SerialName("pattern_categories") val patternCategories: List<PatternCategory>,
    @SerialName("pattern_needle_sizes") val patternNeedleSizes: String,
    @SerialName("pattern_type") val patternType: PatternType,
    @SerialName("pdf_in_library") val pdfInLibrary: Boolean,
    @SerialName("pdf_url") val pdfUrl: String,
    val permalink: String,
    @SerialName("personal_attributes") val personalAttributes: PatternPersonalAttributes,
    val photos: List<RavelryPhoto>,
    val price: String,
    val printings: List<Printing>,
    @SerialName("product_id") val productId: Int,
    @SerialName("projects_count") val projectsCount: Int,
    @SerialName("published") private val publicationDateString: String,
    @SerialName("queued_projects_count") val queuedProjectsCount: Int,
    @SerialName("rating_average") val ratingAverage: Float,
    @SerialName("rating_count") val ratingCount: Int,
    @SerialName("ravelry_download") val availableAsRavelryDownload: Boolean,
    @SerialName("row_gauge") val rowGauge: String,
    @SerialName("sizes_available") val sizesAvailable: String,
    @SerialName("unlisted_product_ids") val unlistedProductIds: List<Int>,
    @SerialName("updated_at") private val lastUpdatedDateString: String,
    val url: String,
    @SerialName("volumes_in_library") val volumesInLibrary: List<Int>,
    val yardage: Int,
    @SerialName("yardage_description") val yardageDescription: String,
    @SerialName("yardage_max") val yardageMax: Int,
    @SerialName("yarn_list_type") val yarnListType: String,
    @SerialName("yarn_weight") val yarnWeight: List<YarnWeight>,
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
}

@Serializable
data class PatternPersonalAttributes(
    val favorited: Boolean,
    @SerialName("bookmark_id") val bookmarkId: Int?,
    val queued: Boolean,
    @SerialName("in_library") val inLibrary: Boolean,
)