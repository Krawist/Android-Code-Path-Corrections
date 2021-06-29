package cm.seeds.appcorrectionandroidpath.helper

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import cm.seeds.appcorrectionandroidpath.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Methode utilitaire permettant d'afficher n'importe qu'elle vue
 */
fun View.show() {
    this.visibility = View.VISIBLE
}


/**
 * Methode utilitaire permettant de cacher n'importe qu'elle vue
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * Charge une image dans un imageView en utilisant la référence de celui ci
 * @param imageView
 * @param imageReference
 * @param isCircle
 */
fun loadImageInView(
    imageView: ImageView,
    imageReference: Any?,
    defaultResourceId: Int = R.drawable.android,
    isCircle: Boolean = false
) {

    var glideManger = when (imageReference) {

        is String, is Uri -> Glide.with(imageView).load(imageReference).error(defaultResourceId)

        is Int -> Glide.with(imageView).load(imageReference).error(defaultResourceId)

        else -> null
    }

    if (glideManger != null) {

        if (isCircle) {
            glideManger = glideManger.circleCrop()
        }

        glideManger = glideManger.transition(DrawableTransitionOptions.withCrossFade())

        glideManger.into(imageView)

    }
}

/**
 * Affiche une texte à l'utilisateur sous forme de boite de dialogue
 */
fun showMessage(context: Context, title: String? = null, message: String?) {

    val actionListener = DialogInterface.OnClickListener { dialog, which ->
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> dialog.dismiss()
        }
    }

    MaterialAlertDialogBuilder(context).apply {
        background = ContextCompat.getDrawable(context, R.drawable.dialog_background)
        if(!title.isNullOrBlank()){
            setTitle(title)
        }
        setMessage(message)
        setPositiveButton("OK", actionListener)
        show()
    }
}

/**
 * Retourne la boite une boite de dialog présentant une interface d'attente
 */
fun getLoadingDialog(context: Context): Dialog {
    val dialog = Dialog(context)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.loading_dialog_layout)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog
}

/**
 * Affiche un Toast
 */
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}