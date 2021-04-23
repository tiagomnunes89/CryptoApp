package br.dev.tmn.cryptocurrency.domain.entities

const val NOT_FOUND = "NOT FOUND"
const val DEFAULT_ID = 0L
const val DEFAULT_DOUBLE = 0.0

class Crypto(
    val id: Long = DEFAULT_ID,
    val name: String = NOT_FOUND,
    val price: Double = DEFAULT_DOUBLE,
    val percentChange1H: Double = DEFAULT_DOUBLE,
    val percentChange24H: Double = DEFAULT_DOUBLE,
    val percentChange7D: Double = DEFAULT_DOUBLE
)