package net.androidsensei.pokedex.provider.pokemon;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.androidsensei.pokedex.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code pokemon} table.
 */
public class PokemonCursor extends AbstractCursor implements PokemonModel {
    public PokemonCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public long getId() {
        return getLongOrNull(PokemonColumns._ID);
    }

    /**
     * The name of the pokemon, for instance. Charizard.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        return getStringOrNull(PokemonColumns.NAME);
    }

    /**
     * UUID.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getUuid() {
        String res = getStringOrNull(PokemonColumns.UUID);
        if (res == null)
            throw new NullPointerException("The value of 'uuid' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code avatar} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getAvatar() {
        return getIntegerOrNull(PokemonColumns.AVATAR);
    }

    /**
     * Get the {@code height} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getHeight() {
        return getDoubleOrNull(PokemonColumns.HEIGHT);
    }

    /**
     * Get the {@code weight} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getWeight() {
        return getDoubleOrNull(PokemonColumns.WEIGHT);
    }
}
