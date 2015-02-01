package net.androidsensei.pokedex.provider.pokemon;

import net.androidsensei.pokedex.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A pocket monster.
 */
public interface PokemonModel extends BaseModel {

    /**
     * The name of the pokemon, for instance: Charizard.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * UUID.
     * Cannot be {@code null}.
     */
    @NonNull
    String getUuid();

    /**
     * Get the {@code avatar} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getAvatar();

    /**
     * Get the {@code height} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getHeight();

    /**
     * Get the {@code weight} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getWeight();
}
