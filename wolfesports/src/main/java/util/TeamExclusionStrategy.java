package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import model.Team;

public class TeamExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {

        return fieldAttributes.getDeclaringClass() == Team.class && (fieldAttributes.getName().equals("tournaments") || fieldAttributes.getName().equals("players"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
