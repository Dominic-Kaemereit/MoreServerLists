package de.d151l.moreserverlists.screen;

import de.d151l.moreserverlists.MoreServerListsModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class ConfigScreen extends Screen {

    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Text.of("MoreServerLists Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        final AtomicInteger startHeight = new AtomicInteger(60);
        final int center = this.width / 2;

        MoreServerListsModClient.getInstance().getServerListHandler().getServerLists().forEach((key, name) -> {
            final TextWidget textWidget = new TextWidget(center - 200, startHeight.get(), 200, 20, Text.of(key), this.textRenderer);

            final TextFieldWidget textFieldWidget = new TextFieldWidget(this.textRenderer, this.width / 2, startHeight.get(), 200, 20, Text.of(name));
            textFieldWidget.setMaxLength(24);
            textFieldWidget.setText(name);

            if (!key.equals("servers")) {
                final ButtonWidget removeButton = ButtonWidget.builder(Text.of("Remove"), (buttonWidget) -> {
                    MoreServerListsModClient.getInstance().getServerListHandler().removeServerList(key);
                    MinecraftClient.getInstance().setScreen(new ConfigScreen(parent));
                }).width(50).position(textFieldWidget.getX() + textFieldWidget.getWidth() + 5, textFieldWidget.getY()).build();
                this.addDrawableChild(removeButton);
            }

            textFieldWidget.setChangedListener((string) -> {
                MoreServerListsModClient.getInstance().getServerListHandler().updateServerList(key, string);
            });

            this.addDrawableChild(textWidget);
            this.addDrawableChild(textFieldWidget);
            startHeight.set(startHeight.get() + 25);
        });


        final int addServerListHeight = startHeight.get() + 50;
        //feals (key, name) and button
        final TextFieldWidget addListKey = new TextFieldWidget(this.textRenderer, (this.width / 2) - 205, addServerListHeight, 200, 20, Text.of(""));
        addListKey.setMaxLength(8);
        addListKey.setPlaceholder(Text.of("Key"));

        final TextFieldWidget addListName = new TextFieldWidget(this.textRenderer, (this.width / 2), addListKey.getY(), 200, 20, Text.of(""));
        addListName.setMaxLength(24);
        addListName.setPlaceholder(Text.of("Name"));

        final ButtonWidget addListButton = ButtonWidget.builder(Text.of("Add"), (buttonWidget) -> {
            MoreServerListsModClient.getInstance().getServerListHandler().addServerList(addListKey.getText(), addListName.getText());
            MinecraftClient.getInstance().setScreen(new ConfigScreen(parent));
        }).width(50).position(addListName.getX() + addListName.getWidth() + 5, addListName.getY()).build();

        this.addDrawableChild(addListKey);
        this.addDrawableChild(addListName);
        this.addDrawableChild(addListButton);

        final ButtonWidget doneButton = ButtonWidget.builder(Text.of("Save & Done"), (buttonWidget) -> {
            MoreServerListsModClient.getInstance().getServerListHandler().saveConfig();
            MinecraftClient.getInstance().setScreen(parent);
        }).width(160).position(center - 80, this.height - 32).build();

        this.addDrawableChild(doneButton);
    }
}
