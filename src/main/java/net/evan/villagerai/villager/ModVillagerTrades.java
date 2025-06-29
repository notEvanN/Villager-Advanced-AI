package net.evan.villagerai.villager;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.evan.villagerai.block.ModBlocks;
import net.evan.villagerai.item.ModItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ModVillagerTrades {
    
    public static List<VillagerTrades.ItemListing> getSapphireMasterTrades() {
        return List.of(
            // Novice level trades
            new VillagerTrades.ItemsForEmeralds(ModItems.RAW_SAPPHIRE.get(), 1, 1, 16),
            new VillagerTrades.ItemsForEmeralds(ModBlocks.SAPPHIRE_ORE.get().asItem(), 2, 1, 12),
            new VillagerTrades.ItemsForEmeralds(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get().asItem(), 3, 1, 8),
            
            // Apprentice level trades
            new VillagerTrades.ItemsForEmeralds(ModItems.SAPPHIRE.get(), 2, 1, 12),
            new VillagerTrades.ItemsForEmeralds(ModBlocks.SAPPHIRE_BLOCK.get().asItem(), 4, 1, 8),
            new VillagerTrades.ItemsForEmeralds(ModBlocks.RAW_SAPPHIRE_BLOCK.get().asItem(), 3, 1, 8),
            
            // Journeyman level trades
            new VillagerTrades.EmeraldForItems(Items.EMERALD, 1, 2, 10),
            new VillagerTrades.EmeraldForItems(Items.DIAMOND, 1, 1, 8),
            
            // Expert level trades
            new VillagerTrades.ItemsForEmeralds(ModItems.SAPPHIRE.get(), 1, 2, 8),
            new VillagerTrades.ItemsForEmeralds(ModBlocks.SAPPHIRE_BLOCK.get().asItem(), 2, 1, 6),
            
            // Master level trades
            new VillagerTrades.ItemsForEmeralds(ModItems.SAPPHIRE.get(), 1, 3, 6),
            new VillagerTrades.ItemsForEmeralds(ModBlocks.SAPPHIRE_BLOCK.get().asItem(), 1, 2, 4)
        );
    }
    
    public static void addSapphireTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {
        List<VillagerTrades.ItemListing> sapphireTrades = getSapphireMasterTrades();
        
        // Add sapphire trades to librarian at different levels
        // Novice level (1) - add first 3 trades
        if (trades.containsKey(1)) {
            List<VillagerTrades.ItemListing> existing = trades.get(1);
            existing.add(sapphireTrades.get(0));
            existing.add(sapphireTrades.get(1));
            existing.add(sapphireTrades.get(2));
        }
        
        // Apprentice level (2) - add next 3 trades
        if (trades.containsKey(2)) {
            List<VillagerTrades.ItemListing> existing = trades.get(2);
            existing.add(sapphireTrades.get(3));
            existing.add(sapphireTrades.get(4));
            existing.add(sapphireTrades.get(5));
        }
        
        // Journeyman level (3) - add next 2 trades
        if (trades.containsKey(3)) {
            List<VillagerTrades.ItemListing> existing = trades.get(3);
            existing.add(sapphireTrades.get(6));
            existing.add(sapphireTrades.get(7));
        }
        
        // Expert level (4) - add next 2 trades
        if (trades.containsKey(4)) {
            List<VillagerTrades.ItemListing> existing = trades.get(4);
            existing.add(sapphireTrades.get(8));
            existing.add(sapphireTrades.get(9));
        }
        
        // Master level (5) - add last 2 trades
        if (trades.containsKey(5)) {
            List<VillagerTrades.ItemListing> existing = trades.get(5);
            existing.add(sapphireTrades.get(10));
            existing.add(sapphireTrades.get(11));
        }
    }
} 