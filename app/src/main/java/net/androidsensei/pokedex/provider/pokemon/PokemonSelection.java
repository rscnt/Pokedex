package net.androidsensei.pokedex.provider.pokemon;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import net.androidsensei.pokedex.provider.base.AbstractSelection;

/**
 * Selection for the {@code pokemon} table.
 */
public class PokemonSelection extends AbstractSelection<PokemonSelection> {
    @Override
    public Uri uri() {
        return PokemonColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PokemonCursor} object, which is positioned before the first entry, or null.
     */
    public PokemonCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PokemonCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public PokemonCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public PokemonCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public PokemonSelection id(long... value) {
        addEquals("pokemon." + PokemonColumns._ID, toObjectArray(value));
        return this;
    }


    public PokemonSelection name(String... value) {
        addEquals(PokemonColumns.NAME, value);
        return this;
    }

    public PokemonSelection nameNot(String... value) {
        addNotEquals(PokemonColumns.NAME, value);
        return this;
    }

    public PokemonSelection nameLike(String... value) {
        addLike(PokemonColumns.NAME, value);
        return this;
    }

    public PokemonSelection nameContains(String... value) {
        addContains(PokemonColumns.NAME, value);
        return this;
    }

    public PokemonSelection nameStartsWith(String... value) {
        addStartsWith(PokemonColumns.NAME, value);
        return this;
    }

    public PokemonSelection nameEndsWith(String... value) {
        addEndsWith(PokemonColumns.NAME, value);
        return this;
    }

    public PokemonSelection uuid(String... value) {
        addEquals(PokemonColumns.UUID, value);
        return this;
    }

    public PokemonSelection uuidNot(String... value) {
        addNotEquals(PokemonColumns.UUID, value);
        return this;
    }

    public PokemonSelection uuidLike(String... value) {
        addLike(PokemonColumns.UUID, value);
        return this;
    }

    public PokemonSelection uuidContains(String... value) {
        addContains(PokemonColumns.UUID, value);
        return this;
    }

    public PokemonSelection uuidStartsWith(String... value) {
        addStartsWith(PokemonColumns.UUID, value);
        return this;
    }

    public PokemonSelection uuidEndsWith(String... value) {
        addEndsWith(PokemonColumns.UUID, value);
        return this;
    }

    public PokemonSelection avatar(Integer... value) {
        addEquals(PokemonColumns.AVATAR, value);
        return this;
    }

    public PokemonSelection avatarNot(Integer... value) {
        addNotEquals(PokemonColumns.AVATAR, value);
        return this;
    }

    public PokemonSelection avatarGt(int value) {
        addGreaterThan(PokemonColumns.AVATAR, value);
        return this;
    }

    public PokemonSelection avatarGtEq(int value) {
        addGreaterThanOrEquals(PokemonColumns.AVATAR, value);
        return this;
    }

    public PokemonSelection avatarLt(int value) {
        addLessThan(PokemonColumns.AVATAR, value);
        return this;
    }

    public PokemonSelection avatarLtEq(int value) {
        addLessThanOrEquals(PokemonColumns.AVATAR, value);
        return this;
    }

    public PokemonSelection height(Double... value) {
        addEquals(PokemonColumns.HEIGHT, value);
        return this;
    }

    public PokemonSelection heightNot(Double... value) {
        addNotEquals(PokemonColumns.HEIGHT, value);
        return this;
    }

    public PokemonSelection heightGt(double value) {
        addGreaterThan(PokemonColumns.HEIGHT, value);
        return this;
    }

    public PokemonSelection heightGtEq(double value) {
        addGreaterThanOrEquals(PokemonColumns.HEIGHT, value);
        return this;
    }

    public PokemonSelection heightLt(double value) {
        addLessThan(PokemonColumns.HEIGHT, value);
        return this;
    }

    public PokemonSelection heightLtEq(double value) {
        addLessThanOrEquals(PokemonColumns.HEIGHT, value);
        return this;
    }

    public PokemonSelection weight(Double... value) {
        addEquals(PokemonColumns.WEIGHT, value);
        return this;
    }

    public PokemonSelection weightNot(Double... value) {
        addNotEquals(PokemonColumns.WEIGHT, value);
        return this;
    }

    public PokemonSelection weightGt(double value) {
        addGreaterThan(PokemonColumns.WEIGHT, value);
        return this;
    }

    public PokemonSelection weightGtEq(double value) {
        addGreaterThanOrEquals(PokemonColumns.WEIGHT, value);
        return this;
    }

    public PokemonSelection weightLt(double value) {
        addLessThan(PokemonColumns.WEIGHT, value);
        return this;
    }

    public PokemonSelection weightLtEq(double value) {
        addLessThanOrEquals(PokemonColumns.WEIGHT, value);
        return this;
    }
}
