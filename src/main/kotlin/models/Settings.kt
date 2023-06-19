package models

class Settings(
    var create: Boolean = true,
    var god: Boolean = false,
    var screen: Float = 12.5f,
    var player: Float = 2f,
    var enemy: Float = 1.5f,
    var playerSpeed: Float = 95f,
    var enemySpeed: Float = 60f
)