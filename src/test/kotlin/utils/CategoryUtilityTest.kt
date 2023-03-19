package utils

import ie.setu.utils.CategoryUtility.categories
import ie.setu.utils.CategoryUtility.isValidCategory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * This CategoryUtilityTest class is for ensuring that entered categories are valid and up to system specification
 */
internal class CategoryUtilityTest {
    @Test
    fun categoriesReturnsFullCategoriesSet(){
        Assertions.assertEquals(6, categories.size)
        Assertions.assertTrue(categories.contains("College Work"))
        Assertions.assertTrue(categories.contains("Work"))
        Assertions.assertTrue(categories.contains("Hobby"))
        Assertions.assertTrue(categories.contains("Holiday"))
        Assertions.assertTrue(categories.contains("App Work"))
        Assertions.assertTrue(categories.contains("Misc"))
        Assertions.assertFalse(categories.contains(""))
    }

    @Test
    fun isValidCategoryTrueWhenCategoryExists(){
        Assertions.assertTrue(isValidCategory("Work"))
        Assertions.assertTrue(isValidCategory("work"))
        Assertions.assertTrue(isValidCategory("WORK"))
    }

    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist(){
        Assertions.assertFalse(isValidCategory("Wor"))
        Assertions.assertFalse(isValidCategory("Colllege Work"))
        Assertions.assertFalse(isValidCategory(""))
    }
}