package com.jackdaw.npcbasicfunction.function;

import com.jackdaw.chatwithnpc.ChatWithNPCMod;
import com.jackdaw.chatwithnpc.conversation.ConversationHandler;
import com.jackdaw.chatwithnpc.npc.NPCEntity;
import com.jackdaw.chatwithnpc.openaiapi.function.CustomFunction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class BasicActionFunction extends CustomFunction {

    public BasicActionFunction() {
        description = "This function is used to express your movement. You can `walk to player` if you want, or `escape` if you think you are in danger.";
        properties = Map.of(
                "action", Map.of(
                        "type", "string",
                        "description", "This is your movement.",
                        "enum", List.of("WALK_TO_PLAYER", "ESCAPE")
                )
        );
    }

    enum Actions {
        WALK_TO_PLAYER,
        ESCAPE
    }

    @Override
    public Map<String, String> execute(@NotNull ConversationHandler conversation, @NotNull Map<String, Object> args) {
        String action = (String) args.get("action");
        try {
            NPCEntity npc = conversation.getNpc();
            PlayerEntity player = npc.getEntity().getEntityWorld().getClosestPlayer(npc.getEntity(), 10);
            if (player == null) return Map.of("status", "failed, no player near you");
            switch (Actions.valueOf(action)) {
                case WALK_TO_PLAYER:
                    walkToPlayer((MobEntity) npc.getEntity(), player);
                    break;
                case ESCAPE:
                    escape((PathAwareEntity) npc.getEntity());
                    break;
            }
        } catch (Exception e) {
            ChatWithNPCMod.LOGGER.error("Failed to execute the action: " + action + e);
            return Map.of("status", "failed");
        }
        return Map.of("status", "success, you `" + action + "`");
    }

    private static void escape(@NotNull PathAwareEntity mobEntity) {
        Random random = mobEntity.getRandom();
        BlockPos blockPos = mobEntity.getBlockPos();
        Vec3d vec3d = null;
        for(int i = 0; i < 10; ++i) {
            BlockPos blockPos2 = blockPos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (mobEntity.getPathfindingFavor(blockPos2) < 0.0F) {
                vec3d = Vec3d.ofBottomCenter(blockPos2);
                break;
            }
        }
        if (vec3d == null) return;
        double targetX = vec3d.x;
        double targetY = vec3d.y;
        double targetZ = vec3d.z;
        mobEntity.getNavigation().startMovingTo(targetX, targetY, targetZ, mobEntity.getMovementSpeed() + 1);
    }

    private static void walkToPlayer(@NotNull MobEntity mobEntity, PlayerEntity player) {
        mobEntity.getNavigation().startMovingTo(player, mobEntity.getMovementSpeed() + 1);
    }
}
