package de.d151l.moreserverlists.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.d151l.moreserverlists.MoreServerListsModClient;
import de.d151l.moreserverlists.config.MoreServerListsDatarConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigHandler {

    private MoreServerListsDatarConfig config;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String configFileName = "moreserverlistsconfig.json";
    private final File configDir = new File("config/");


    public ConfigHandler() {

    }

    public void loadConfig() {
        MoreServerListsModClient.LOGGER.info("Loading config...");
        final File file = new File(this.configDir, this.configFileName);

        if (!file.exists()) {
            MoreServerListsModClient.LOGGER.info("Config file not found, creating new one...");
            this.config = new MoreServerListsDatarConfig();
            this.config.getServerLists().put("servers", "Default Server List");

            final boolean mkdirs = file.getParentFile().mkdirs();
            MoreServerListsModClient.LOGGER.info("mkdirs: " + mkdirs);

            this.saveConfig();
            return;
        }

        try {
            this.config = this.gson.fromJson(new String(Files.readAllBytes(file.toPath())), MoreServerListsDatarConfig.class);
        } catch (IOException e) {
            MoreServerListsModClient.LOGGER.error("Failed to load config!", e);
        }

        MoreServerListsModClient.LOGGER.info("Config loaded!");
    }

    public void saveConfig() {
        MoreServerListsModClient.LOGGER.info("Saving config...");
        final File file = new File(this.configDir, this.configFileName);

        try {
            final FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(this.gson.toJson(this.config));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException exception) {
            MoreServerListsModClient.LOGGER.error("Failed to save config!", exception);
        }
        MoreServerListsModClient.LOGGER.info("Config saved!");
    }

    public MoreServerListsDatarConfig getConfig() {
        return config;
    }
}
