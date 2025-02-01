package survivalblock.axe_throw.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import survivalblock.axe_throw.common.AxeThrow;

import java.util.HashMap;
import java.util.Map;

public class AxeThrowDamageTypes {

    public static final RegistryKey<DamageType> THROWN_AXE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AxeThrow.id("thrown_axe_hit"));

    public static ImmutableMap<RegistryKey<DamageType>, DamageType> asDamageTypes() {
        Map<RegistryKey<DamageType>, DamageType> damageTypes = new HashMap<>();
        damageTypes.put(THROWN_AXE, new DamageType("axe_throw.thrown_axe_hit", 0.1F));
        return ImmutableMap.copyOf(damageTypes);
    }

    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : asDamageTypes().entrySet()) {
            damageTypeRegisterable.register(entry.getKey(), entry.getValue());
        }
    }
}
