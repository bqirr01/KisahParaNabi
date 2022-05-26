package com.basya.kisahparanabi.utils

fun fromHttpToHttpsString(httpString: String?): String? {
    if (httpString == null) return null
    if (!httpString.startsWith("http")) return null

    return StringBuilder(httpString).insert(4, "s").toString();
}