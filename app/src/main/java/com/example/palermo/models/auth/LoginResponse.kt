package coomecipar.example.palermo.models.auth

data class LoginResponse(var usuario: Usuario,
                         var token: ApiToken)

data class ApiToken(var access_token: String = "",
                    var refresh_token: String = "",
                    var expires_in: Int = 0) {
}