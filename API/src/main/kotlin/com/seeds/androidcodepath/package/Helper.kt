package com.seeds.androidcodepath.`package`

import java.util.regex.Pattern

/**
 * Vérifie si la chaine passée en paramètre est effectivement une adresse mail
 * @param string la possible addresse mail
 * @return [true] si [string] est une adresse mail et [false] sinon
 */
fun isEmailAddress(string: String?) : Boolean{
    val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"

    val pat = Pattern.compile(emailRegex)
    return if(string == null || string=="")
        false
    else pat.matcher(string).matches()
}