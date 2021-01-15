package unit;

import com.google.gson.JsonObject;
import unit.data.TestConfiguration;
import net.programmer.igoodie.format.GsonGoodieFormat;
import net.programmer.igoodie.objectify.GoodieObjectifier;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.runtime.GoodiePrimitive;
import net.programmer.igoodie.sanitizer.LowercaseSanitizer;
import net.programmer.igoodie.schema.*;
import net.programmer.igoodie.validator.StringValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TouchTests {

    @Test
    public void test1() {
        GoodiePrimitive lowcased = GoodiePrimitive.from("lowcased-text");
        GoodiePrimitive upcased = GoodiePrimitive.from("UPCASED-TEXT");
        LowercaseSanitizer sanitizer = new LowercaseSanitizer();
        Assertions.assertEquals(lowcased, sanitizer.sanitize(lowcased));
        Assertions.assertNotEquals(upcased, sanitizer.sanitize(upcased));
    }

    @Test
    public void test2() {
        GoodieSchema<GoodiePrimitive> schema = new PrimitiveSchema("someProperty", "Foo")
                .withValidator(new StringValidator().withLength(0, 5))
                .withSanitizers(new LowercaseSanitizer());

        GoodiePrimitive goodie = GoodiePrimitive.from("Foobarbaz");

        SchematicResult<GoodiePrimitive> result = schema.check(goodie);
        Assertions.assertTrue(result.isModified());
        Assertions.assertEquals(result.getModified(), GoodiePrimitive.from("foo"));
    }

    @Test
    public void test3() {
        ObjectSchema schema = ObjectSchema.of(
                new PrimitiveSchema("someProperty", "Foo")
                        .withValidator(new StringValidator().withLength(0, 5))
                        .withSanitizers(new LowercaseSanitizer()),

                ObjectSchema.of("obj",
                        new PrimitiveSchema("someProperty", "Foo")
                                .withValidator(new StringValidator().withLength(0, 5))
                                .withSanitizers(new LowercaseSanitizer())
                )
        );

        GsonGoodieFormat format = new GsonGoodieFormat();
        JsonObject json = format.readFromString("{someProperty:'Foobarbaz',obj:{someProperty:'foob',otherProperty:999}}");
        GoodieObject goodie = format.writeToGoodie(json);

        SchematicResult<GoodieObject> result = schema.check(goodie);
        System.out.println(goodie);
        System.out.println(result.getModified());
        System.out.println(result.isModified());
    }

    @Test
    public void test4() {
        ObjectSchema schema = ObjectSchema.of(
                new ArraySchema("moderators", new PrimitiveSchema("$root", ""))
        );

        GsonGoodieFormat format = new GsonGoodieFormat();
        JsonObject json = format.readFromString("{moderators:'Fooo'}");
        GoodieObject goodie = format.writeToGoodie(json);

        SchematicResult<GoodieObject> result = schema.check(goodie);
        System.out.println(goodie);
        System.out.println(result.getModified());
        System.out.println(result.isModified());
    }

    @Test
    public void test5() throws IllegalAccessException, InstantiationException {
        GoodieObjectifier objectifier = new GoodieObjectifier();
        GsonGoodieFormat jsonFormat = new GsonGoodieFormat();
        JsonObject json = jsonFormat.readFromString("{username:'Foo',yearOfBirth:9999,subconfig:{flag:true}}");
        GoodieObject goodieObject = jsonFormat.writeToGoodie(json);

        TestConfiguration configuration = new TestConfiguration();
        objectifier.fillConfig(goodieObject, configuration);
    }

    @Test
    public void test6() throws IllegalAccessException, IOException, InstantiationException {
        TestConfiguration configuration = (TestConfiguration) new TestConfiguration().readAndFill();
//        System.out.println("Username = " + configuration.getUsername());
        System.out.println(configuration);
    }

}
