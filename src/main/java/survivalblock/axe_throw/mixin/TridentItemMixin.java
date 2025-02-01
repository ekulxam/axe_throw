package survivalblock.axe_throw.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.entity.ThrownAxeEntity;
import survivalblock.axe_throw.common.init.AxeThrowSoundEvents;

import java.util.List;
import java.util.Optional;

@Mixin(TridentItem.class)
public class TridentItemMixin {

    @WrapOperation(method = "onStoppedUsing", at = @At(value = "NEW", target = "(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/projectile/TridentEntity;"))
    private TridentEntity invokeThrownAxeConstructor(World world, LivingEntity living, ItemStack stack, Operation<TridentEntity> original, @Local PlayerEntity player) {
        if (!AxeThrow.canBeThrown(stack)) {
            return original.call(world, living, stack);
        }
        PlayerInventory inventory = player.getInventory();
        int slot = 0;
        final int size = inventory.size();
        for (int i = 0; i < size; i++) {
            ItemStack tempStack = inventory.getStack(i);
            if (stack.equals(tempStack)) {
                slot = i;
                break;
            }
        }
        return ThrownAxeEntity.fromOwnerAndItemStack(world, living, stack, slot);
    }

    @WrapOperation(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEffect(Lnet/minecraft/item/ItemStack;Lnet/minecraft/component/ComponentType;)Ljava/util/Optional;"))
    private Optional<RegistryEntry<SoundEvent>> useAxeThrowSound(ItemStack stack, ComponentType<List<RegistryEntry<SoundEvent>>> componentType, Operation<Optional<RegistryEntry<SoundEvent>>> original) {
        if (!AxeThrow.canBeThrown(stack)) {
            return original.call(stack, componentType);
        }
        return Optional.ofNullable(AxeThrowSoundEvents.ITEM_THROWN_AXE_THROW);
    }
}
