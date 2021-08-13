package com.abcairlines.mobile

fun currentVersion(): abcVersion{
    return abcVersion.values().find { it.versionName == BuildConfig.abc_VERSION } ?:
    throw Exception("Version ${BuildConfig.abc_VERSION} not yet added in enum list (see abcVersion)")
}

enum class abcVersion( val majorVersion: Int, val minorVersion: Int, val patchVersion: Int ) {

    V_4_11_0(4,11,0),
    V_4_12_0(4,12,0),
    V_4_12_3(4,12,3),
    V_4_13_0(4,13,0),

    V_5_1_0(5,1,0),
    V_5_1_1(5,1,1),

    V_5_2_0(5,2,0),

    V_5_3_0(5,3,0),
    V_5_3_1(5,3,1),
    V_5_3_2(5,3,2),

    V_5_4_0(5,4,0),
    V_5_4_50(5,4,50),

    V_5_5_0(5,5,0),
    V_5_5_1(5,5,1),
    V_5_5_2(5,5,2),
    V_5_5_50(5,5,50),

    V_5_6_0(5,6,0),

    V_5_6_1(5,6,1),

    V_5_7_0(5,7,0),

    V_5_8_0(5,8,0),
    V_5_8_1(5,8,1),

    V_5_9_0(5,9,0),

    V_5_10_0(5,10,0),
    V_5_10_1(5,10,1),
    V_5_10_2(5,10,2),
    V_5_10_50(5,10,50),

    V_5_11_0(5,11,0),

    V_5_12_0(5,12,0),
    V_5_12_1(5,12,1),

    V_6_1_0(6,1,0),

    V_6_2_0(6,2,0),
    V_6_2_1(6,2,1),
    V_6_2_2(6,2,2),
    V_6_2_50(6,2,50),

    V_6_3_0(6,3,0),

    V_6_4_0(6,4,0),

    V_6_5_0(6,5,0),
    V_6_5_1(6,5,1),
    V_6_5_2(6,5,2),

    V_6_6_0(6,6,0),

    V_6_7_0(6,7,0),
    V_6_7_1(6,7,1),

    V_6_8_0(6,8,0),

    V_6_9_0(6,9,0),

    V_6_10_0(6,10,0),
    V_6_10_1(6,10,1),
    V_6_10_2(6,10,2),

    V_6_11_0(6,11,0),
    V_6_11_1(6,11,1),
    V_6_11_2(6,11,2),

    V_6_12_0(6,12,0),

    V_7_1_0(7, 1, 0),

    V_7_2_0(7, 2, 0),
    V_7_2_1(7, 2, 1),

    V_7_3_0(7, 3, 0),

    V_7_4_0(7, 4, 0),
    V_7_4_1(7, 4, 1),

    V_7_5_0(7, 5, 0),

    V_7_6_0(7, 6, 0),
    V_7_6_1(7, 6, 1),

    V_7_7_0(7, 7, 0),

    V_7_8_0(7, 8, 0),
    V_7_8_1(7, 8, 1),

    V_7_9_0(7, 9, 0),

    V_7_10_0(7, 10, 0),
    V_7_10_1(7, 10, 1),

    V_7_11_0(7, 11, 0),
    V_7_11_50(7, 11, 50),

    V_7_12_0(7, 12, 0),
    V_7_12_1(7, 12, 1),

    V_8_1_0(8, 1, 0),
    V_8_1_1(8, 1, 1),
    V_8_1_2(8, 1, 2),

    V_8_2_0(8, 2, 0),

    V_8_3_0(8, 3, 0),

    V_8_4_0(8, 4, 0),
    V_8_4_1(8, 4, 1),
    V_8_4_2(8, 4, 2),

    V_8_5_0(8, 5, 0),

    V_8_6_0(8, 6, 0),

    V_8_7_0(8, 7, 0),

    V_8_8_0(8, 8, 0),
    V_8_8_1(8, 8, 1),

    V_8_9_0(8, 9, 0),

    V_8_10_0(8, 10, 0),

    /**
     * The max version supported by our configuration
     */
    V_99_99_99(99,99,99);

    val versionName: String = "$majorVersion.$minorVersion.$patchVersion"

    fun isLessThan(version: abcVersion) = versionToComparableInteger(versionName) < versionToComparableInteger(version.versionName)

    companion object {

        fun parse(versionToParse: String): abcVersion? {
            return values().find { it.versionName == versionToParse }
        }

        // current algorithm: versionCode versionMajor * 100000 + versionMinor * 1000 + versionPatch * 10
        // same as specified in build.gradle
        @JvmStatic
        fun versionToComparableInteger(versionName: String): Int{
            // for matching "xx.xx.xx" or "xx.xx" "only, where the first digit is not a zero in any case
            val pattern = "([1-9]|[1-9][0-9])[.]([0-9]|[1-9][0-9])([.]([0-9]|[1-9][0-9]))?\$"

            if(!pattern.toRegex().matches(versionName)){
                return -1
            }

            val splitResult = versionName.split(".")

            val majorVersion = splitResult[0].toInt()
            val minorVersion = splitResult[1].toInt()

            val patchVersion = if (splitResult.size > 2) {
                splitResult[2].toInt()
            } else {
                0
            }

            return (majorVersion * 100000) + (minorVersion * 1000) + (patchVersion * 10)
        }
    }
}