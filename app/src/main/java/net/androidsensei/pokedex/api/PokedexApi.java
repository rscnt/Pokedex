package net.androidsensei.pokedex.api;

import com.android.volley.Response;

import net.androidsensei.pokedex.PokedexApplication;
import net.androidsensei.pokedex.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rscnt on 1/18/15.
 */
public class PokedexApi {

    private final static String BASE_PATH = "http://mi-pokedex.herokuapp.com";
    private final static String MI_POKEDEX_URL = BASE_PATH.concat("/api/pokemons");

    /**
     * @param onResponse An implemented interface of {Response.Listener} must accepts an
     *                   Pokemon[] instance as parameter;
     * @param onError An implemented interface of {Response.ErrorListener} */
    public static void getPokemons(Response.Listener onResponse, Response.ErrorListener onError) {
        GsonRequest<ResponsePokemon> request = new GsonRequest<>(MI_POKEDEX_URL,  ResponsePokemon.class, onResponse, onError);
        PokedexApplication.getInstance().addToRequestQueue(request);
    }
}
