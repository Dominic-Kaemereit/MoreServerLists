package de.d151l.moreserverlists.mixin;

import de.d151l.moreserverlists.MoreServerListsModClient;
import de.d151l.moreserverlists.screen.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin extends Screen {

    private Screen parent;

    private final int arrowWidth = 20;
    private final int listNameWidth = 150;
    private final int spaceBetweenListNameAndArrow = 3;
    private final int spaceBetweenWindowAndBar = 5;

    protected MultiplayerScreenMixin(final Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("INVOKE"))
    private void init(CallbackInfo info) {

        final int screenWidth = this.width;
        final int center = screenWidth / 2;
        //final int leftCenter = center / 2;

        final int barWidth = this.arrowWidth + this.spaceBetweenListNameAndArrow + this.arrowWidth + this.spaceBetweenListNameAndArrow + this.listNameWidth;
        final int centerBar = barWidth / 2;

        //int start = leftCenter - centerBar;

        final ButtonWidget arrowLeft = ButtonWidget.builder(Text.of("«"), (buttonWidget) -> {
            MoreServerListsModClient.getInstance().getServerListHandler().previousServerList();

            MinecraftClient.getInstance().setScreen(new MultiplayerScreen(parent));
        }).width(this.arrowWidth).position(this.spaceBetweenWindowAndBar, this.spaceBetweenWindowAndBar).build();

        final ButtonWidget arrowRight = ButtonWidget.builder(Text.of("»"), (buttonWidget) -> {
            MoreServerListsModClient.getInstance().getServerListHandler().nextServerList();

            MinecraftClient.getInstance().setScreen(new MultiplayerScreen(parent));
        }).width(this.arrowWidth).position(arrowLeft.getX() + arrowLeft.getWidth() + this.spaceBetweenListNameAndArrow, arrowLeft.getY()).build();

        final String currentServerListName = MoreServerListsModClient.getInstance().getServerListHandler().getCurrentServerListName();
        final int serverListIndex = MoreServerListsModClient.getInstance().getServerListHandler().getServerListIndex();
        final int maximumServerListIndex = MoreServerListsModClient.getInstance().getServerListHandler().getMaximumServerListIndex();

        final ButtonWidget listName = ButtonWidget.builder(Text.of(currentServerListName + " (" + serverListIndex + "/" + maximumServerListIndex + ")"), (buttonWidget) -> {

            final ConfigScreen configScreen = new ConfigScreen(parent);
            MinecraftClient.getInstance().setScreen(configScreen);
        }).width(this.listNameWidth).position(arrowRight.getX() + arrowRight.getWidth() + this.spaceBetweenListNameAndArrow, arrowRight.getY()).build();

        this.addDrawableChild(arrowLeft);
        this.addDrawableChild(arrowRight);
        this.addDrawableChild(listName);
    }
}