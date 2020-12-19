import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import data.TestConfiguration;
import net.programmer.igoodie.converter.GoodieToGson;
import net.programmer.igoodie.converter.GsonToGoodie;
import net.programmer.igoodie.runtime.GoodieArray;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.sanitizer.LowercaseSanitizer;
import net.programmer.igoodie.schema.ObjectSchema;
import net.programmer.igoodie.schema.PrimitiveSchema;
import net.programmer.igoodie.util.CommonPatterns;
import net.programmer.igoodie.validator.StringValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UsageTests {

    @Test
    public void test1() {
        GoodieObject goodie = new GoodieObject()
                .with("username", "iGoodie")
                .with("friends", GoodieArray.of("KaptainWutax", "JML"))
                .with("birthDate", 1997);

        System.out.println(goodie);
    }

    @Test
    public void test2() {
        ObjectSchema schema = ObjectSchema.of(
                new PrimitiveSchema("username", "<Your Nickname Here>")
                        .withValidator(new StringValidator().withLength(0, 20))
                        .withSanitizers(new LowercaseSanitizer()),

                new PrimitiveSchema("email", "<Your email here>")
                        .withValidator(new StringValidator().withPattern(CommonPatterns.EMAIL_PATTERN)),

                new PrimitiveSchema("birthDate", 2020)
                        .withValidator(new StringValidator().withLength(0, 20))
        );

        Assertions.assertThrows(InternalError.class, schema::getDefaultValue);
    }

    @Test
    public void test3() {
        JsonElement jsonElement = JsonParser.parseString("{a:123, b:[1,2,3,4,5,6], c:{foo:\"Bar\"}}");
        GoodieElement convertedGoodie = GsonToGoodie.convert(jsonElement);
        JsonElement convertedJson = GoodieToGson.convert(convertedGoodie);
        System.out.println(convertedGoodie);
        System.out.println(convertedJson);
    }

    @Test
    public void test4() throws IOException {
        TestConfiguration configuration = new TestConfiguration();
        configuration.readGoodies();
    }

}
