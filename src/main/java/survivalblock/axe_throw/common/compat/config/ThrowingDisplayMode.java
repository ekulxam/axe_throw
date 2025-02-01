package survivalblock.axe_throw.common.compat.config;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ThrowingDisplayMode implements StringIdentifiable, TranslatableOption {

    ALWAYS("always"),
    NEVER("never"),
    WHEN_SHIFT_PRESSED("shift");

    private final String name;
    private final String translationKey;

    @SuppressWarnings("deprecation")
    public static final EnumCodec<ThrowingDisplayMode> CODEC = StringIdentifiable.createCodec(ThrowingDisplayMode::values);
    private static final IntFunction<ThrowingDisplayMode> BY_ID = ValueLists.createIdToValueFunction(ThrowingDisplayMode::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);

    ThrowingDisplayMode(final String name) {
        this.name = name;
        this.translationKey = "axe_throw.yacl.option.enum.throwingDisplayMode." + name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    @SuppressWarnings("unused")
    public static ThrowingDisplayMode getType(int type) {
        return BY_ID.apply(type);
    }

    @SuppressWarnings("unused")
    public static ThrowingDisplayMode getType(String name) {
        return CODEC.byId(name, NEVER);
    }

    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }
}
