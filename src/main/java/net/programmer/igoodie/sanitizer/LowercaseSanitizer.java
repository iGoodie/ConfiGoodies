package net.programmer.igoodie.sanitizer;

import net.programmer.igoodie.runtime.PrimitiveGoodie;

import java.util.Locale;

public class LowercaseSanitizer extends ConfigSanitizer<PrimitiveGoodie> {

    protected Locale locale;

    public LowercaseSanitizer() { }

    public LowercaseSanitizer(Locale locale) {
        this.locale = locale;
    }

    @Override
    public PrimitiveGoodie sanitize(PrimitiveGoodie input) {
        String stringValue = input.getStringValue();
        return PrimitiveGoodie.of(locale == null
                ? stringValue.toLowerCase()
                : stringValue.toLowerCase(locale));
    }

}
