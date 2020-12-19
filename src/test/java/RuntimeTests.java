import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.PrimitiveGoodie;
import net.programmer.igoodie.validator.StringValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RuntimeTests {

    @Test
    public void test1() {
        PrimitiveGoodie goodie = new PrimitiveGoodie(true);
        Assertions.assertTrue(goodie.getBooleanValue());
        Assertions.assertEquals(goodie.getStringValue(), "true");
    }

    @Test
    public void test2() {
        StringValidator validator = new StringValidator().withLength(5, 10);
        Assertions.assertDoesNotThrow(() ->
                validator.validate("test", PrimitiveGoodie.from("12345")));
        Assertions.assertDoesNotThrow(() ->
                validator.validate("test", PrimitiveGoodie.from("123456")));

        Assertions.assertThrows(ValidationException.class, () ->
                validator.validate("test", PrimitiveGoodie.from("")));
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validate("test", PrimitiveGoodie.from("123")));
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validate("test", PrimitiveGoodie.from("12345678910")));
    }

}
