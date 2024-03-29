package utils

import ie.setu.utils.Utilities.validRange
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * This UtilitiesTest class is responsible for validating that the priority range numbers are accurate to system specifications
 */
class UtilitiesTest {

    @Test
    fun validRangeWorksWithPositiveTestData() {
        Assertions.assertTrue(validRange(1, 1, 1))
        Assertions.assertTrue(validRange(1, 1, 2))
        Assertions.assertTrue(validRange(1, 0, 1))
        Assertions.assertTrue(validRange(1, 0, 2))
        Assertions.assertTrue(validRange(-1, -2, -1))
    }

    @Test
    fun validRangeWorksWithNegativeTestData() {
        Assertions.assertFalse(validRange(1, 0, 0))
        Assertions.assertFalse(validRange(1, 1, 0))
        Assertions.assertFalse(validRange(1, 2, 1))
        Assertions.assertFalse(validRange(-1, -1, -2))
    }
}