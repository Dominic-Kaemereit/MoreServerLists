package de.d151l.moreserverlists;

import de.d151l.moreserverlists.handler.ConfigHandler;
import de.d151l.moreserverlists.handler.ServerListHandler;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreServerListsModClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("moreserverlists");

    private static MoreServerListsModClient instance;

    private ServerListHandler serverListHandler;
    private ConfigHandler configHandler;

    @Override
    public void onInitializeClient() {
        instance = this;

        this.configHandler = new ConfigHandler(this);
        this.serverListHandler = new ServerListHandler(this);

        this.serverListHandler.loadConfig();

        LOGGER.info("MoreServerListsModClient initialized");
    }

    public static MoreServerListsModClient getInstance() {
        return instance;
    }

    public ServerListHandler getServerListHandler() {
        return serverListHandler;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
