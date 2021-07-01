package com.seeds.androidcodepath.controller

import com.seeds.androidcodepath.`package`.isEmailAddress
import com.seeds.androidcodepath.modeles.Notation
import com.seeds.androidcodepath.modeles.NotationRepository
import com.seeds.androidcodepath.modeles.NoteRepository
import fr.smartds.smartdsmarketplace.api.request_settings.RequestResult
import org.springframework.beans.factory.annotation.Autowired
import fr.smartds.smartdsmarketplace.api.request_settings.RequestResult.Companion.RESULT_CODE_ALL_IS_CORRECT
import fr.smartds.smartdsmarketplace.api.request_settings.RequestResult.Companion.RESULT_CODE_BAD_INFORMATION
import fr.smartds.smartdsmarketplace.api.request_settings.RequestResult.Companion.error
import fr.smartds.smartdsmarketplace.api.request_settings.RequestResult.Companion.success
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/notations")
class NotationController {

    @Autowired
    private var notationRepository : NotationRepository? = null

    @Autowired
    private var noteRepository : NoteRepository? = null

    @PostMapping("save")
    fun saveNotation(
        @RequestBody notation: Notation
    ) : RequestResult<Any?>{
        try {
            var allIsCorrect = true
            notation.notes?.forEach {
                if(it.note<0 || it.note>it.noteOver){
                    allIsCorrect = false
                }
            }
            if(!allIsCorrect){
                return error(RESULT_CODE_BAD_INFORMATION,"Les notes fournies nes sont pas correctes")
            }
            return when{

                notation.notes.isNullOrEmpty() ->{
                    error(RESULT_CODE_BAD_INFORMATION, "Les notes fournies nes sont pas correctes")
                }

                notation.codeApprenant.isBlank() -> {
                    error(RESULT_CODE_BAD_INFORMATION, "Le code de l'apprenant n'a pas été fourni")
                }

                notation.codeWork.isBlank() ->{
                    error(RESULT_CODE_BAD_INFORMATION, "Le code du travail n'a pas été fourni")
                }

                notation.userEmail.isBlank() || !isEmailAddress(notation.userEmail)-> {
                    error(RESULT_CODE_BAD_INFORMATION, "L'adresse email n'a pas été fournie ou n'est pas correcte")
                }

                else -> {
                    notation.notes.forEach {
                        it.noteKey = notation.key
                        noteRepository?.save(it)
                    }
                    val saveNotation = notationRepository?.save(notation)
                    success(saveNotation, RESULT_CODE_ALL_IS_CORRECT)
                }

            }
        }catch (e : Exception){
            return error(RESULT_CODE_BAD_INFORMATION,e.stackTraceToString())
        }
    }

    @GetMapping("all")
    fun getAllNotation() : RequestResult<Any?>{
        return try {
            val notations = notationRepository?.findAll()
            success(notations, RESULT_CODE_ALL_IS_CORRECT)
        }catch (e : Exception){
            error(RESULT_CODE_BAD_INFORMATION,e.stackTraceToString())
        }
    }
}