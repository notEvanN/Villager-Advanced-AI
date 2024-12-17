package net.evan.villagerai.villager;

import com.google.common.collect.ImmutableSet;
import net.evan.villagerai.VillagerAI;
import net.evan.villagerai.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, VillagerAI.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, VillagerAI.MOD_ID);

    public static final RegistryObject<PoiType> SAPPHIRE_POI = POI_TYPES.register("sapphire_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SAPPHIRE_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> SOUND_MASTER =
            VILLAGER_PROFESSIONS.register("sapphiremaster", () -> new VillagerProfession("sapphiremaster",
                    holder -> holder.get() == SAPPHIRE_POI.get(), holder -> holder.get() == SAPPHIRE_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));



    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
