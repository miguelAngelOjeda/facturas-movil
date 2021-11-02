package coomecipar.example.palermo.models.auth

data class Token(var token_type: String, var access_token: String, var refresh_token: String, var expires_in: Int)