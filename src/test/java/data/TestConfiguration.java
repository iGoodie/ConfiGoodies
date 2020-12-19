package data;

import com.google.gson.JsonObject;
import net.programmer.igoodie.GoodieConfiguration;
import net.programmer.igoodie.format.GsonGoodieFormat;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.schema.ObjectSchema;
import net.programmer.igoodie.schema.PrimitiveSchema;

import java.io.File;

public class TestConfiguration extends GoodieConfiguration<JsonObject, GoodieObject> {

    public TestConfiguration() {
        super(new GsonGoodieFormat());
    }

    @Override
    public File getFile() {
        return new File("./test/test_config.json");
    }

    @Override
    public ObjectSchema getRootSchema() {
        return ObjectSchema.of(
                new PrimitiveSchema("TestValue", "Actually Test Data!")
        );
    }

}
