package survivalblock.axe_throw.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.entity.ThrownAxeEntity;

public class AxeThrowEntityTypes {

    public static final EntityType<ThrownAxeEntity> THROWN_AXE = registerEntity("thrown_axe", EntityType.Builder.<ThrownAxeEntity>create(ThrownAxeEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));

    @SuppressWarnings("SameParameterValue")
    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, AxeThrow.id(name), builder.build());
    }

    public static void init() {
    }
}
