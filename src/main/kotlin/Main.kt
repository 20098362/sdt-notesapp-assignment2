package ie.setu

import java.lang.System.exit
import java.util.*

val scanner = Scanner(System.`in`)

fun mainMenu(): Int{
    val printout = """
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
        """.trimMargin(">")
    println(printout)
    return scanner.nextInt()
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
    println("Create note selected")
}

fun listNotes(){
    println("List notes selected")
}

fun updateNote(){
    println("Update note selected")
}

fun deleteNote(){
    println("Delete note selected")
}

fun exitApp(){
    println("Closing app")
    exit(0)
}

fun main(args: Array<String>){
    runMenu()
}