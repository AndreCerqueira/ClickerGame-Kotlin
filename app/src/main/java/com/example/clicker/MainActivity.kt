package com.example.clicker

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    // Models
    var charactersList: MutableList<Character> = arrayListOf()
    var dinheiro: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initial Settings
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variables
        val listViewCharacters = findViewById<ListView>(R.id.listViewCharacters)
        val monsterImage = findViewById<ImageView>(R.id.monster)
        val dinheiroTextView = findViewById<TextView>(R.id.tb_dinheiro)

        // Set data
        listViewCharacters.adapter = CharactersAdapter()
        charactersList.add(Character("Kazuma", R.drawable.kazuma, 1))
        charactersList.add(Character("Erza", R.drawable.erza, 5))
        charactersList.add(Character("Gon", R.drawable.gon, 15))

        // Per second function
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                //Call your function here
                handler.postDelayed(this, 1000)//1 sec delay
                dinheiro += getDPS()
                dinheiroTextView.text = dinheiro.toString()
            }
        }, 0)

        // Monster on click
        monsterImage.setOnClickListener {

            dinheiro += getDPC()
            dinheiroTextView.text = dinheiro.toString()
        }

    }


    /*
    *   This function gets the value of money per click
    */
    fun getDPC(): Int {
        return charactersList[0].nivel
    }


    /*
    *   This function gets the value of money per second
    */
    fun getDPS(): Int {

        var dps = 0

        for (character in charactersList) {
            if (character.nome != "Kazuma")
                dps += character.nivel
        }

        return dps - 2
    }


    /*
    *   Character rows
    */
    inner class CharactersAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return charactersList.size
        }

        override fun getItem(position: Int): Any {
            return charactersList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            // Variables
            val rowView = layoutInflater.inflate(R.layout.row_character, parent, false)
            val character = charactersList[position]

            // Object Variables
            val textViewNome = rowView.findViewById<TextView>(R.id.nome)
            val textViewNivel = rowView.findViewById<TextView>(R.id.nivel)
            val textViewCost = rowView.findViewById<TextView>(R.id.tb_custo)
            val buttonUpgrade = rowView.findViewById<TextView>(R.id.upgrade_bt)
            val imageView = rowView.findViewById<ImageView>(R.id.characterImageView)
            var textViewDinheiro = findViewById<TextView>(R.id.tb_dinheiro)

            // Set data
            textViewNome.text = character.nome
            textViewNivel.text = "Nivel " + character.nivel
            imageView.setImageResource(character.imageId)

            // Upgrade button
            buttonUpgrade.setOnClickListener {

                // Enough Money Verification
                if (dinheiro >= character.custo) {

                    // Calculations
                    dinheiro -= character.custo
                    character.nivel ++
                    character.custo = character.getCost()

                    // Set data
                    textViewDinheiro.text = dinheiro.toString()
                    textViewNivel.text = "Nivel " + character.nivel
                    textViewCost.text = "Custo: " + character.custo
                }
            }

            return rowView
        }
    }
}