package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import model.Game;

public class GameExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {

        return fieldAttributes.getDeclaringClass() == Game.class && fieldAttributes.getName().equals("tournaments");
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
