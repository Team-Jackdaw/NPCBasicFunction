package com.jackdaw.npcbasicfunction.function;

import com.jackdaw.chatwithnpc.ChatWithNPCMod;
import com.jackdaw.chatwithnpc.conversation.ConversationHandler;
import com.jackdaw.chatwithnpc.npc.NPCEntity;
import com.jackdaw.chatwithnpc.openaiapi.functioncalling.CustomFunction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BasicFeelingFunction extends CustomFunction {

    public BasicFeelingFunction() {
        description = "This function is used to express your feelings. You can `shake head` if you feel bad or disagree with something, or `feel happy` if you feel happy or agree with something.";
        properties = Map.of(
                "feeling", Map.of(
                        "description", "This is your feeling.",
                        "enum", List.of("SHAKE_HEAD", "FEEL_HAPPY")
                )
        );
    }

    enum Actions {
        SHAKE_HEAD,
        FEEL_HAPPY
    }

    @Override
    public Map<String, String> execute(@NotNull ConversationHandler conversation, @NotNull Map args) {
        String feeling = (String) args.get("feeling");
        try {
            NPCEntity npc = conversation.getNpc();
            switch (Actions.valueOf(feeling)) {
                case SHAKE_HEAD:
                    shakeHead((VillagerEntity) npc.getEntity());
                    break;
                case FEEL_HAPPY:
                    feelHappy((LivingEntity) npc.getEntity());
                    break;
            }
        } catch (Exception e) {
            ChatWithNPCMod.LOGGER.error("Failed to execute the action: " + feeling + e);
            return Map.of("status", "failed");
        }
        return Map.of("status", "success, you `" + feeling + "`");
    }

    private static void shakeHead(@NotNull VillagerEntity villager) {
        villager.setHeadRollingTimeLeft(60);
    }

    private static void feelHappy(@NotNull LivingEntity livingEntity) {
        ServerWorld world = (ServerWorld) livingEntity.world;
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            double x = livingEntity.getX();
            double y = livingEntity.getY() + livingEntity.getHeight() + 0.5;
            double z = livingEntity.getZ();
            world.spawnParticles(ParticleTypes.HEART, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }, 0, 500, TimeUnit.MILLISECONDS);
        ScheduledExecutorService scheduledExecutorService1 = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService1.schedule(() -> {
            scheduledExecutorService.shutdown();
            scheduledExecutorService1.shutdown();
        }, 5, TimeUnit.SECONDS);
    }
}
