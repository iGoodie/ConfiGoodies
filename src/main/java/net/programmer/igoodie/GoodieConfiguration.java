package net.programmer.igoodie;

import net.programmer.igoodie.format.GoodieFormat;
import net.programmer.igoodie.objectify.GoodieObjectifier;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.schema.GoodieSchema;
import net.programmer.igoodie.schema.SchematicResult;
import net.programmer.igoodie.util.FileUtilities;

import java.io.File;
import java.io.IOException;

public abstract class GoodieConfiguration<E, G extends GoodieElement> {

    protected GoodieFormat<E, G> format;

    protected GoodieConfiguration(GoodieFormat<E, G> format) {
        this.format = format;
    }

    public abstract File getFile();

    public GoodieSchema<G> getRootSchema() {
        return null;
    }

    public G readGoodies() throws IOException {
        File configFile = getFile();

        if (configFile.isDirectory())
            throw new IllegalStateException("Cannot read directories...");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
            G defaultValue = getRootSchema().getDefaultValue();
            E defaultExternal = format.readFromGoodie(defaultValue);
            String text = format.writeToString(defaultExternal);
            FileUtilities.writeToFile(text, configFile);
        }

        String text = FileUtilities.stringFromFile(configFile);

        E externalConfig = format.readFromString(text);
        return format.writeToGoodie(externalConfig);
    }

    public GoodieConfiguration readAndFill() throws IOException, InstantiationException, IllegalAccessException {
        G readGoodies = readGoodies();
        GoodieSchema<G> rootSchema = getRootSchema();

        SchematicResult<G> result = rootSchema != null
                ? rootSchema.check(readGoodies) : null;

        GoodieObjectifier objectifier = new GoodieObjectifier();
        File targetFile = getFile();
        File oldFile = new File(targetFile.getParentFile().getPath() + File.separator + targetFile.getName() + ".old");

        if (result != null && result.isModified()) {
            if (targetFile.exists()) FileUtilities.writeToFile(format.writeToString(result.getInput()), oldFile);
            FileUtilities.writeToFile(format.writeToString(result.getModified()), targetFile);
            objectifier.fillConfig((GoodieObject) result.getModified(), this);
        } else {
            G initialGoodie = (G) readGoodies.deepCopy();
            if (objectifier.fillConfig((GoodieObject) readGoodies, this)) {
                if (targetFile.exists()) FileUtilities.writeToFile(format.writeToString(initialGoodie), oldFile);
                FileUtilities.writeToFile(format.writeToString(readGoodies), targetFile);
            }
        }

        return this;
    }

}
