package net.programmer.igoodie.schema;

import net.programmer.igoodie.runtime.GoodieArray;
import net.programmer.igoodie.runtime.GoodieElement;

import java.util.Arrays;

public class ArraySchema extends GoodieSchema<GoodieArray> {

    protected GoodieSchema<? extends GoodieElement> elementSchema;
    protected GoodieArray defaultValue;

    public ArraySchema(String propertyName, GoodieSchema<? extends GoodieElement> elementSchema) {
        super(propertyName);
        this.elementSchema = elementSchema;
    }

    @Override
    public GoodieArray getDefaultValue() {
        return defaultValue == null ? new GoodieArray() : defaultValue.deepCopy();
    }

    public ArraySchema withPreGeneratedValues(GoodieElement... elements) {
        GoodieArray defaultArray = new GoodieArray();
        defaultArray.addAll(Arrays.asList(elements));
        this.defaultValue = defaultArray;

        return this;
    }

    @Override
    public SchematicResult<GoodieArray> check(GoodieArray goodie) {
        GoodieArray copied = goodie.deepCopy();
        SchematicResult<GoodieArray> result = new SchematicResult<>(goodie);

        GoodieSchema<GoodieElement> schema = (GoodieSchema<GoodieElement>) this.elementSchema;

        for (int i = 0; i < copied.size(); i++) {
            GoodieElement element = copied.get(i);

            if (!GoodieSchema.matchesType(this.elementSchema, element)) {
                copied.set(i, this.elementSchema.getDefaultValue());
                result.validatedTo(copied);
                continue;
            }

            SchematicResult<GoodieElement> checkResult = schema.check(element.deepCopy());

            if (checkResult.isModified()) {
                copied.set(i, checkResult.getModified().deepCopy());
                if (checkResult.isValidated())
                    result.validatedTo(copied);
                else if (checkResult.isSanitized())
                    result.sanitizedTo(copied);
            }
        }

        return result;
    }

}
