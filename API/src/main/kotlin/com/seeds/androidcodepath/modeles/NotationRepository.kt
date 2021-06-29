package com.seeds.androidcodepath.modeles

import org.springframework.data.jpa.repository.JpaRepository

interface NotationRepository : JpaRepository<Notation, NotationKey> {
}