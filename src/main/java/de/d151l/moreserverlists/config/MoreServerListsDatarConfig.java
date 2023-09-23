package de.d151l.moreserverlists.config;

import java.util.Map;

public class MoreServerListsDatarConfig {

    private static MoreServerListsDatarConfig instance;

    private Map<String, String> serverLists = Map.of(
            "servers", "Default Server List"
    );

    public static void setInstance(MoreServerListsDatarConfig instance) {
        MoreServerListsDatarConfig.instance = instance;
    }

    public static MoreServerListsDatarConfig getInstance() {
        return instance;
    }

    public Map<String, String> getServerLists() {
        return serverLists;
    }
}
