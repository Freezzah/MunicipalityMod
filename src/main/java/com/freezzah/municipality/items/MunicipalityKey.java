package com.freezzah.municipality.items;

import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.municipality.MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY;

public class MunicipalityKey extends Item {

    public MunicipalityKey() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack itemStack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        if (livingEntity instanceof Player) {
            IMunicipalityManagerCapability cap =
                    player.level().getCapability(MUNICIPALITY_MANAGER_CAPABILITY).orElseThrow(
                            () -> new IllegalArgumentException("LazyOptional must not be empty!")
                    );
            Municipality municipality = cap.getMunicipalityByInhabitant(Inhabitant.fromPlayer(player));
            if (municipality == null) {
                return InteractionResult.PASS;
            }
            if (municipality.isOwner(Inhabitant.fromPlayer(player))) {
                Player targetPlayer = (Player) livingEntity;
                municipality.setOwner(Inhabitant.fromPlayer(targetPlayer));
                for (Player member : municipality.getInhabitantsAsPlayers(player.level())) {
                    member.sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_OWNER(
                            municipality.getMunicipalityName(),
                            player.getName(), targetPlayer.getName())));
                }
            }
        }
        return InteractionResult.PASS;
    }
}
