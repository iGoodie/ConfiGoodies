package data;

import com.google.gson.JsonObject;
import net.programmer.igoodie.GoodieConfiguration;
import net.programmer.igoodie.annotation.Goodie;
import net.programmer.igoodie.format.GsonGoodieFormat;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.schema.ObjectSchema;
import net.programmer.igoodie.schema.PrimitiveSchema;
import net.programmer.igoodie.validator.StringValidator;

import java.io.File;
import java.util.List;

public class TestConfiguration extends GoodieConfiguration<JsonObject, GoodieObject> {

    @Goodie private String username;

    @Goodie private int yearOfBirth;

    @Goodie private List<String> moderators;

    @Goodie private TestSubconfig subconfig;

    @Goodie private List<TestSubconfig> subconfigList;

    private Object someInternalValue;
    public Object someInternalValue2;

    public TestConfiguration() {
        super(new GsonGoodieFormat());
    }

    public String getUsername() {
        return username;
    }

    public List<String> getModerators() {
        return moderators;
    }

    @Override
    public File getFile() {
        return new File("./test/test_config.json");
    }

//    @Override
//    public ObjectSchema getRootSchema() {
//        return ObjectSchema.of(
//                new PrimitiveSchema("username", "UsernameHere")
//                        .withValidator(new StringValidator()),
//                new PrimitiveSchema("yearOfBirth", 1880),
//                ObjectSchema.of("subconfig",
//                        new PrimitiveSchema("flag", false)
//                )
//        );
//    }

    @Override
    public String toString() {
        return "TestConfiguration{" +
                "username='" + username + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", moderators=" + moderators +
                ", subconfig=" + subconfig +
                ", subconfigList=" + subconfigList +
                ", someInternalValue=" + someInternalValue +
                ", someInternalValue2=" + someInternalValue2 +
                '}';
    }

}
