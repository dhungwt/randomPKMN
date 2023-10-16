package com.example.randomcomic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random
import com.example.randomcomic.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var pkmnName = ""
    var pkmnGenus = ""
    var pkmnFact = ""
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)

        val button = activityMainBinding.button
        button.setOnClickListener {

            getPkmn()
            Log.d("pkmn", "pkmn set")
        }
    }


    private fun getPkmn() {
        val client = AsyncHttpClient()
        val  randoNum = Random.nextInt(1, 898)
        Log.d("random number generated", "number generated: $randoNum")

        client["https://pokeapi.co/api/v2/pokemon-species/$randoNum/?language=en", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {

                var resultJSON = json.jsonObject
                Log.d("pkmn retrieval", "Result JSON: $resultJSON")

                pkmnName = resultJSON.getString("name")
                Log.d("pkmn name", "Pokémon Name: $pkmnName")
                activityMainBinding.pkmnNameTextView.text = pkmnName

                var genusArray = resultJSON.getJSONArray("genera")
                pkmnGenus = genusArray.getJSONObject(0).getString("genus")
                Log.d("pkmn genus", "Pokémon Genus: $pkmnGenus")
                activityMainBinding.genusTextView.text =  pkmnGenus

                var flavorTextEntries = resultJSON.getJSONArray("flavor_text_entries")
                var factEntry = flavorTextEntries.getJSONObject(0)
                pkmnFact = factEntry.getString("flavor_text")
                Log.d("pkmn fact", "Pokémon Fact: $pkmnFact")
                activityMainBinding.factTextView.text =  pkmnFact
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("pkmn Error", errorResponse)
            }
        }]
    }



}