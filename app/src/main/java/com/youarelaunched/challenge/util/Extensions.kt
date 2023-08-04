package com.youarelaunched.challenge.util


inline fun <reified T : Enum<T>> T.next(): T? {
    val values = enumValues<T>()
    return values.getOrNull(ordinal + 1)
}
val String.Companion.EMPTY: String
    get() = ""
