package net.programmer.igoodie.schema;

import net.programmer.igoodie.runtime.ConfigGoodie;

public class SchematicResult<T extends ConfigGoodie> {

    protected T input;
    protected T modified;

    protected boolean validated;
    protected boolean sanitized;

    public SchematicResult(T input) {
        this.input = input;
    }

    public void validatedTo(T modified) {
        this.validated = true;
        this.modified = modified;
    }

    public void sanitizedTo(T modified) {
        this.sanitized = true;
        this.modified = modified;
    }

    public T getInput() {
        return input;
    }

    public T getModified() {
        return modified;
    }

    public boolean isModified() {
        return this.modified != null;
    }

    public boolean isValidated() {
        return validated;
    }

    public boolean isSanitized() {
        return sanitized;
    }

    public static <T extends ConfigGoodie> SchematicResult<T> invalidType(T defaultValue) {
        SchematicResult<T> result = new SchematicResult<>(null);
        result.validatedTo(defaultValue);
        return result;
    }

}
