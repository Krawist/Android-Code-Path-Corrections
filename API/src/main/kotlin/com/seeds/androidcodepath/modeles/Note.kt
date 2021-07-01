package com.seeds.androidcodepath.modeles

import com.seeds.androidcodepath.modeles.Note.Companion.NOTE_TABLE_NAME
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = NOTE_TABLE_NAME)
data class Note(

    @Column(name = NOTE_NAME_FIELD)
    var noteName : String = "",

    @Column(name = NOTE_OVER_FIELD)
    var noteOver : Double = 0.0,

    @Column(name = NOTE_FIELD)
    var note : Double = 0.0,

    @Column(name = NOTE_TYPE_FIELD)
    var noteType : String = ""
) : Serializable {

    @Id
    @Column(name = NOTE_KEY_FIELD)
    var noteKey : Int = 0

    companion object{

        const val NOTE_TABLE_NAME = "note"
        const val NOTE_NAME_FIELD = "note_name"
        const val NOTE_OVER_FIELD = "note_over"
        const val NOTE_FIELD = "note_gave"
        const val NOTE_TYPE_FIELD = "note_type"
        const val NOTE_KEY_FIELD = "note_key"

    }

}
