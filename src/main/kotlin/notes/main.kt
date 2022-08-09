package notes

data class Note(
    val nId: Int = 0,
    val title: String = "title",
    val text: String = "text",
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    val privacyView: String = "privacy",
    val privacyComment: String = "privacy",
    val comments: MutableList<Comment>,
    val deletedNotes: Boolean = false
) {
    override fun toString(): String {
        return "$title \n $text"
    }
}

data class Comment(
    val cid: Int = 0,
    val ownerId: Int = 0,
    val replyTo: Int = 0,
    val message: String = "msg",
    val guid: String = "guid",
    val deleteComment: Boolean = false
)

interface ServiceForGeneric<N> {
    fun add(elem: N): N
    fun delete(elem: N): Boolean
    fun edit(elem: N): Boolean
    fun get(): String
    fun restore(elem: N): Boolean
}

class NoteWasDeletedException : RuntimeException()
class CommentWasDeletedException : RuntimeException()

class NoteService : ServiceForGeneric<Note> {

    private var notes = emptyArray<Note>()
    private var nId = 0
    private var coms = mutableListOf<Comment>()
    private var cid = 0

    override fun add(elem: Note): Note {
        notes += elem.copy(nId = ++nId)
        return notes.last()
    }

    override fun delete(elem: Note): Boolean {
        for ((index, deleted) in notes.withIndex()) {
            if (elem.nId == nId) {
                notes[index] = deleted.copy(
                    deleted.nId,
                    deleted.title,
                    deleted.text,
                    deleted.privacy,
                    deleted.commentPrivacy,
                    deleted.privacyView,
                    deleted.privacyComment,
                    deleted.comments,
                    deletedNotes = true
                )
                return true
            }
        }
        return false
    }

    @Throws(NoteWasDeletedException::class)
    override fun edit(elem: Note): Boolean {
        for ((index, oldNote) in notes.withIndex()) {
            if (elem.nId == oldNote.nId) {
                if (!oldNote.deletedNotes) {
                    notes[index] = oldNote.copy(
                        oldNote.nId,
                        oldNote.title,
                        oldNote.text,
                        oldNote.privacy,
                        oldNote.commentPrivacy,
                        oldNote.privacyView,
                        oldNote.privacyComment,
                        oldNote.comments,
                        oldNote.deletedNotes
                    )
                    return true
                }
            }
        }
        throw NoteWasDeletedException()
    }

    override fun restore(elem: Note): Boolean {
        for ((index, deleted) in notes.withIndex()) {
            if (elem.nId == nId) {
                notes[index] = deleted.copy(
                    deleted.nId,
                    deleted.title,
                    deleted.text,
                    deleted.privacy,
                    deleted.commentPrivacy,
                    deleted.privacyView,
                    deleted.privacyComment,
                    deleted.comments,
                    deletedNotes = false
                )
                return true
            }
        }
        return false
    }

    override fun get(): String {
        for ((index, elem) in notes.withIndex()) {
            if (!elem.deletedNotes) {
                println(elem)
            }
        }
        return "No notes"
    }


    fun getById(idToDelete: Int): String {
        for ((index, list) in notes.withIndex()) {
            if (idToDelete == list.nId) {
                if (!list.deletedNotes) {
                    var str = notes[index]
                    return str.toString()
                }
            }
        }
        throw NoteWasDeletedException()
    }


    @Throws(NoteWasDeletedException::class)
    fun createComment(elem: Note, com: Comment): Comment {
        for ((index, noteToComment) in notes.withIndex()) {
            if (elem.nId == noteToComment.nId) {
                if (!noteToComment.deletedNotes) {
                    coms += com.copy(cid = ++cid)
                    return coms.last()
                }
            }
        }
        throw NoteWasDeletedException()
    }

    @Throws(NoteWasDeletedException::class)
    fun deleteComment(elem: Note): Boolean {
        for ((index, noteToComment) in notes.withIndex()) {
            if (elem.nId == noteToComment.nId) {
                if (!noteToComment.deletedNotes) {
                    notes[index] = noteToComment.copy()
                    return true
                }
            }
        }
        throw NoteWasDeletedException()
    }

    @Throws(NoteWasDeletedException::class)
    fun restoreComment(elem: Note): Boolean {
        for ((index, noteToComment) in notes.withIndex()) {
            if (elem.nId == noteToComment.nId) {
                if (noteToComment.deletedNotes != true) {
                    notes[index] = noteToComment.copy(
                        noteToComment.nId,
                        noteToComment.title,
                        noteToComment.text,
                        noteToComment.privacy,
                        noteToComment.commentPrivacy,
                        noteToComment.privacyView,
                        noteToComment.privacyComment,
                        noteToComment.comments,
                        noteToComment.deletedNotes
                    )
                    return true
                }
            }
        }
        throw NoteWasDeletedException()
    }

    @Throws(NoteWasDeletedException::class)
    fun editComment(elem: Note): Boolean {
        for ((index, noteToComment) in notes.withIndex()) {
            if (elem.nId == noteToComment.nId) {
                if (!noteToComment.deletedNotes) {
                    notes[index] = elem.copy()
                    return true
                }
            }
        }
        throw NoteWasDeletedException()
    }

    fun getComment(): String {
        for ((index, elem) in coms.withIndex()) {
            if (!elem.deleteComment) {
                println(elem.message)
            }
        }
        return "No notes"
    }
}


fun main() {
}