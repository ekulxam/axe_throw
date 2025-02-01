package survivalblock.axe_throw.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.axe_throw.common.AxeThrow;

public class AxeThrowTags {

    public static final TagKey<Item> THROWABLE = TagKey.of(RegistryKeys.ITEM, AxeThrow.id("throwable"));
}
