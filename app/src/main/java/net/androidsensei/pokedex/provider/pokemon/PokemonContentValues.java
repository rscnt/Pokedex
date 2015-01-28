package net.androidsensei.pokedex.provider.pokemon;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.androidsensei.pokedex.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code pokemon} table.
 */
public class PokemonContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PokemonColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PokemonSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * The name of the pokemon, for instance. Charizard.
     */
    public PokemonContentValues putName(@Nullable String value) {
        mContentValues.put(PokemonColumns.NAME, value);
        return this;
    }

    public PokemonContentValues putNameNull() {
        mContentValues.putNull(PokemonColumns.NAME);
        return this;
    }


    /**
     * UUID.
     */
    public PokemonContentValues putUuid(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("uuid must not be null");
        mContentValues.put(PokemonColumns.UUID, value);
        return this;
    }



    public PokemonContentValues putAvatar(@Nullable Integer value) {
        mContentValues.put(PokemonColumns.AVATAR, value);
        return this;
    }

    public PokemonContentValues putAvatarNull() {
        mContentValues.putNull(PokemonColumns.AVATAR);
        return this;
    }


    public PokemonContentValues putHeight(@Nullable Double value) {
        mContentValues.put(PokemonColumns.HEIGHT, value);
        return this;
    }

    public PokemonContentValues putHeightNull() {
        mContentValues.putNull(PokemonColumns.HEIGHT);
        return this;
    }


    public PokemonContentValues putWeight(@Nullable Double value) {
        mContentValues.put(PokemonColumns.WEIGHT, value);
        return this;
    }

    public PokemonContentValues putWeightNull() {
        mContentValues.putNull(PokemonColumns.WEIGHT);
        return this;
    }

}
