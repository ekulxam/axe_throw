package survivalblock.axe_throw.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.init.AxeThrowDataComponentTypes;
import survivalblock.axe_throw.common.init.AxeThrowGameRules;
import survivalblock.axe_throw.common.init.AxeThrowSoundEvents;
import survivalblock.axe_throw.common.init.AxeThrowTags;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    public abstract int getMaxUseTime(ItemStack stack, LivingEntity user);

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void prepareToThrow(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if (!AxeThrow.canBeThrown(user.getStackInHand(hand))) {
			return;
		}
		cir.setReturnValue(Items.TRIDENT.use(world, user, hand));
	}

	@Inject(method = "onStoppedUsing", at = @At("HEAD"), cancellable = true)
	private void throwItem(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
		if (!AxeThrow.canBeThrown(stack)) {
			return;
		}
		Items.TRIDENT.onStoppedUsing(stack, world, user, remainingUseTicks);
		ci.cancel();
	}

	@Inject(method = "getMaxUseTime", at = @At("HEAD"), cancellable = true)
	private void allowUsingThrowableItem(ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
		if (!AxeThrow.canBeThrown(stack)) {
			return;
		}
		cir.setReturnValue(Items.TRIDENT.getMaxUseTime(stack, user));
	}

	@Inject(method = "getUseAction", at = @At("HEAD"), cancellable = true)
	private void changeThrowableUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
		if (!AxeThrow.canBeThrown(stack)) {
			return;
		}
		cir.setReturnValue(Items.TRIDENT.getUseAction(stack));
	}

	@Inject(method = "onClicked", at = @At("HEAD"), cancellable = true)
	private void setIsThrowable(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
		World world = player.getWorld();
		if (!otherStack.isEmpty()) {
			return;
		}
		if (!ClickType.RIGHT.equals(clickType)) {
			return;
		}
		if (!stack.isIn(AxeThrowTags.THROWABLE)) {
			return;
		}
		if (stack.isIn(AxeThrowTags.ALWAYS_THROWABLE)) {
			return;
		}
		boolean canThrow = stack.getOrDefault(AxeThrowDataComponentTypes.CAN_THROW, false);
		if (world.isClient()) {
			player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.4F, canThrow ? 0.75F : 1.0F);
		}
		if (canThrow) {
			stack.remove(AxeThrowDataComponentTypes.CAN_THROW);
		} else {
			stack.set(AxeThrowDataComponentTypes.CAN_THROW, true);
		}
		cir.setReturnValue(true);
	}

    @Inject(method = "usageTick", at = @At("HEAD"))
    private void playSoundIfReady(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci) {
        if (!(user instanceof ServerPlayerEntity player)) {
            return;
        }
        if (!AxeThrow.canBeThrown(stack)) {
            return;
        }
        if (!world.getGameRules().getBoolean(AxeThrowGameRules.NOTIFY_WHEN_READY)) {
            return;
        }
        int threshold = (int) (10 * 1.1 / AxeThrow.getAttributeValue(user, stack, EntityAttributes.GENERIC_ATTACK_SPEED));
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (i == threshold) {
            Vec3d pos = player.getPos();
            player.networkHandler.sendPacket(
                    new PlaySoundS2CPacket(
                            AxeThrowSoundEvents.ITEM_THROWN_AXE_CHARGED,
                            SoundCategory.PLAYERS,
                            pos.x,
                            pos.y,
                            pos.z,
                            1,
                            1,
                            user.getRandom().nextLong()
                    )
            );
        }
    }
}