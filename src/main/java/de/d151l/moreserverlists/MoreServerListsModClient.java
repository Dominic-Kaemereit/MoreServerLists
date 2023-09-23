package de.d151l.moreserverlists;

import de.d151l.moreserverlists.handler.ServerListHandler;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreServerListsModClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("moreserverlists");

    private static MoreServerListsModClient instance;

    private final ServerListHandler serverListHandler = new ServerListHandler(this);

    @Override
    public void onInitializeClient() {
        instance = this;

        LOGGER.info("MoreServerListsModClient initialized");
    }

    public static MoreServerListsModClient getInstance() {
        return instance;
    }

    public ServerListHandler getServerListHandler() {
        return serverListHandler;
    }
}
