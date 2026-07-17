package survivalblock.axe_throw.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
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
        if (stack.getItem() instanceof TridentItem) {
            return false;
        }
		return stack.isIn(AxeThrowTags.ALWAYS_THROWABLE) ||
				(stack.isIn(AxeThrowTags.THROWABLE)
						&& stack.getOrDefault(AxeThrowDataComponentTypes.CAN_THROW, false));
	}

    @SuppressWarnings("deprecation")
    public static double getAttributeValue(@Nullable Entity owner, ItemStack stack, RegistryEntry<EntityAttribute> attribute) {
        double base;
        if (owner instanceof LivingEntity living) {
            base = living.getAttributeBaseValue(attribute);
        } else {
            base = DefaultAttributeRegistry.get(EntityType.PLAYER).getBaseValue(attribute);
        }
        AxeThrow.DoubleHolder change = new AxeThrow.DoubleHolder(base);
        stack.applyAttributeModifiers(EquipmentSlot.MAINHAND, (registryEntry, modifier) -> {
            if (registryEntry.matches(attribute) || registryEntry == attribute) {
                change.add(
                        switch (modifier.operation()) {
                            case ADD_VALUE -> modifier.value();
                            case ADD_MULTIPLIED_BASE -> modifier.value() * base;
                            case ADD_MULTIPLIED_TOTAL -> modifier.value() * change.get();
                        }
                );
            }
        });
        return change.get();
    }

    public static final class DoubleHolder {
        private double value;

        @SuppressWarnings("unused")
        public DoubleHolder() {
            this(0);
        }

        public DoubleHolder(double value) {
            this.set(value);
        }

        public void add(double other) {
            this.set(this.value + other);
        }

        public double get() {
            return this.value;
        }

        public void set(double value) {
            this.value = value;
        }
    }
}