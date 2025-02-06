package survivalblock.axe_throw.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import survivalblock.axe_throw.common.init.AxeThrowGameRules;
import survivalblock.axe_throw.common.init.AxeThrowSoundEvents;
import survivalblock.axe_throw.common.init.AxeThrowTags;

import java.util.concurrent.CompletableFuture;

public class AxeThrowEnUsLangGenerator extends FabricLanguageProvider {

    public AxeThrowEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        // components
        translationBuilder.add("item.axe_throw.throwable", "✅ Throwable");
        translationBuilder.add("item.axe_throw.not_throwable", "❌ Not throwable");

        // item tags
        translationBuilder.add(AxeThrowTags.THROWABLE, "Throwable Items");

        // damage types
        translationBuilder.add("death.attack.axe_throw.thrown_axe_hit", "%1$s was impaled by %2$s with a thrown weapon");
        translationBuilder.add("death.attack.axe_throw.thrown_axe_hit.item", "%1$s was impaled by %2$s with the thrown weapon %3$s");

        // subtitles
        addSoundEvent(translationBuilder, AxeThrowSoundEvents.ITEM_THROWN_AXE_HIT, "Thrown weapon stabs");
        addSoundEvent(translationBuilder, AxeThrowSoundEvents.ITEM_THROWN_AXE_HIT_GROUND, "Thrown weapon vibrates");
        addSoundEvent(translationBuilder, AxeThrowSoundEvents.ITEM_THROWN_AXE_RETURN, "Thrown weapon returns");
        addSoundEvent(translationBuilder, AxeThrowSoundEvents.ITEM_THROWN_AXE_THROW.value(), "Thrown weapon clangs");

        // config
        translationBuilder.add("axe_throw.yacl.category.main", "Axe Throw Config (Powered by YACL)");
        translationBuilder.add("axe_throw.yacl.category.main.tooltip", "Config");
        translationBuilder.add("axe_throw.yacl.group.client", "Client");
        translationBuilder.add("axe_throw.yacl.option.enum.throwingDisplayMode", "Ability to Throw - Display Mode");
        translationBuilder.add("axe_throw.yacl.option.enum.throwingDisplayMode.desc", "Controls when Amarong should log the world time when a beacon's beam segments are set for rendering.");
        translationBuilder.add("axe_throw.yacl.option.enum.throwingDisplayMode.never", "NEVER");
        translationBuilder.add("axe_throw.yacl.option.enum.throwingDisplayMode.always", "ALWAYS");
        translationBuilder.add("axe_throw.yacl.option.enum.throwingDisplayMode.shift", "ONLY WHEN HOLDING SHIFT");

        translationBuilder.add(AxeThrowGameRules.PROJECTILE_DAMAGE_MULTIPLIER.getTranslationKey(), "Axe Throw - Projectile Damage Multiplier");
        translationBuilder.add(AxeThrowGameRules.PROJECTILE_DRAG_IN_WATER.getTranslationKey(), "Axe Throw - Projectile Drag in Water");
    }

    public static void addSoundEvent(TranslationBuilder translationBuilder, SoundEvent soundEvent, String value) {
        translationBuilder.add(soundEvent.id().toTranslationKey("subtitles"), value);
    }
}
