package de.d151l.moreserverlists.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigMapper {

    private final Gson gson = new Gson();
    private final GsonBuilder gsonBuilder = new GsonBuilder();

    public <T> T readJson(File file, String fileName, Class<T> clazz) throws IOException {
        var targetFile = new File(file, fileName);
        if (!targetFile.exists()) throw new NullPointerException("Required file was not created yet!");
        var serialized = new String(Files.readAllBytes(targetFile.toPath()));
        return gson.fromJson(serialized, clazz);
    }

    public void writeJson(File parent, String fileName, Object object) throws IOException {
        if (!parent.exists()) parent.mkdirs();
        var config = new File(parent, fileName);
        if (!config.exists()) {
            config.createNewFile();
        }
        try {
            var fileWriter = new FileWriter(config);
            fileWriter.write(
                    gsonBuilder.disableHtmlEscaping().setPrettyPrinting().serializeNulls().create().toJson(object)
            );
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {

        }
    }

    public <T> T getOrCreate(File file, String fileName, Object object, Class<T> clazz) throws IOException {
        var targetFile = new File(file, fileName);
        if (!targetFile.exists()) {
            writeJson(file, fileName, object);
        }
        return readJson(file, fileName, clazz);
    }
}