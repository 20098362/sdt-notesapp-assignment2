package ie.setu

import ie.setu.controllers.NoteAPI
import ie.setu.models.Note
import ie.setu.persistence.JSONSerializer
import ie.setu.persistence.XMLSerializer
import ie.setu.persistence.YAMLSerializer
import ie.setu.utils.ValidateInput.readValidCategory
import ie.setu.utils.ValidateInput.readValidPriority
import mu.KotlinLogging
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import kotlin.system.exitProcess

/**
 * The commented out values below are how you swap between persistence types
 */

//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
//private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
private val noteAPI = NoteAPI(YAMLSerializer(File("notes.yaml")))

const val ansiReset = "\u001B[0m"
const val ansiRed = "\u001B[31m"
const val ansiGreen = "\u001B[32m"
const val ansiYellow = "\u001B[33m"
const val ansiBlue = "\u001B[34m"
const val ansiCyan = "\u001B[36m"

/**
 * The methods below are responsible for interacting with the user and printing the relevant data to the screen
 */
fun mainMenu() : Int {
    return readNextInt(""" 
         > $ansiCyan--------------------------------------------$ansiReset
         > |          $ansiRed NOTE KEEPER APP $ansiReset               |
         > | $ansiRed No. of notes in system: ${noteAPI.numberOfNotes()}$ansiReset               |
         > $ansiCyan--------------------------------------------$ansiReset
         > | $ansiRed NOTE MENU $ansiReset                              |
         > |  $ansiBlue 1) Add a note $ansiReset                         |
         > |  $ansiBlue 2) List notes $ansiReset                         |
         > |  $ansiBlue 3) Update a note$ansiReset                       |
         > |  $ansiBlue 4) Delete a note $ansiReset                      |
         > |  $ansiBlue 5) Archive a note$ansiReset                      |
         > |  $ansiBlue 6) Search note(by title) $ansiReset              |
         > |  $ansiBlue 7) Set note as Todo $ansiReset                   |
         > |  $ansiBlue 8) Set note as complete$ansiReset                |
         > |  $ansiBlue 9) Search note(by category) $ansiReset           |
         > $ansiCyan--------------------------------------------$ansiReset
         > |  $ansiBlue 20) Save notes$ansiReset                         |
         > |  $ansiBlue 21) Load notes$ansiReset                         |
         > $ansiCyan--------------------------------------------$ansiReset
         > |  $ansiYellow 0) Exit $ansiReset                               |
         > $ansiCyan--------------------------------------------$ansiReset
         > $ansiGreen==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> createNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            5 -> archiveNote()
            6 -> searchNotes()
            7 -> todoNote()
            8 -> completeNote()
            9 -> searchByCategory()
            20  -> save()
            21  -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun createNote(){
    logger.info { "createNote() function invoked" }

    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readValidPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    println("College Work, Work, Hobby, Holiday, App Work, Misc")
    val noteCategory = readValidCategory("Enter a category for the note: ")
    val noteContent = readNextLine("Enter the note's content: ")
    val isAdded = noteAPI.create(Note(noteTitle, notePriority, noteCategory, false, false, false, noteContent))

    if (isAdded) println("Created Successfully")
    else println("Create Failed")
}

fun listNotes() {
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiRed No. of notes: ${noteAPI.numberOfNotes()} $ansiReset                         |
                  > |$ansiRed No. of active notes: ${noteAPI.numberOfActiveNotes()} $ansiReset                  |
                  > |$ansiRed No. of archived notes: ${noteAPI.numberOfArchivedNotes()} $ansiReset                |
                  > |$ansiRed No. of todo notes: ${noteAPI.numberOfTodo()} $ansiReset                    |
                  > |$ansiRed No. of completed notes: ${noteAPI.numberOfCompleted()} $ansiReset               |
                  > |$ansiRed No. of notes in progress: ${noteAPI.numberOfInProgress()} $ansiReset             |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   1) View ALL notes   $ansiReset                   |
                  > |$ansiBlue   2) View ACTIVE notes  $ansiReset                 |
                  > |$ansiBlue   3) View ARCHIVED notes   $ansiReset              |
                  > |$ansiBlue   4) View note by priority  $ansiReset             |
                  > |$ansiBlue   5) View all todo notes    $ansiReset             |
                  > |$ansiBlue   6) View all completed notes  $ansiReset          |
                  > |$ansiBlue   7) View all in progress       $ansiReset         |
                  > $ansiCyan--------------------------------------------$ansiReset
         >$ansiGreen ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllNotes()
            2 -> listActiveNotes()
            3 -> listArchivedNotes()
            4 -> listByPriority()
            5 -> allTodo()
            6 -> allCompleted()
            7 -> allInProgress()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun searchNotes() {
    val searchTitle = readNextLine("Enter the note title to search by: ")
    val searchResults = noteAPI.searchByTitle(searchTitle)
    if (searchResults.isEmpty()) println("No notes found")
    else println(searchResults)
}

fun listAllNotes() = println(noteAPI.listAllNotes())

fun listArchivedNotes() = println(noteAPI.listArchivedNotes())

fun listActiveNotes() = println(noteAPI.listActiveNotes())

fun archiveNote() {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        //only ask the user to choose the note to archive if active notes exist
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveNote(indexToArchive)) println("Archive Successful!")
        else println("Archive NOT Successful")
    }
}

fun todoNote(){
    listActiveNotes()
    if(noteAPI.numberOfActiveNotes() > 0){
        val indexTodo = readNextInt("Enter the index of the note you wish to set as todo: ")
        if(noteAPI.setNoteTodo(indexTodo)) println("Note set as Todo")
        else println("Set as Todo failed")
    }
}

fun completeNote(){
    listActiveNotes()
    if(noteAPI.numberOfActiveNotes() > 0){
        val indexComplete = readNextInt("Enter the index of the note you wish to set as complete: ")
        if(noteAPI.setNoteCompleted(indexComplete)) println("Note set as completed")
        else println("Set as completed failed")
    }
}

fun listByPriority(){
    val priorityNum = readValidPriority("Please enter the note priority you wish to search by: ")
    println(noteAPI.listNotesBySelectedPriority(priorityNum))
}

fun searchByCategory(){
    println("College Work, Work, Hobby, Holiday, App Work, Misc")
    val searchCategory = readValidCategory("Enter the note category you wish to search by: ")
    val categoryResults = noteAPI.searchByCategory(searchCategory)
    if(categoryResults.isEmpty()) println("No notes found")
    else println(categoryResults)
}

fun allTodo() = println(noteAPI.listAllTodo())

fun allCompleted() = println(noteAPI.listAllCompleted())

fun allInProgress() = println(noteAPI.listAllInProgress())

fun updateNote() {
    //logger.info { "updateNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readValidPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            println("College Work, Work, Hobby, Holiday, App Work, Misc")
            val noteCategory = readValidCategory("Enter a category for the note: ")
            val noteContent = readNextLine("Enter the note's content: ")
            val archiveNote = readNextInt("Archive note? 1=Yes/0=No")
            var localArchive = false
            if(archiveNote == 1){
                localArchive = true
            }
            val todoNote = readNextInt("Set as Todo? 1=Yes/0=No")
            var localTodo = false
            if(todoNote == 1){
                localTodo = true
            }
            val completeNote = readNextInt("Complete note? 1=Yes/0=No")
            var localComplete = false
            if(completeNote == 1){
                localComplete = true
            }


            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, localArchive, localTodo, localComplete, noteContent))){
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