package net.programmer.igoodie.schema;

import net.programmer.igoodie.runtime.GoodieArray;
import net.programmer.igoodie.runtime.GoodieElement;

public class ArraySchema extends GoodieSchema<GoodieArray> {

    public ArraySchema(String propertyName) {
        super(propertyName);
    }

    @Override
    public GoodieArray getDefaultValue() {
        return new GoodieArray();
    }

    @Override
    public SchematicResult<GoodieArray> check(GoodieArray goodie) {
        GoodieArray copied = (GoodieArray) goodie.deepCopy();
        SchematicResult<GoodieArray> result = new SchematicResult<>(copied);

        GoodieArray validated = validate(goodie);
        if (!validated.equals(goodie)) {
            result.validatedTo(validated);
            return result;
        }

        GoodieArray sanitized = sanitize(goodie);
        if (!sanitized.equals(goodie)) {
            result.sanitizedTo(sanitized);
            return result;
        }

        return result;
    }

}
