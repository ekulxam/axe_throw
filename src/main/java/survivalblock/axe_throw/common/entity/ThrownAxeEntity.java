package survivalblock.axe_throw.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import survivalblock.axe_throw.common.AxeThrow;
import survivalblock.axe_throw.common.init.AxeThrowGameRules;
import survivalblock.axe_throw.common.init.AxeThrowSoundEvents;

import java.util.function.Supplier;

import static survivalblock.axe_throw.common.init.AxeThrowAttachments.THROWN_AXE_ITEM_STACK;
import static survivalblock.axe_throw.common.init.AxeThrowAttachments.THROWN_AXE_TICKS_ACTIVE;

@SuppressWarnings("UnstableApiUsage")
public class ThrownAxeEntity extends TridentEntity {

    public static final Supplier<ItemStack> DEFAULT_ITEM_STACK_SUPPLIER = () -> new ItemStack(Items.DIAMOND_AXE);
    public static final String SLOT_KEY = "shotFromInventorySlot";

    private int slot = 0;

    public ThrownAxeEntity(EntityType<? extends TridentEntity> entityType, World world) {
        super(entityType, world);
    }

    private ThrownAxeEntity(World world, LivingEntity owner, ItemStack stack, int slot) {
        super(world, owner, stack);
        this.setAttached(THROWN_AXE_TICKS_ACTIVE, 0L);
        this.setAttached(THROWN_AXE_ITEM_STACK, stack);
        this.slot = slot;
    }

    public static @NotNull ThrownAxeEntity fromOwnerAndItemStack(World world, LivingEntity owner, ItemStack stack, int slot) {
        AxeThrow.throwingAxeAndNotTrident = true;
        ThrownAxeEntity thrownAxe = new ThrownAxeEntity(world, owner, stack, slot);
        AxeThrow.throwingAxeAndNotTrident = false;
        return thrownAxe;
    }

    @Override
    public void tick() {
        if (!this.isOnGround() && this.inGroundTime <= 0 && !this.horizontalCollision && !this.verticalCollision) {
            this.setAttached(THROWN_AXE_TICKS_ACTIVE, this.getTicksActive() + 1);
        }
        super.tick();
    }

    public long getTicksActive() {
        return this.getAttachedOrCreate(THROWN_AXE_TICKS_ACTIVE);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return DEFAULT_ITEM_STACK_SUPPLIER.get();
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        if (PickupPermission.CREATIVE_ONLY.equals(this.pickupType)) {
            return true;
        }
        if (isOwner(player)) {
            PlayerInventory inventory = player.getInventory();
            try {
                ItemStack stack = inventory.getStack(this.slot);
                if (stack == null || stack.isEmpty()) {
                    inventory.setStack(this.slot, this.asItemStack());
                    return true;
                }
                return inventory.insertStack(this.asItemStack());
            } catch (ArrayIndexOutOfBoundsException e) {
                AxeThrow.LOGGER.error("An error occurred when picking up a thrown axe!", e);
                return inventory.insertStack(this.asItemStack());
            }
        }
        return super.tryPickup(player);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(SLOT_KEY, this.slot);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(SLOT_KEY)) {
            this.slot = nbt.getInt(SLOT_KEY);
        }
    }

    @Override
    protected float getDragInWater() {
        return (float) this.getWorld().getGameRules().get(AxeThrowGameRules.PROJECTILE_DRAG_IN_WATER).get();
    }

    @Override
    protected SoundEvent getHitSound() {
        return AxeThrowSoundEvents.ITEM_THROWN_AXE_HIT_GROUND;
    }
}
