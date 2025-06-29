package net.evan.villagerai.villager;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.evan.villagerai.VillagerAI;
import net.evan.villagerai.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = VillagerAI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModVillagers {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModVillagers.class);
    
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, VillagerAI.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, VillagerAI.MOD_ID);

    public static final RegistryObject<PoiType> SAPPHIRE_POI = POI_TYPES.register("sapphire_poi",
            () -> {
                Set<BlockState> states = ImmutableSet.copyOf(net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK.getStateDefinition().getPossibleStates());
                LOGGER.info("Registering SAPPHIRE_POI with {} block states (using diamond blocks)", states.size());
                for (BlockState state : states) {
                    LOGGER.info("POI state: {}", state);
                }
                return new PoiType(states, 1, 1);
            });

    public static final RegistryObject<VillagerProfession> SAPPHIRE_MASTER =
            VILLAGER_PROFESSIONS.register("armorer", () -> {
                LOGGER.info("Registering SAPPHIRE_MASTER profession (replacing armorer)");
                return new VillagerProfession("armorer",
                        holder -> {
                            LOGGER.info("POI check called for: {}", holder.get());
                            LOGGER.info("POI check - holder key: {}", holder.unwrapKey().orElse(null));
                            LOGGER.info("POI check - ARMORER POI key: {}", net.minecraft.world.entity.ai.village.poi.PoiTypes.ARMORER);
                            boolean matches = holder.is(net.minecraft.world.entity.ai.village.poi.PoiTypes.ARMORER);
                            LOGGER.info("POI check result: {} for {}", matches, holder.get());
                            return matches;
                        },
                        holder -> {
                            LOGGER.info("POI check (acquirable) called for: {}", holder.get());
                            LOGGER.info("POI check (acquirable) - holder key: {}", holder.unwrapKey().orElse(null));
                            LOGGER.info("POI check (acquirable) - ARMORER POI key: {}", net.minecraft.world.entity.ai.village.poi.PoiTypes.ARMORER);
                            boolean matches = holder.is(net.minecraft.world.entity.ai.village.poi.PoiTypes.ARMORER);
                            LOGGER.info("POI check (acquirable) result: {} for {}", matches, holder.get());
                            return matches;
                        },
                        com.google.common.collect.ImmutableSet.of(),
                        com.google.common.collect.ImmutableSet.of(),
                        net.minecraft.sounds.SoundEvents.VILLAGER_WORK_LIBRARIAN);
            });

    public static void register(IEventBus eventBus) {
        LOGGER.info("Registering ModVillagers");
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        LOGGER.info("VillagerTradesEvent triggered for profession: {}", event.getType());
        
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            LOGGER.info("Adding sapphire trades to LIBRARIAN");
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            
            // Add sapphire trades to librarian
            ModVillagerTrades.addSapphireTrades(trades);
        }
    }

    @SubscribeEvent
    public static void onVillagerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            LOGGER.info("Villager spawned with profession: {}", villager.getVillagerData().getProfession());
            LOGGER.info("Villager level: {}", villager.getVillagerData().getLevel());
            LOGGER.info("Villager position: {}", villager.blockPosition());
        }
    }
}
