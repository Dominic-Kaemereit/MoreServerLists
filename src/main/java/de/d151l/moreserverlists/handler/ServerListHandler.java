package de.d151l.moreserverlists.handler;

import de.d151l.moreserverlists.MoreServerListsModClient;

import java.util.Map;

public class ServerListHandler {

    private final MoreServerListsModClient mod;

    private String currentServerList = "servers";

    private final Map<String, String> serverLists = Map.of(
        "servers", "Default Server List",
        "servers2", "Second Server List"
    );

    public ServerListHandler(final MoreServerListsModClient mod) {
        this.mod = mod;
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
}
