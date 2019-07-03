package com.example.foodmenukotlin.payload


class JwtAuthenticationResponse(var accessToken: String?) {
    var tokenType = "Bearer"
}
