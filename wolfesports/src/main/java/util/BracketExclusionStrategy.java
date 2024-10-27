package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import model.Bracket;

public class BracketExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {

        return fieldAttributes.getDeclaringClass() == Bracket.class && fieldAttributes.getName().equals("tournament");
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
