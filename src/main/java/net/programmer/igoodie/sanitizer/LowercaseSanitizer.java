package net.programmer.igoodie.sanitizer;

import net.programmer.igoodie.runtime.GoodiePrimitive;

import java.util.Locale;

public class LowercaseSanitizer implements GoodieSanitizer<GoodiePrimitive> {

    protected Locale locale;

    public LowercaseSanitizer() { }

    public LowercaseSanitizer(Locale locale) {
        this.locale = locale;
    }

    @Override
    public GoodiePrimitive sanitize(GoodiePrimitive goodie) {
        String stringValue = goodie.getStringValue();
        return GoodiePrimitive.from(locale == null
                ? stringValue.toLowerCase()
                : stringValue.toLowerCase(locale));
    }

}
