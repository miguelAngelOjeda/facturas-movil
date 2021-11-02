package coomecipar.example.palermo.models.auth

/**
 * Created by miguel on 28/10/21.
 */
data class Usuario(var id: Int,
                   var username: String,
                   var nombre: String,
                   var apellido: String,
                   var email: String,
                   var idMovil: String) {
}
