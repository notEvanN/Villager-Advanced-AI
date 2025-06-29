package net.evan.villagerai;

import com.mojang.logging.LogUtils;
import net.evan.villagerai.block.ModBlocks;
import net.evan.villagerai.item.ModCreativeModTabs;
import net.evan.villagerai.item.ModItems;
import net.evan.villagerai.villager.ModVillagers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VillagerAI.MOD_ID)
public class VillagerAI {

    public static final String MOD_ID = "villagerai";

    private static final Logger LOGGER = LogUtils.getLogger();

    public VillagerAI(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModVillagers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("=== VILLAGERAI COMMON SETUP ===");
        LOGGER.info("ModVillagers registration status:");
        LOGGER.info("SAPPHIRE_POI: {}", ModVillagers.SAPPHIRE_POI.isPresent());
        LOGGER.info("SAPPHIRE_MASTER: {}", ModVillagers.SAPPHIRE_MASTER.isPresent());
        
        event.enqueueWork(() -> {
            LOGGER.info("Checking SAPPHIRE_POI registration in Forge POI system");
            try {
                // Get the Forge POI state mapping
                var stateToPoiMap = net.minecraftforge.registries.GameData.PoiTypeCallbacks.getStateToPoi();
                LOGGER.info("Forge POI state mapping size: {}", stateToPoiMap.size());
                
                // Get all possible states of the diamond block
                var states = com.google.common.collect.ImmutableSet.copyOf(net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK.getStateDefinition().getPossibleStates());
                LOGGER.info("Diamond block has {} possible states", states.size());
                
                // Check if our block states are mapped to our POI
                for (var state : states) {
                    var poiHolder = stateToPoiMap.get(state);
                    if (poiHolder != null) {
                        LOGGER.info("Block state {} is mapped to POI: {}", state, poiHolder.get());
                        LOGGER.info("POI holder key: {}", poiHolder.unwrapKey().orElse(null));
                        LOGGER.info("Matches our SAPPHIRE_POI: {}", poiHolder.is(ModVillagers.SAPPHIRE_POI.getKey()));
                    } else {
                        LOGGER.warn("Block state {} is NOT mapped to any POI", state);
                    }
                }
                
                // Also check if our POI is in the registry
                var poiRegistry = net.minecraft.core.registries.BuiltInRegistries.POINT_OF_INTEREST_TYPE;
                LOGGER.info("POI Registry contains SAPPHIRE_POI: {}", poiRegistry.containsKey(ModVillagers.SAPPHIRE_POI.getKey()));
                LOGGER.info("SAPPHIRE_POI in registry: {}", poiRegistry.get(ModVillagers.SAPPHIRE_POI.getKey()));
                
            } catch (Exception e) {
                LOGGER.error("Error checking SAPPHIRE_POI registration: {}", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.RAW_SAPPHIRE);
        }
        
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.SAPPHIRE_MASTER_SPAWN_EGG);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
