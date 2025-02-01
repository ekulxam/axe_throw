package survivalblock.axe_throw.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.axe_throw.common.init.AxeThrowDamageTypes;

import java.util.concurrent.CompletableFuture;

public class AxeThrowDamageTypeGenerator extends FabricDynamicRegistryProvider {

    public AxeThrowDamageTypeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        AxeThrowDamageTypes.asDamageTypes().forEach(entries::add); // wow that was so easy
    }

    @Override
    public String getName() {
        return "Damage types";
    }
}
