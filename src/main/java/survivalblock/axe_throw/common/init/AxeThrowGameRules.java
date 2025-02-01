package survivalblock.axe_throw.common.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class AxeThrowGameRules {

    public static final GameRules.Key<DoubleRule> PROJECTILE_DRAG_IN_WATER =
            GameRuleRegistry.register("axe_throw:projectile_drag_in_water", GameRules.Category.MISC, GameRuleFactory.createDoubleRule(0.85F, 0.1F, 1.0F));

    public static final GameRules.Key<DoubleRule> PROJECTILE_DAMAGE_MULTIPLIER =
            GameRuleRegistry.register("axe_throw:projectile_damage_multiplier", GameRules.Category.MISC, GameRuleFactory.createDoubleRule(0.7F, 0.01F, Float.MAX_VALUE));

    public static void init() {

    }
}
