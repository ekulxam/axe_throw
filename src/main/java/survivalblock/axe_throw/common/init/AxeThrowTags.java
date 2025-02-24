package survivalblock.axe_throw.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.axe_throw.common.AxeThrow;

public class AxeThrowTags {

    public static final TagKey<Item> THROWABLE = TagKey.of(RegistryKeys.ITEM, AxeThrow.id("throwable"));
    public static final TagKey<Item> KNIVES = TagKey.of(RegistryKeys.ITEM, AxeThrow.id("knives")); // Okay, I know that in real life, it is almost impossible to throw a knife/sword with no spin (but you can throw it "straight" so it hits its target at the intended angle), but for implementation reasons, they just face forward.
    public static final TagKey<Item> ALWAYS_THROWABLE = TagKey.of(RegistryKeys.ITEM, AxeThrow.id("always_throwable"));
}
