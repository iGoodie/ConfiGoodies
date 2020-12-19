import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.programmer.igoodie.converter.GsonToGoodie;
import net.programmer.igoodie.runtime.ArrayGoodie;
import net.programmer.igoodie.runtime.ObjectGoodie;
import net.programmer.igoodie.sanitizer.LowercaseSanitizer;
import net.programmer.igoodie.schema.ObjectSchema;
import net.programmer.igoodie.schema.PrimitiveSchema;
import net.programmer.igoodie.validator.StringValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsageTests {

    @Test
    public void test1() {
        ObjectGoodie goodie = new ObjectGoodie()
                .with("username", "iGoodie")
                .with("friends", ArrayGoodie.of("KaptainWutax", "JML"))
                .with("birthDate", 1997);

        System.out.println(goodie);
    }

    @Test
    public void test2() {
        ObjectSchema schema = ObjectSchema.of(
                new PrimitiveSchema("username", "<Your Nickname Here>")
                        .withValidator(new StringValidator().withLength(0, 20))
                        .withSanitizers(new LowercaseSanitizer()),

                new PrimitiveSchema("birthDate", 2020)
                        .withValidator(new StringValidator().withLength(0, 20))
        );

        Assertions.assertThrows(InternalError.class, schema::getDefaultValue);
    }

    @Test
    public void test3() {
        JsonElement jsonElement = JsonParser.parseString("{a:123, b:[1,2,3,4,5,6], c:{foo:\"Bar\"}}");
        System.out.println(GsonToGoodie.convert(jsonElement));
    }

}
