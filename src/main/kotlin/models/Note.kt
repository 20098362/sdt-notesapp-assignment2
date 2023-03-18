package ie.setu.models

data class Note (var noteTitle: String,
                 var notePriority: Int,
                 var noteCategory: String,
                 var isNoteArchived: Boolean,
                 var isNoteTodo: Boolean,
                 var isNoteCompleted: Boolean,
                 var noteContents: String){
}