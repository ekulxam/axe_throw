package survivalblock.axe_throw.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import survivalblock.axe_throw.common.datagen.AxeThrowTagGenerator.AxeThrowDamageTypeTagGenerator;
import survivalblock.axe_throw.common.datagen.AxeThrowTagGenerator.AxeThrowEntityTypeTagGenerator;
import survivalblock.axe_throw.common.datagen.AxeThrowTagGenerator.AxeThrowItemTagGenerator;
import survivalblock.axe_throw.common.init.AxeThrowDamageTypes;

public class AxeThrowDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(AxeThrowDamageTypeTagGenerator::new);
		pack.addProvider(AxeThrowEntityTypeTagGenerator::new);
		pack.addProvider(AxeThrowItemTagGenerator::new);
		pack.addProvider(AxeThrowEnUsLangGenerator::new);
		pack.addProvider(AxeThrowDamageTypeGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, AxeThrowDamageTypes::bootstrap);
	}
}
