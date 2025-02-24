package survivalblock.axe_throw.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import survivalblock.axe_throw.common.init.AxeThrowDamageTypes;
import survivalblock.axe_throw.common.init.AxeThrowEntityTypes;
import survivalblock.axe_throw.common.init.AxeThrowTags;

import java.util.concurrent.CompletableFuture;

public class AxeThrowTagGenerator {

    public static class AxeThrowDamageTypeTagGenerator extends FabricTagProvider<DamageType> {

        public AxeThrowDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            this.getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE).add(AxeThrowDamageTypes.THROWN_AXE);
            this.getOrCreateTagBuilder(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS).add(AxeThrowDamageTypes.THROWN_AXE);
            this.getOrCreateTagBuilder(DamageTypeTags.PANIC_CAUSES).add(AxeThrowDamageTypes.THROWN_AXE);
        }
    }


    public static class AxeThrowEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

        public AxeThrowEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(AxeThrowEntityTypes.THROWN_AXE);
        }
    }

    public static class AxeThrowItemTagGenerator extends FabricTagProvider.ItemTagProvider {

        public AxeThrowItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            FabricTagBuilder axeThrowable = getOrCreateTagBuilder(AxeThrowTags.THROWABLE);
            axeThrowable.add(Items.DIAMOND_AXE, Items.NETHERITE_AXE);
            axeThrowable.addOptional(Registries.ITEM.getId(Items.GOLDEN_AXE));
            axeThrowable.forceAddTag(AxeThrowTags.KNIVES);
            axeThrowable.forceAddTag(AxeThrowTags.ALWAYS_THROWABLE);

            FabricTagBuilder knives = getOrCreateTagBuilder(AxeThrowTags.KNIVES);
            knives.add(Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
            knives.addOptional(Registries.ITEM.getId(Items.GOLDEN_SWORD));

            //noinspection unused
            FabricTagBuilder alwaysThrowable = getOrCreateTagBuilder(AxeThrowTags.ALWAYS_THROWABLE);
        }
    }
}
