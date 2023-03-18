package ie.setu.controllers

import ie.setu.models.Note
import ie.setu.persistence.Serializer

class NoteAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var notes = ArrayList<Note>()

    fun create(note: Note): Boolean = notes.add(note)


    fun deleteNote(indexToDelete: Int): Note? =
        if (isValidListIndex(indexToDelete, notes)) notes.removeAt(indexToDelete)
        else null

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            foundNote.noteContents = note.noteContents
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun listAllNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(notes)

    fun listActiveNotes(): String =
        if  (numberOfActiveNotes() == 0)  "No active notes stored"
        else formatListString(notes.filter { note -> !note.isNoteArchived})

    fun listArchivedNotes(): String =
        if  (numberOfArchivedNotes() == 0) "No archived notes stored"
        else formatListString(notes.filter { note -> note.isNoteArchived})

    fun listNotesBySelectedPriority(priority: Int): String =
        if (notes.isEmpty()) "No notes stored"
        else {
            val listOfNotes = formatListString(notes.filter{ note -> note.notePriority == priority})
            if (listOfNotes.equals("")) "No notes with priority: $priority"
            else "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
        }

    fun numberOfArchivedNotes(): Int = notes.count { note: Note -> note.isNoteArchived }

    fun numberOfNotesByPriority(priority: Int): Int = notes.count { p: Note -> p.notePriority == priority }

    fun numberOfNotes(): Int = notes.size

    fun numberOfActiveNotes(): Int = notes.count{note: Note -> !note.isNoteArchived}

    fun findNote(index: Int): Note? =
        if (isValidListIndex(index, notes)) notes[index]
        else null

    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)

    fun isValidIndex(index: Int): Boolean = isValidListIndex(index, notes)

    fun archiveNote(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val noteToArchive = notes[indexToArchive]
            if (!noteToArchive.isNoteArchived) {
                noteToArchive.isNoteArchived = true
                return true
            }
        }
        return false
    }

    fun setNoteTodo(indexTodo: Int): Boolean{
        if(isValidIndex(indexTodo)){
            val noteTodo = notes[indexTodo]
            if(!noteTodo.isNoteTodo){
                noteTodo.isNoteTodo = true
                return true
            }
        }
        return false
    }

    fun setNoteCompleted(indexComplete: Int): Boolean{
        if(isValidIndex(indexComplete)){
            val noteComplete = notes[indexComplete]
            if(!noteComplete.isNoteCompleted){
                noteComplete.isNoteCompleted = true
                return true
            }
        }
        return false
    }

    private fun formatListString(notesToFormat : List<Note>) : String =
        notesToFormat
            .joinToString (separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString() }

    fun searchByTitle (searchString : String) =
        formatListString(
            notes.filter { note -> note.noteTitle.contains(searchString, ignoreCase = true) })

    fun listAllTodo(): String =
        if(numberOfNotes() == 0) "No notes stored"
        else formatListString(notes.filter {note -> note.isNoteTodo})

    fun listAllCompleted(): String =
        if(numberOfNotes() == 0) "No notes stored"
        else formatListString(notes.filter {note -> note.isNoteCompleted})

    fun listAllInProgress(): String =
        if(numberOfActiveNotes() == 0) "No active notes of that kind"
        else formatListString(notes.filter {note -> note.isNoteTodo && !note.isNoteCompleted})

    fun searchByCategory (searchString: String) =
        formatListString(
            notes.filter {note -> note.noteCategory.contains(searchString, ignoreCase = true)})

    fun numberOfTodo(): Int = notes.count{note -> note.isNoteTodo}

    fun numberOfCompleted(): Int = notes.count{note -> note.isNoteCompleted}

    fun numberOfInProgress(): Int = notes.count{note -> !note.isNoteCompleted && note.isNoteTodo}

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() = serializer.write(notes)
}