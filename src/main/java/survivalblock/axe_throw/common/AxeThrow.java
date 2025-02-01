package survivalblock.axe_throw.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.axe_throw.common.init.AxeThrowAttachments;
import survivalblock.axe_throw.common.init.AxeThrowDataComponentTypes;
import survivalblock.axe_throw.common.init.AxeThrowEntityTypes;
import survivalblock.axe_throw.common.init.AxeThrowGameRules;
import survivalblock.axe_throw.common.init.AxeThrowSoundEvents;
import survivalblock.axe_throw.common.init.AxeThrowTags;

public class AxeThrow implements ModInitializer {

	public static final String MOD_ID = "axe_throw";

	public static boolean throwingAxeAndNotTrident = false;

	public static final Logger LOGGER = LoggerFactory.getLogger("Axe Throw");

	@Override
	public void onInitialize() {
		AxeThrowDataComponentTypes.init();
		AxeThrowAttachments.init();
		AxeThrowGameRules.init();
		AxeThrowSoundEvents.init();
		AxeThrowEntityTypes.init();
		EnchantmentEvents.ALLOW_ENCHANTING.register((enchantment, target, enchantingContext) -> {
			if (!EnchantingContext.ACCEPTABLE.equals(enchantingContext)) {
				return TriState.DEFAULT;
			}
			if (!target.isIn(AxeThrowTags.THROWABLE)) {
				return TriState.DEFAULT;
			}
			return enchantment.matchesKey(Enchantments.LOYALTY) ? TriState.TRUE : TriState.DEFAULT;
		});
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean canBeThrown(ItemStack stack) {
		return stack.isIn(AxeThrowTags.THROWABLE) && stack.getOrDefault(AxeThrowDataComponentTypes.CAN_THROW, false);
	}
}