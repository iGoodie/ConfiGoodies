package net.programmer.igoodie;

import net.programmer.igoodie.format.GoodieFormat;
import net.programmer.igoodie.runtime.ConfigGoodie;
import net.programmer.igoodie.schema.ConfigSchema;
import net.programmer.igoodie.util.FileUtilities;

import java.io.File;
import java.io.IOException;

public abstract class GoodieConfiguration<E, G extends ConfigGoodie> {

    protected GoodieFormat<E, G> format;

    protected GoodieConfiguration(GoodieFormat<E, G> format) {
        this.format = format;
    }

    public abstract File getFile();

    public abstract ConfigSchema<G> getRootSchema();

    public ConfigGoodie readGoodies() throws IOException {
        File configFile = getFile();

        if (configFile.isDirectory())
            throw new IllegalStateException("Cannot read directories...");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
            G defaultValue = getRootSchema().getDefaultValue();
            E defaultExternal = format.deserializeGoodie(defaultValue);
            String text = format.writeToString(defaultExternal);
            FileUtilities.writeToFile(text, configFile);
        }

        String text = FileUtilities.stringFromFile(configFile);

        E externalConfig = format.readFromString(text);
        return format.serializeGoodie(externalConfig);
    }

}
