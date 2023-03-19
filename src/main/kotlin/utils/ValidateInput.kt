package ie.setu.utils

import utils.ScannerInput
import java.util.*

/**
 * This utils object is responsible for validating the category and priority inputs made by the user
 */
object ValidateInput {

    @JvmStatic
    fun readValidCategory(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (CategoryUtility.isValidCategory(input))
                return input
            else {
                print("Invalid category $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    @JvmStatic
    fun readValidPriority(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1 ,5))
                return input
            else {
                print("Invalid priority $input.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }
}