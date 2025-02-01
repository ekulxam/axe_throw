package survivalblock.axe_throw.common.compat.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.EnumControllerBuilderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import survivalblock.axe_throw.common.AxeThrow;

public class AxeThrowYACLCompat {

    public static Screen create(Screen parent){
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("axe_throw.yacl.category.main"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("axe_throw.yacl.category.main"))
                        .tooltip(Text.translatable("axe_throw.yacl.category.main.tooltip"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("axe_throw.yacl.group.client"))
                                .option(Option.<ThrowingDisplayMode>createBuilder()
                                        .name(Text.translatable("axe_throw.yacl.option.enum.throwingDisplayMode"))
                                        .description(OptionDescription.of(Text.translatable("axe_throw.yacl.option.enum.throwingDisplayMode.desc")))
                                        .binding(AxeThrowYACLCompat.HANDLER.defaults().throwingDisplayMode, () -> AxeThrowYACLCompat.HANDLER.instance().throwingDisplayMode, newVal -> AxeThrowYACLCompat.HANDLER.instance().throwingDisplayMode = newVal)
                                        .controller(option -> new EnumControllerBuilderImpl<>(option).enumClass(ThrowingDisplayMode.class))
                                        .build())
                                .build())
                        .build())
                .save(AxeThrowYACLCompat.HANDLER::save)
                .build()
                .generateScreen(parent);
    }

    public static final ConfigClassHandler<AxeThrowYACLCompat> HANDLER = ConfigClassHandler.createBuilder(AxeThrowYACLCompat.class)
            .id(AxeThrow.id("axe_throw"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("axe_throw.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public ThrowingDisplayMode throwingDisplayMode = ThrowingDisplayMode.WHEN_SHIFT_PRESSED;
}
