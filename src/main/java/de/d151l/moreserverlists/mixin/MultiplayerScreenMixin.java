package de.d151l.moreserverlists.mixin;

import de.d151l.moreserverlists.MoreServerListsModClient;
import de.d151l.moreserverlists.screen.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin extends Screen {

    private Screen parent;

    private Text title = Text.of("");

    private final int arrowWidth = 20;
    private final int listNameWidth = 130;
    private final int spaceBetweenListNameAndArrow = 5;
    private final int spaceBetweenWindowAndBar = 5;

    protected MultiplayerScreenMixin(final Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("INVOKE"))
    private void init(CallbackInfo info) {

        final int screenWidth = this.width;
        final int center = screenWidth / 2;

        final int barWidth = this.arrowWidth + this.spaceBetweenListNameAndArrow + this.listNameWidth + this.spaceBetweenListNameAndArrow + this.arrowWidth;
        final int centerBar = barWidth / 2;

        int start = center - centerBar;

        final ButtonWidget arrowLeft = ButtonWidget.builder(Text.of("«"), (buttonWidget) -> {
            MoreServerListsModClient.getInstance().getServerListHandler().previousServerList();

            MinecraftClient.getInstance().setScreen(new MultiplayerScreen(parent));
        }).width(this.arrowWidth).position(start, this.spaceBetweenWindowAndBar).build();

        final String currentServerListName = MoreServerListsModClient.getInstance().getServerListHandler().getCurrentServerListName();
        final ButtonWidget listName = ButtonWidget.builder(Text.of(currentServerListName), (buttonWidget) -> {

            final ConfigScreen configScreen = new ConfigScreen(parent);
            MinecraftClient.getInstance().setScreen(configScreen);
        }).width(this.listNameWidth).position(arrowLeft.getX() + arrowLeft.getWidth() + this.spaceBetweenListNameAndArrow, arrowLeft.getY()).build();

        final ButtonWidget arrowRight = ButtonWidget.builder(Text.of("»"), (buttonWidget) -> {
            MoreServerListsModClient.getInstance().getServerListHandler().nextServerList();

            MinecraftClient.getInstance().setScreen(new MultiplayerScreen(parent));
        }).width(this.arrowWidth).position(listName.getX() + listName.getWidth() + this.spaceBetweenListNameAndArrow, listName.getY()).build();

        this.addDrawableChild(arrowLeft);
        this.addDrawableChild(listName);
        this.addDrawableChild(arrowRight);
    }
}