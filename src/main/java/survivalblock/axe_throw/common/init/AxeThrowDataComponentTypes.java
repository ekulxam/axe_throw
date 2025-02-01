package survivalblock.axe_throw.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.axe_throw.common.AxeThrow;

public class AxeThrowDataComponentTypes {

    public static final ComponentType<Boolean> CAN_THROW = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();

    public static void init() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, AxeThrow.id("can_throw"), CAN_THROW);
    }
}
