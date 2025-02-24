package survivalblock.axe_throw.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.init.AxeThrowDataComponentTypes;
import survivalblock.axe_throw.common.init.AxeThrowTags;

@Mixin(Item.class)
public class ItemMixin {

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
}