package ie.setu.utils

object Utilities {

    //NOTE: JvmStatic annotation means that the methods are static (i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.



    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)
}