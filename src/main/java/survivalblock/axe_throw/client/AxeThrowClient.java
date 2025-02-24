package survivalblock.axe_throw.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import survivalblock.axe_throw.client.render.entity.ThrownAxeEntityRenderer;
import survivalblock.axe_throw.common.init.AxeThrowDataComponentTypes;
import survivalblock.axe_throw.common.init.AxeThrowEntityTypes;
import survivalblock.axe_throw.common.init.AxeThrowTags;

import java.util.List;

public class AxeThrowClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(AxeThrowEntityTypes.THROWN_AXE, ThrownAxeEntityRenderer::new);
        ItemTooltipCallback.EVENT.register(AxeThrowClient::appendThrowingComponentTooltip);
    }

    public static void appendThrowingComponentTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> lines) {
        if (!stack.isIn(AxeThrowTags.THROWABLE)) {
            return;
        }
        MutableText text;
        if (stack.getOrDefault(AxeThrowDataComponentTypes.CAN_THROW, false) || stack.isIn(AxeThrowTags.ALWAYS_THROWABLE)) {
            text = Text.translatable("item.axe_throw.throwable").formatted(Formatting.GREEN);
        } else {
            text = Text.translatable("item.axe_throw.not_throwable").formatted(Formatting.RED);
        }
        lines.add(Math.min(lines.size(), 1), text); // under item name
    }
}
