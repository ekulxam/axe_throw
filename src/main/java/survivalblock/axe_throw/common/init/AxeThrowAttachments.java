package survivalblock.axe_throw.common.init;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.entity.ThrownAxeEntity;

@SuppressWarnings("UnstableApiUsage")
public class AxeThrowAttachments {

    public static final AttachmentType<Long> THROWN_AXE_TICKS_ACTIVE = AttachmentRegistry.create(
            AxeThrow.id("thrown_axe_ticks_active"),
            builder -> builder
                    .initializer(() -> 0L)
                    .persistent(Codec.LONG)
                    .syncWith(PacketCodecs.VAR_LONG, AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<ItemStack> THROWN_AXE_ITEM_STACK = AttachmentRegistry.create(
            AxeThrow.id("thrown_axe_item_stack"),
            builder -> builder
                    .initializer(ThrownAxeEntity.DEFAULT_ITEM_STACK_SUPPLIER)
                    .persistent(ItemStack.OPTIONAL_CODEC)
                    .syncWith(ItemStack.OPTIONAL_PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    public static void init() {

    }
}
