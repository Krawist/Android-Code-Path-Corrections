package cm.seeds.appcorrectionandroidpath.modeles

data class Notation(
    var notationKey: NotationKey? = NotationKey(),
    var qualiteRendu: Float = 0f,
    var respectDetails: Float = 0f,
    var respectContraintes: Float = 0f
)