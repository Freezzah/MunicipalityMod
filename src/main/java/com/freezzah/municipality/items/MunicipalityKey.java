package com.freezzah.municipality.items;

import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.municipality.IMunicipality;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.freezzah.municipality.MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY;

public class MunicipalityKey extends Item {

    public MunicipalityKey(Properties properties) {
        super(properties);
    }

    public MunicipalityKey() {
        super(new Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity instanceof Player) {
            IMunicipalityManagerCapability cap =
                    player.level().getCapability(MUNICIPALITY_MANAGER_CAPABILITY).orElseThrow(
                            () -> new IllegalArgumentException("LazyOptional must not be empty!")
                    );
            IMunicipality municipality = cap.getPlayerInAnyMunicipality(player);
            if (municipality.isOwner(player)) {
                Player targetPlayer = (Player) livingEntity;
                municipality.setOwner(targetPlayer);
                for (Player member : municipality.getPlayers()) {
                    member.sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_OWNER(
                            municipality.getMunicipalityName(),
                            player.getName(), targetPlayer.getName())));
                }
            }
        }
        return InteractionResult.PASS;
    }
}
