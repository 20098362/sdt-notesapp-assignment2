package ie.setu

import mu.KotlinLogging
import utils.ScannerInput
import java.lang.System.exit

fun mainMenu(): Int{
    return ScannerInput.readNextInt("""
        >--------------------
        >Notes Keeper App
        >--------------------
        >Note Menu
        >
        >   1)Create new note
        >   2)List all notes
        >   3)Update existing note
        >   4)Delete note
        >--------------------
        >   0)Exit
        >--------------------
        >=====>""".trimMargin(">"))
}

fun runMenu(){
    do{
        when(val option = mainMenu()){
            1 -> createNote()
            2 -> listNotes()
            3 -> updateNote()
            4 -> deleteNote()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun createNote(){
    logger.info { "createNote() function invoked" }
}

fun listNotes(){
    logger.info { "listNotes() function invoked" }
}

fun updateNote(){
    logger.info { "updateNote() function invoked" }
}

fun deleteNote(){
    logger.info { "deleteNote() function invoked" }
}

fun exitApp(){
    println("Closing app")
    exit(0)
}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>){
    runMenu()
}