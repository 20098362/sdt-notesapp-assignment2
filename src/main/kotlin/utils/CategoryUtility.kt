package ie.setu.utils

object CategoryUtility {

    @JvmStatic
    val categories = setOf ("College Work", "Work", "Hobby", "Holiday", "App Work", "Misc")

    @JvmStatic
    fun isValidCategory(categoryToCheck: String?): Boolean {
        for (category in categories) {
            if (category.equals(categoryToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}