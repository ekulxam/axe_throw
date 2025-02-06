package survivalblock.axe_throw.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import survivalblock.axe_throw.common.AxeThrow;

public class AxeThrowSoundEvents {

    public static final SoundEvent ITEM_THROWN_AXE_HIT = SoundEvent.of(AxeThrow.id("item.thrown_axe.hit"));
    public static final SoundEvent ITEM_THROWN_AXE_HIT_GROUND = SoundEvent.of(AxeThrow.id("item.thrown_axe.hit_ground"));
    public static final SoundEvent ITEM_THROWN_AXE_RETURN = SoundEvent.of(AxeThrow.id("item.thrown_axe.return"));
    public static final RegistryEntry<SoundEvent> ITEM_THROWN_AXE_THROW = registerReference(AxeThrow.id("item.thrown_axe.throw"));

    private static RegistryEntry<SoundEvent> registerReference(Identifier id) {
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, ITEM_THROWN_AXE_HIT.id(), ITEM_THROWN_AXE_HIT);
        Registry.register(Registries.SOUND_EVENT, ITEM_THROWN_AXE_HIT_GROUND.id(), ITEM_THROWN_AXE_HIT_GROUND);
        Registry.register(Registries.SOUND_EVENT, ITEM_THROWN_AXE_RETURN.id(), ITEM_THROWN_AXE_RETURN);
    }
}
