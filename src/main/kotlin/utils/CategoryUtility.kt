package ie.setu.utils

object CategoryUtility {

    @JvmStatic
    val categories = setOf ("College Work", "Work", "Hobby", "Holiday", "App Work", "Misc")  //add more categories in here.

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