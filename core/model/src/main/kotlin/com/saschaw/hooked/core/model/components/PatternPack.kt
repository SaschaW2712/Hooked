package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatternPack(
    @SerialName("color_family_id") val colorFamilyId: String?,
    val colorway: String?,
    @SerialName("dye_lot") val dyeLot: String?,
    @SerialName("grams_per_skein") val gramsPerSkein: Float?,
    val id: Int,
    @SerialName("meters_per_skein") val metersPerSkein: Float?,
    @SerialName("ounces_per_skein") val ouncesPerSkein: Float?,
    @SerialName("personal_name") val personalName: String?,
    @SerialName("prefer_metric_length") val preferMetricLength: Boolean?,
    @SerialName("prefer_metric_weight") val preferMetricWeight: Boolean?,
    @SerialName("primary_pack_id") val primaryPackId: Int?,
    @SerialName("project_id") val projectId: Int?,
    @SerialName("quantity_description") val quantityDescription: String?,
    @SerialName("shop_id") val shopId: Int?,
    @SerialName("shop_name") val shopName: String?,
    val skeins: String?,
    @SerialName("stash_id") val stashId: Int?,
    @SerialName("thread_size") val threadSize: String?,
    @SerialName("total_grams") val totalGrams: Float?,
    @SerialName("total_meters") val totalMeters: Float?,
    @SerialName("total_ounces") val totalOunces: Float?,
    @SerialName("total_paid_currency") val totalPaidCurrency: String? = null,
    @SerialName("total_paid") val totalPaid: String? = null,
    @SerialName("total_yards") val totalYards: Float?,
    @SerialName("yards_per_skein") val yardsPerSkein: Float?,
    val yarn: PatternYarn? = null,
    @SerialName("yarn_id") val yarnId: Int?,
    @SerialName("yarn_name") val yarnName: String?,
    @SerialName("yarn_weight") val yarnWeight: YarnWeight? = null
)