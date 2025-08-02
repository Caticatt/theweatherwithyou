package com.alexcfa.yourweather.ui.common


object WeatherTranslations {
    
    private val translations = mapOf(
        "sunny" to "soleado",
        "clear" to "despejado",
        "bright" to "brillante",
        
        "cloudy" to "nublado",
        "overcast" to "encapotado",
        "partly cloudy" to "parcialmente nublado",
        "partly sunny" to "parcialmente soleado",
        "mostly cloudy" to "mayormente nublado",
        "mostly sunny" to "mayormente soleado",
        "scattered clouds" to "nubes dispersas",
        "broken clouds" to "nubes fragmentadas",
        "few clouds" to "pocas nubes",
        
        "rainy" to "lluvioso",
        "rain" to "lluvia",
        "light rain" to "lluvia ligera",
        "moderate rain" to "lluvia moderada",
        "heavy rain" to "lluvia intensa",
        "drizzle" to "llovizna",
        "showers" to "chubascos",
        "thunderstorm" to "tormenta eléctrica",
        "storm" to "tormenta",
        
        "snow" to "nieve",
        "snowy" to "nevado",
        "light snow" to "nevada ligera",
        "heavy snow" to "nevada intensa",
        "sleet" to "aguanieve",
        "blizzard" to "ventisca",
        
        "fog" to "niebla",
        "foggy" to "con niebla",
        "mist" to "neblina",
        "misty" to "con neblina",
        "haze" to "bruma",
        "hazy" to "brumoso",
        
        "windy" to "ventoso",
        "breezy" to "con brisa",
        "gusty" to "con ráfagas",
        
        "hot" to "caluroso",
        "cold" to "frío",
        "freezing" to "helado",
        "humid" to "húmedo",
        "dry" to "seco",
        
        "fair" to "bueno",
        "mild" to "templado",
        "cool" to "fresco",
        "warm" to "cálido"
    )

    fun translateWeatherDescription(description: String?): String {
        if (description.isNullOrBlank()) return ""
        
        val cleanDescription = description.lowercase().trim()
        
        translations[cleanDescription]?.let {
            return it.replaceFirstChar { char -> char.uppercase() }
        }
        
        for ((english, spanish) in translations) {
            if (cleanDescription.contains(english)) {
                return spanish.replaceFirstChar { char -> char.uppercase() }
            }
        }
        
        return description.replaceFirstChar { char -> char.uppercase() }
    }
}

fun String?.translateWeather(): String = WeatherTranslations.translateWeatherDescription(this)