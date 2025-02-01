package survivalblock.axe_throw.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.entity.ThrownAxeEntity;
import survivalblock.axe_throw.common.init.AxeThrowDamageTypes;
import survivalblock.axe_throw.common.init.AxeThrowEntityTypes;
import survivalblock.axe_throw.common.init.AxeThrowGameRules;
import survivalblock.axe_throw.common.init.AxeThrowSoundEvents;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyExpressionValue(method =
            {"<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
            "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V"},
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityType;TRIDENT:Lnet/minecraft/entity/EntityType;"))
    private static EntityType<?> replaceWithAxeThrowEntityType(EntityType<?> original) {
        return AxeThrow.throwingAxeAndNotTrident ? AxeThrowEntityTypes.THROWN_AXE : original;
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/sound/SoundEvents;ITEM_TRIDENT_RETURN:Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent useAxeReturnSound(SoundEvent original) {
        if ((TridentEntity) (Object) this instanceof ThrownAxeEntity) {
            return AxeThrowSoundEvents.ITEM_THROWN_AXE_RETURN;
        }
        return original;
    }

    @WrapOperation(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSources;trident(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/entity/damage/DamageSource;"))
    private DamageSource useThrownAxeDamageSource(DamageSources instance, Entity source, Entity attacker, Operation<DamageSource> original) {
        if ((TridentEntity) (Object) this instanceof ThrownAxeEntity) {
            return new DamageSource(this.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(AxeThrowDamageTypes.THROWN_AXE), source, attacker);
        }
        return original.call(instance, source, attacker);
    }

    @ModifyVariable(method = "onEntityHit", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/projectile/TridentEntity;dealtDamage:Z", opcode = Opcodes.PUTFIELD), index = 3)
    private float changeAxeDamage(float value) {
        if ((TridentEntity) (Object) this instanceof ThrownAxeEntity) {
            return (float) (value * this.getWorld().getGameRules().get(AxeThrowGameRules.PROJECTILE_DAMAGE_MULTIPLIER).get());
        }
        return value;
    }

    @ModifyExpressionValue(method = "onEntityHit", at = @At(value = "FIELD", target = "Lnet/minecraft/sound/SoundEvents;ITEM_TRIDENT_HIT:Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent useAxeHitSound(SoundEvent original) {
        if ((TridentEntity) (Object) this instanceof ThrownAxeEntity) {
            return AxeThrowSoundEvents.ITEM_THROWN_AXE_HIT;
        }
        return original;
    }
}
