package ie.setu.persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.StaxDriver
import ie.setu.models.Note
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class YAMLSerializer(private val file: File) : Serializer {
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(StaxDriver())
        xStream.allowTypes(arrayOf(Note::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(StaxDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}