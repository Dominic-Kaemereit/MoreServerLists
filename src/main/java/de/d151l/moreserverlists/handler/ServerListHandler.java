package de.d151l.moreserverlists.handler;

import de.d151l.moreserverlists.MoreServerListsModClient;
import de.d151l.moreserverlists.config.ConfigMapper;
import de.d151l.moreserverlists.config.MoreServerListsDatarConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerListHandler {

    private final MoreServerListsModClient mod;

    private String currentServerList = null;

    private final Map<String, String> serverLists = new HashMap<>();

    public ServerListHandler(final MoreServerListsModClient mod) {
        this.mod = mod;
    }

    public void loadConfig() {
        MoreServerListsModClient.LOGGER.info("Loading config");
        try {
            MoreServerListsDatarConfig.setInstance(
                    new ConfigMapper().getOrCreate(new File("."), "moreserverlists.json", new MoreServerListsDatarConfig(), MoreServerListsDatarConfig.class)
            );

            this.serverLists.putAll(MoreServerListsDatarConfig.getInstance().getServerLists());
            this.currentServerList = (String) this.serverLists.keySet().toArray()[0];
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addServerList(String key, String name) {
        this.serverLists.put(key, name);
    }

    public void removeServerList(String key) {
        this.serverLists.remove(key);
    }

    public void nextServerList() {
        if (this.currentServerList == null) {
            this.loadConfig();
            return;
        }

        int index = 0;
        for (String key : this.serverLists.keySet()) {
            if (key.equals(this.currentServerList)) {
                break;
            }
            index++;
        }

        int searchingIndex = index + 1;
        if (searchingIndex >= this.serverLists.size()) {
            searchingIndex = 0;
        }

        this.currentServerList = (String) this.serverLists.keySet().toArray()[searchingIndex];
    }

    public void previousServerList() {
        if (this.currentServerList == null) {
            this.loadConfig();
            return;
        }

        int index = 0;
        for (String key : this.serverLists.keySet()) {
            if (key.equals(this.currentServerList)) {
                break;
            }
            index++;
        }

        int searchingIndex = index - 1;
        if (searchingIndex < 0) {
            searchingIndex = this.serverLists.size() - 1;
        }

        this.currentServerList = (String) this.serverLists.keySet().toArray()[searchingIndex];
    }

    public String getCurrentServerList() {
        if (this.currentServerList == null) {
            this.loadConfig();
        }

        return currentServerList;
    }

    public String getCurrentServerListName() {
        if (this.currentServerList == null) {
            this.loadConfig();
        }

        return this.serverLists.get(this.currentServerList);
    }
}
