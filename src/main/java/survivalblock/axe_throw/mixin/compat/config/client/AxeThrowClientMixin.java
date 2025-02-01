package survivalblock.axe_throw.mixin.compat.config.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.axe_throw.client.AxeThrowClient;
import survivalblock.axe_throw.common.compat.config.AxeThrowYACLCompat;
import survivalblock.axe_throw.common.compat.config.ThrowingDisplayMode;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(value = AxeThrowClient.class, remap = false)
public class AxeThrowClientMixin {

    @SuppressWarnings("DiscouragedShift")
    @Inject(method = "appendThrowingComponentTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getOrDefault(Lnet/minecraft/component/ComponentType;Ljava/lang/Object;)Ljava/lang/Object;", remap = true, shift = At.Shift.BEFORE), cancellable = true)
    private static void configControlsComponentVisibilityInTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> lines, CallbackInfo ci) {
        ThrowingDisplayMode throwingDisplayMode = AxeThrowYACLCompat.HANDLER.instance().throwingDisplayMode;
        if (ThrowingDisplayMode.NEVER.equals(throwingDisplayMode)) {
            ci.cancel();
            return;
        }
        if (ThrowingDisplayMode.WHEN_SHIFT_PRESSED.equals(throwingDisplayMode) && !Screen.hasShiftDown()) {
            ci.cancel();
            return;
        }
    }
}
