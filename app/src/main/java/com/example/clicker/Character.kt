package com.example.clicker

class Character {

    val nome: String
    val imageId: Int
    var nivel: Int = 1
    var custo: Int = 0
    var costModifier: Int


    constructor(nome: String, imageId: Int, costModifier: Int) {

        this.nome = nome
        this.imageId = imageId
        this.costModifier = costModifier
    }

    fun getCost(): Int {

        if (nivel/2 < 1)
            return nivel * costModifier
        else
            return (nivel * (nivel/2)) * costModifier
    }

}