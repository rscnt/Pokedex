package net.androidsensei.pokedex.api;

import net.androidsensei.pokedex.models.Pokemon;

import java.util.List;

/**
 * Created by rscnt on 1/18/15.
 */
public class ResponsePokemon {
    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> pokemons;
}
