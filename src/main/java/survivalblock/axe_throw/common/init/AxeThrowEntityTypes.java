package survivalblock.axe_throw.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import survivalblock.axe_throw.common.entity.ThrownAxeEntity;

public class AxeThrowEntityTypes {

    public static final EntityType<ThrownAxeEntity> THROWN_AXE = registerEntity(keyOf(), EntityType.Builder.<ThrownAxeEntity>create(ThrownAxeEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));

    private static <T extends Entity> EntityType<T> registerEntity(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static RegistryKey<EntityType<?>> keyOf() {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.ofVanilla("thrown_axe"));
    }

    public static void init() {
    }
}
