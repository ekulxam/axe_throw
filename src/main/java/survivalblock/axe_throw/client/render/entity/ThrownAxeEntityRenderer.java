package survivalblock.axe_throw.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import survivalblock.axe_throw.common.entity.ThrownAxeEntity;
import survivalblock.axe_throw.common.init.AxeThrowAttachments;
import survivalblock.axe_throw.common.init.AxeThrowTags;

public class ThrownAxeEntityRenderer extends EntityRenderer<ThrownAxeEntity> {

    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/item/diamond_axe.png");

    public ThrownAxeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ThrownAxeEntity axe, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(90.0F - yaw));
        //noinspection UnstableApiUsage
        ItemStack stack = axe.getAttachedOrElse(AxeThrowAttachments.THROWN_AXE_ITEM_STACK, axe.getItemStack()).copy();
        if (stack.isIn(AxeThrowTags.KNIVES)) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(axe.getPitch() - 135));
        } else {
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(axe.getTicksActive() * 15));
        }
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, axe.getWorld(), 0);
        matrices.pop();
        super.render(axe, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ThrownAxeEntity entity) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(ThrownAxeEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
