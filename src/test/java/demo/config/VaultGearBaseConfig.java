package demo.config;

import com.google.gson.JsonObject;
import demo.orm.GearBaseStats;
import net.programmer.igoodie.GoodieConfiguration;
import net.programmer.igoodie.annotation.Goodie;
import net.programmer.igoodie.format.GoodieFormat;
import net.programmer.igoodie.format.GsonGoodieFormat;
import net.programmer.igoodie.runtime.GoodieArray;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.schema.ArraySchema;
import net.programmer.igoodie.schema.GoodieSchema;
import net.programmer.igoodie.schema.ObjectSchema;
import net.programmer.igoodie.schema.PrimitiveSchema;
import net.programmer.igoodie.validator.IntegerValidator;
import net.programmer.igoodie.validator.StringValidator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VaultGearBaseConfig extends GoodieConfiguration<JsonObject, GoodieObject> {

    @Goodie private List<GearBaseStats> SWORD;
    @Goodie private List<GearBaseStats> AXE;
    @Goodie private List<GearBaseStats> HELM;

    protected VaultGearBaseConfig(GoodieFormat<JsonObject, GoodieObject> format) {
        super(format);
    }

    @Override
    public File getFile() {
        return new File("./test/vault_g_base.json");
    }

    @Override
    public GoodieSchema<GoodieObject> getRootSchema() {
        return ObjectSchema.of(
                getBaseStatSchema("SWORD"),
                getBaseStatSchema("AXE"),
                getBaseStatSchema("HELM")
        );
    }

    private GoodieSchema<GoodieArray> getBaseStatSchema(String field) {
        ObjectSchema baseStatSchema = ObjectSchema.of(
                new PrimitiveSchema("rarity", "common")
                        .withValidator(new StringValidator()),

                ObjectSchema.of(
                        "damage",
                        new PrimitiveSchema("min", 0)
                                .withValidator(new IntegerValidator()),
                        new PrimitiveSchema("max", 10)
                                .withValidator(new IntegerValidator())
                ),

                ObjectSchema.of(
                        "durability",
                        new PrimitiveSchema("min", 0)
                                .withValidator(new IntegerValidator()),
                        new PrimitiveSchema("max", 10)
                                .withValidator(new IntegerValidator())
                ),

                ObjectSchema.of(
                        "attackspeed",
                        new PrimitiveSchema("min", 0)
                                .withValidator(new IntegerValidator()),
                        new PrimitiveSchema("max", 10)
                                .withValidator(new IntegerValidator())
                )
        );

        return new ArraySchema(field, baseStatSchema)
                .withPreGeneratedValues(baseStatSchema.getDefaultValue());
    }

    @Override
    public String toString() {
        return "VaultGearBaseConfig{" +
                "SWORD=" + SWORD +
                ", AXE=" + AXE +
                ", HELM=" + HELM +
                '}';
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        VaultGearBaseConfig config = (VaultGearBaseConfig) new VaultGearBaseConfig(new GsonGoodieFormat()).readAndFill();

        System.out.println(config);
    }

}
