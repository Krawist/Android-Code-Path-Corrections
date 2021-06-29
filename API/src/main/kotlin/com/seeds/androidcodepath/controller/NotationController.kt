package com.seeds.androidcodepath.controller

import com.seeds.androidcodepath.`package`.isEmailAddress
import com.seeds.androidcodepath.modeles.Notation
import com.seeds.androidcodepath.modeles.NotationRepository
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

    @PostMapping("save")
    fun saveNotation(
        @RequestBody notation: Notation
    ) : RequestResult<Any?>{
        try {
            return when{
                notation.notationKey == null -> {
                    error(RESULT_CODE_BAD_INFORMATION, "La clé fournie n'est pas correcte")
                }

                notation.notationKey.codeApprenant.isBlank() -> {
                    error(RESULT_CODE_BAD_INFORMATION, "Le code de l'apprenant n'a pas été fourni")
                }

                notation.notationKey.codeWork.isBlank() ->{
                    error(RESULT_CODE_BAD_INFORMATION, "Le code du travail n'a pas été fourni")
                }

                notation.notationKey.userEmail.isBlank() || !isEmailAddress(notation.notationKey.userEmail)-> {
                    error(RESULT_CODE_BAD_INFORMATION, "L'adresse email n'a pas été fournie ou n'est pas correcte")
                }

                notation.qualiteRendu < 0 && notation.respectContraintes < 0 && notation.respectDetails < 0 -> {
                    error(RESULT_CODE_BAD_INFORMATION, "Au moins une note parmi qualité de rendu, respect des contraintes, respects des contraintes doit etre supérieur à 0")
                }

                else -> {
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