package com.seeds.androidcodepath.modeles

import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {
}