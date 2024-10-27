package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import model.Tournament;

public class TournamentExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {

        return fieldAttributes.getDeclaringClass() == Tournament.class && (fieldAttributes.getName().equals("brackets") || fieldAttributes.getName().equals("teams"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
