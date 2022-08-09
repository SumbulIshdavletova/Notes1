package notes

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NoteServiceTest {
    private val service = NoteService()

    @Test
    fun add() {
        var nId = 0
        val note = service.add(
            Note(
                nId = ++nId,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )
        val result = note.nId != 0
        assertEquals(true, result)
    }

    @Test
    fun delete() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )

        val result = service.delete(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg2", "guid3", false)),
                deletedNotes = true
            )
        )
        assertTrue(result)
    }

    @Test(expected = NoteWasDeletedException::class)
    fun editWithException() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = true
            )
        )

        service.edit(
            Note(
                nId = 1,
                title = "title3",
                text = "text3",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg2", "guid3", false)),
                deletedNotes = true
            )
        )
    }

    @Test
    fun editTrue() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )

        val result = service.edit(
            Note(
                nId = 1,
                title = "title3",
                text = "text3",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg2", "guid3", false)),
                deletedNotes = false
            )
        )
        assertTrue(result)
    }

    @Test
    fun restore() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = true
            )
        )

        val result = service.restore(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg2", "guid3", false)),
                deletedNotes = false
            )
        )
        assertTrue(result)
    }

    @Test
    fun get() {
        val service = NoteService()
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )

        service.add(
            Note(
                nId = 0,
                title = "tie",
                text = "t",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = true
            )
        )

        service.get()
    }


    @Test(expected = NoteWasDeletedException::class)
    fun getByIdException() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = true
            )
        )
        service.getById(1)

    }

    @Test
    fun getById() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )
        service.getById(1)
    }


    @Test
    fun createComment() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )

        service.createComment(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(1, 2, 2, "THE COMMENT", "guid3", false)),
                deletedNotes = false
            ), (Comment(1, 2, 2, "THE COMMENT", "guid3", false))
        )

    }

    @Test(expected = NoteWasDeletedException::class)
    fun deleteCommentException() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(1, 2, 2, "THE COMMENT", "guid3", true)),
                deletedNotes = true
            )
        )

        val result = service.deleteComment(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", true)),
                deletedNotes = false
            )
        )
        assertTrue(result)
    }

    @Test
    fun deleteComment() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(1, 2, 2, "THE COMMENT", "guid3", false)),
                deletedNotes = false
            )
        )

        val result = service.deleteComment(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(1, 2, 2, "THE COMMENT", "guid3", true)),
                deletedNotes = false
            )
        )
        assertTrue(result)
    }

    @Test
    fun restoreComment() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", true)),
                deletedNotes = false
            )
        )

        val result = service.restoreComment(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )
        assertTrue(result)
    }

    @Test(expected = NoteWasDeletedException::class)
    fun editCommentException() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = true
            )
        )

        val result = service.editComment(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 2, 2, "The comment", "g", false)),
                deletedNotes = false
            )
        )
        assertTrue(result)
    }

    @Test
    fun editComment() {
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )

        val result = service.editComment(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(1, 2, 2, "The comment", "g", false)),
                deletedNotes = false
            )
        )
        assertTrue(result)
    }

    @Test
    fun getComment() {
        val service = NoteService()
        service.add(
            Note(
                nId = 1,
                title = "title",
                text = "text",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = false
            )
        )

        service.add(
            Note(
                nId = 0,
                title = "tie",
                text = "t",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = "privacy",
                privacyComment = "privacy",
                comments = mutableListOf(Comment(0, 1, 1, "msg", "guid", false)),
                deletedNotes = true
            )
        )
        service.getComment()
    }

}

