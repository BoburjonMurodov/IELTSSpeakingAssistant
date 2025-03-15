package com.boboor.speaking.utils.enums


/*
    Created by Boburjon Murodov 15/03/25 at 12:48
*/

val EVERY_DAY = "Every day"
val EVERY_APP_OPENING = "Every app opening"
val NEVER = "Never"

enum class UpdateFrequency(val title: String) {
    EVERY_DAY(com.boboor.speaking.utils.enums.EVERY_DAY),
    EVERY_APP_OPENING(com.boboor.speaking.utils.enums.EVERY_APP_OPENING),
    NEVER(com.boboor.speaking.utils.enums.NEVER)
}