package by.langvest.plantopia.block;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.block.special.PlantopiaFireweedBlock;
import by.langvest.plantopia.block.special.PlantopiaTriplePlantBlock;
import by.langvest.plantopia.block.PlantopiaCompats.Compostability;
import by.langvest.plantopia.item.PlantopiaItems;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaBlockMeta.MetaProperties;
import by.langvest.plantopia.meta.PlantopiaBlockMeta.MetaType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PlantopiaBlocks {
	private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Plantopia.MOD_ID);

	public static final RegistryObject<Block> FIREWEED = registerBlock("fireweed", () -> new PlantopiaFireweedBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.PLANT).doubleHigh().customModel().customTint().dropSelfByShears().preferredByBees().compostable(Compostability.PLANT_2 + Compostability.HAS_FLOWERS));
	public static final RegistryObject<Block> GIANT_GRASS = registerBlock("giant_grass", () -> new PlantopiaTriplePlantBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.PLANT).tripleHigh().grassTint().customDrop().compostable(Compostability.PLANT_3));

	static {
		registerPottedBlocks();
	}

	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> supplier, MetaProperties metaProperties) {
		RegistryObject<T> blockRegistryObject = BLOCK_REGISTER.register(name, supplier);
		PlantopiaBlockMeta blockMeta = PlantopiaMetaStore.add(name, blockRegistryObject, metaProperties);
		PlantopiaItems.registerBlockItem(blockMeta);
		return blockRegistryObject;
	}

	private static void registerPottedBlocks() {
		PlantopiaMetaStore.getBlocks(PlantopiaBlockMeta::isPottable).forEach(blockMeta ->
			registerBlock("potted_" + blockMeta.getName(), () -> new FlowerPotBlock(null, blockMeta.getObject(), Properties.of(Material.DECORATION).instabreak()), MetaProperties.of(MetaType.POTTED).pottedBy(blockMeta.getObject()))
		);
	}

	public static void setup(IEventBus bus) {
		BLOCK_REGISTER.register(bus);
	}
}