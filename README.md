# Throwing your Weapons
Axe Throw defines a tag `axe_throw:throwable` that allows items to be thrown like tridents (by default, this contains the gold, diamond, and netherite axes, and items in the `axe_throw:knives` and `axe_throw:always_throwable` tags). Thrown weapons will spin while moving through the air and damage entities upon contact. This mod also allows items in the tag to be enchanted with Loyalty, which will work like trident Loyalty. Thrown weapons will attempt to return to the slot they were thrown from when picked up.

To retain vanilla behavior, items in the `axe_throw:throwable` tag cannot be thrown immediately. This means they will default to the regular behavior (and therefore delegate item use logic to the offhand item, such as a shield). Press the Shift key while hovering over a valid ItemStack in the inventory to display the throwing mode.</br>
<img src="https://cdn.modrinth.com/data/cached_images/08031510bbf50e7f24c11b489302360a36366fb2.png" alt="Not throwable" width="300">
<img src="https://cdn.modrinth.com/data/cached_images/ae8a354a4a8c2a0f8033afd75196ab27575dff6f.png" alt="Throwable" width="300"></br>
To change the throwing mode, right-click on the ItemStack in the inventory. This will allow the ItemStack to be thrown like a trident.

Items in the `axe_throw:knives` tag will not spin when thrown. By default, this includes the gold, diamond, and netherite swords.
Items in the `axe_throw:always_throwable` tag will always be throwable and cannot be toggled.

### Configuration:</br>
Axe Throw adds two new gamerules:
1. `axe_throw:projectile_drag_in_water` - Determines the drag in water for thrown weapons. By default, the value is set to 0.85.
2. `axe_throw:projectile_damage_multiplier` - All damage dealt by thrown weapons will be multiplied by this amount. By default, the value is set to 0.7.

Axe Throw's config is powered by YetAnotherConfigLib (requires manual installation, not included). The config is accessible through ModMenu and allows the user to control how the throwing mode is displayed (SHIFT, ALWAYS, NEVER).