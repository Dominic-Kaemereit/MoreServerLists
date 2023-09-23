package de.d151l.moreserverlists.mixin;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import de.d151l.moreserverlists.MoreServerListsModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@Mixin(ServerList.class)
public class ServerListMixin {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final MinecraftClient client;
    private final List<ServerInfo> servers = Lists.newArrayList();
    private final List<ServerInfo> hiddenServers = Lists.newArrayList();

    public ServerListMixin(MinecraftClient client) {
        this.client = client;
    }

    public void loadFile() {
        try {
            this.servers.clear();
            this.hiddenServers.clear();
            NbtCompound nbtCompound = NbtIo.read(new File(this.client.runDirectory, this.getServerListName()));
            if (nbtCompound == null) {
                return;
            }

            NbtList nbtList = nbtCompound.getList("servers", 10);

            for(int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound2 = nbtList.getCompound(i);
                ServerInfo serverInfo = ServerInfo.fromNbt(nbtCompound2);
                if (nbtCompound2.getBoolean("hidden")) {
                    this.hiddenServers.add(serverInfo);
                } else {
                    this.servers.add(serverInfo);
                }
            }
        } catch (Exception var6) {
            LOGGER.error("Couldn't load server list", var6);
        }

    }

    public void saveFile() {
        try {
            NbtList nbtList = new NbtList();
            Iterator var2 = this.servers.iterator();

            ServerInfo serverInfo;
            NbtCompound nbtCompound;
            while(var2.hasNext()) {
                serverInfo = (ServerInfo)var2.next();
                nbtCompound = serverInfo.toNbt();
                nbtCompound.putBoolean("hidden", false);
                nbtList.add(nbtCompound);
            }

            var2 = this.hiddenServers.iterator();

            while(var2.hasNext()) {
                serverInfo = (ServerInfo)var2.next();
                nbtCompound = serverInfo.toNbt();
                nbtCompound.putBoolean("hidden", true);
                nbtList.add(nbtCompound);
            }

            NbtCompound nbtCompound2 = new NbtCompound();
            nbtCompound2.put("servers", nbtList);

            final String[] split = this.getServerListName().split("\\.");

            File file = File.createTempFile(split[0], "." + split[1], this.client.runDirectory);
            NbtIo.write(nbtCompound2, file);
            File file2 = new File(this.client.runDirectory, this.getServerListNameOld());
            File file3 = new File(this.client.runDirectory, this.getServerListName());
            Util.backupAndReplace(file3, file, file2);
        } catch (Exception var6) {
            LOGGER.error("Couldn't save server list", var6);
        }
    }

    public String getServerListName() {
        return MoreServerListsModClient.getInstance().getServerListHandler().getCurrentServerList() + ".dat";
    }

    public String getServerListNameOld() {
        return MoreServerListsModClient.getInstance().getServerListHandler().getCurrentServerList() + ".dat_old";
    }
}