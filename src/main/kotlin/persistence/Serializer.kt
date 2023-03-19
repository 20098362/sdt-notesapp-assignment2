package ie.setu.persistence

/**
 * This interface is responsible for providing the read and write methods used by the other serializer classes
 */
interface Serializer {
    @Throws(Exception::class)
    fun write(obj: Any?)

    @Throws(Exception::class)
    fun read(): Any?
}

