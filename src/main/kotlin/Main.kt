package ie.setu

import ie.setu.controllers.NoteAPI
import ie.setu.models.Note
import ie.setu.persistence.JSONSerializer
import ie.setu.persistence.XMLSerializer
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import kotlin.system.exitProcess

//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))



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
        >   5)Archive note
        >--------------------
        >   6)List all active notes
        >   7)List all archived notes
        >   8)List by priority number
        >--------------------
        >   20)Save to file
        >   21)Load from file
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
            5 -> archiveNote()
            6 -> listActiveNotes()
            7 -> listArchive()
            8 -> listByPriority()
            20 -> save()
            21 -> load()
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


fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun archiveNote() {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        //only ask the user to choose the note to archive if active notes exist
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveNote(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}



fun listArchive(){
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

fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}


private val logger = KotlinLogging.logger {}

fun main(args: Array<String>){
    runMenu()
}