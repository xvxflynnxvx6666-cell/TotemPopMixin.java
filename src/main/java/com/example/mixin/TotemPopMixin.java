package net.fabricmc.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class TotemPopMixin {
    @Inject(method = "tryUseTotem", at = @At("RETURN"))
    private void onTotemPop(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        // If the return value is true, a totem was successfully used
        if (cir.getReturnValue()) {
            // Check if the entity popping the totem is you (the player)
            if ((Object) this instanceof PlayerEntity player) {
                // Check if we are on the client side, then stop the game
                if (player.getWorld().isClient) {
                    MinecraftClient.getInstance().stop();
                }
            }
        }
    }
}
