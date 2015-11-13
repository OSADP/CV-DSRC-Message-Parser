package com.leidos.dataparser.io.formatting;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Interface/trait that generates a map of tagged data fields for output purposes
 */
public interface OutputData {
    default List<Field> getParsedValues() {
        // Look at the fancy Java 8. LOOK AT IT!
        return Arrays.asList(this.getClass().getDeclaredFields()).stream()
                .filter(field -> {
                    field.setAccessible(true);
                    // Validate the annotation

                    /**
                     * HERE BE ERRORS:
                     *
                     * Using field.getType() is to ignore the possibility of the underling object reference contained
                     * inside the field to be of a different type (be it subclass or superclass). Example, field is of
                     * type (interface) OSILevel2Header, which is not directly assignable to OutputData, but the
                     * underlying object reference may be of type OutputData.
                     */

                    try {
                        return (field.getAnnotation(Output.class) != null
                                // Validate the type of the annotated field
                                && (field.get(this) != null)
                                && (field.getType().isPrimitive()
                                || field.getType().equals(String.class)
                                || OutputData.class.isAssignableFrom(field.get(this).getClass())
                                || Collection.class.isAssignableFrom(field.get(this).getClass())
                                || Map.class.isAssignableFrom(field.get(this).getClass())
                                || field.getType().isArray()));
                    } catch (IllegalAccessException iae) {
                        return false;
                    }
                }).collect(Collectors.toList());
    }
}
