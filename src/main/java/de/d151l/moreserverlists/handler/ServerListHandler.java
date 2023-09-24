package de.d151l.moreserverlists.handler;

import de.d151l.moreserverlists.MoreServerListsModClient;

import java.util.HashMap;
import java.util.Map;

public class ServerListHandler {

    private final MoreServerListsModClient mod;

    private String currentServerList = "servers";

    private final Map<String, String> serverLists = new HashMap<>();

    public ServerListHandler(final MoreServerListsModClient mod) {
        this.mod = mod;
    }

    public void loadConfig() {
        this.mod.getConfigHandler().loadConfig();

        this.serverLists.putAll(this.mod.getConfigHandler().getConfig().getServerLists());
    }

    public void saveConfig() {
        this.mod.getConfigHandler().getConfig().getServerLists().clear();
        this.mod.getConfigHandler().getConfig().getServerLists().putAll(this.serverLists);

        this.mod.getConfigHandler().saveConfig();
    }

    public void addServerList(String key, String name) {
        if (key == null || key.isEmpty()) {
            return;
        }

        if (name == null || name.isEmpty()) {
            return;
        }

        if (this.getServerLists().containsKey(key)) {
            return;
        }

        key = key.toLowerCase();
        key = key.replaceAll("[^a-z0-9]", "");

        this.serverLists.put(key, name);
    }

    public void updateServerList(String key, String name) {
        if (key == null || key.isEmpty()) {
            return;
        }

        if (name == null || name.isEmpty()) {
            return;
        }

        this.serverLists.put(key, name);
    }

    public void removeServerList(String key) {
        this.serverLists.remove(key);
    }

    public void nextServerList() {
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
        return currentServerList;
    }

    public String getCurrentServerListName() {
        return this.serverLists.get(this.currentServerList);
    }

    public Map<String, String> getServerLists() {
        return serverLists;
    }
}
