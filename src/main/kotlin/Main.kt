package ie.setu

import ie.setu.controllers.NoteAPI
import ie.setu.models.Note
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit
import kotlin.system.exitProcess

private val noteAPI = NoteAPI()

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
        >   5)List all active notes
        >   6)List all archived notes
        >   7)List by priority number
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
            5 -> listActive()
            6 -> listArchive()
            7 -> listByPriority()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun createNote(){
    logger.info { "createNote() function invoked" }

    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.create(Note(noteTitle, notePriority, noteCategory, false))

    if (isAdded) {
        println("Created Successfully")
    } else {
        println("Create Failed")
    }
}

fun listNotes(){
    logger.info { "listNotes() function invoked" }

    println(noteAPI.listAllNotes())
}

fun listActive(){
    println("Number of notes currently active: ${noteAPI.numberOfActiveNotes()}")
    println(noteAPI.listActiveNotes())
}

fun listArchive(){
    println("Number of notes currently archived: ${noteAPI.numberOfArchivedNotes()}")
    println(noteAPI.listArchivedNotes())
}

fun listByPriority(){
    val priorityNum = ScannerInput.readNextInt("Please enter the note priority you wish to search by: ")
    println(noteAPI.listNotesBySelectedPriority(priorityNum))
}

fun updateNote() {
    //logger.info { "updateNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readNextLine("Enter a category for the note: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}


fun deleteNote(){
    //logger.info { "deleteNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}


fun exitApp(){
    println("Closing app")
    exitProcess(0)
}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>){
    runMenu()
}