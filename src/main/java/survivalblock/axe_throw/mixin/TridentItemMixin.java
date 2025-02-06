package survivalblock.axe_throw.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.entity.ThrownAxeEntity;
import survivalblock.axe_throw.common.init.AxeThrowSoundEvents;

import java.util.List;
import java.util.Optional;

@Mixin(TridentItem.class)
@Debug(export = true)
public class TridentItemMixin<T extends ProjectileEntity> {

    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;spawnWithVelocity(Lnet/minecraft/entity/projectile/ProjectileEntity$ProjectileCreator;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;FFF)Lnet/minecraft/entity/projectile/ProjectileEntity;"))
    public T invokeThrownAxeConstructor(ProjectileEntity.ProjectileCreator<T> creator, ServerWorld serverWorld, ItemStack stack, LivingEntity living, float roll, float power, float divergence, @Local PlayerEntity player) {
        if (!AxeThrow.canBeThrown(stack)) {
            return (T) ProjectileEntity.spawnWithVelocity(TridentEntity::new, serverWorld, stack, living, 0.0F, 2.5F, 1.0F);
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
        return (T) ThrownAxeEntity.fromOwnerAndItemStack(serverWorld, living, stack, slot);
    }

    @WrapOperation(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEffect(Lnet/minecraft/item/ItemStack;Lnet/minecraft/component/ComponentType;)Ljava/util/Optional;"))
    private Optional<RegistryEntry<SoundEvent>> useAxeThrowSound(ItemStack stack, ComponentType<List<RegistryEntry<SoundEvent>>> componentType, Operation<Optional<RegistryEntry<SoundEvent>>> original) {
        if (!AxeThrow.canBeThrown(stack)) {
            return original.call(stack, componentType);
        }
        return Optional.ofNullable(AxeThrowSoundEvents.ITEM_THROWN_AXE_THROW);
    }
}
