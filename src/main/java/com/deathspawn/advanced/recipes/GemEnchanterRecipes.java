package com.deathspawn.advanced.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.deathspawn.advanced.init.ModItems;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class GemEnchanterRecipes {

	private static final GemEnchanterRecipes ENCHANTING_BASE = new GemEnchanterRecipes();
	/** The list of smelting results. */
	private final Table<ItemStack, ItemStack, ItemStack> enchantingList = HashBasedTable
			.<ItemStack, ItemStack, ItemStack>create();
	/**
	 * A list which contains how many experience points each recipe output will
	 * give.
	 */
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	/**
	 * Returns an instance of FurnaceRecipes.
	 */
	public static GemEnchanterRecipes instance() {
		return ENCHANTING_BASE;
	}

	private GemEnchanterRecipes() {
		this.addEnchanting(ModItems.fireGem, Items.DIAMOND_AXE, new ItemStack(ModItems.fireAxe, 1), 5.0F);
		this.addEnchanting(ModItems.fireGem, Items.DIAMOND_HOE, new ItemStack(ModItems.fireHoe, 1), 5.0F);
		this.addEnchanting(ModItems.fireGem, Items.DIAMOND_PICKAXE, new ItemStack(ModItems.firePickaxe, 1), 5.0F);
		this.addEnchanting(ModItems.fireGem, Items.DIAMOND_SHOVEL, new ItemStack(ModItems.fireShovel, 1), 5.0F);
		this.addEnchanting(ModItems.fireGem, Items.DIAMOND_SWORD, new ItemStack(ModItems.fireSword, 1), 5.0F);
	}

	/**
	 * Adds a smelting recipe, where the input item is an instance of Block.
	 */
	public void addEnchantingRecipeForBlock(Block input, Item input2, ItemStack result, float experience) {
		this.addEnchanting(Item.getItemFromBlock(input), input2, result, experience);
	}

	/**
	 * Adds a smelting recipe using an Item as the input item.
	 */
	public void addEnchanting(Item input, Item input2, ItemStack stack, float experience) {
		this.addEnchantingRecipe(new ItemStack(input, 1, 32767), new ItemStack(input2, 1, 32767), stack, experience);
	}

	/**
	 * Adds a smelting recipe using an ItemStack as the input for the recipe.
	 */
	public void addEnchantingRecipe(ItemStack input, ItemStack input2, ItemStack stack, float experience) {
		if (getEnchantingResult(input, input2) != ItemStack.EMPTY)
			return;
		this.enchantingList.put(input, input2, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	/**
	 * Returns the smelting result of an item.
	 */
	public ItemStack getEnchantingResult(ItemStack input1, ItemStack input2) {
		for (Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.enchantingList.columnMap().entrySet()) {
			if (this.compareItemStacks(input1, (ItemStack) entry.getKey())) {
				for (Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
					if (this.compareItemStacks(input2, (ItemStack) ent.getKey())) {
						return (ItemStack) ent.getValue();
					}
				}
			}
		}

		return ItemStack.EMPTY;
	}

	/**
	 * Compares two itemstacks to ensure that they are the same. This checks both
	 * the item and the metadata of the item.
	 */
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Table<ItemStack, ItemStack, ItemStack> getEnchantingList() {
		return this.enchantingList;
	}

	public float getEnchantingExperience(ItemStack stack) {
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1)
			return ret;

		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
			if (this.compareItemStacks(stack, entry.getKey())) {
				return ((Float) entry.getValue()).floatValue();
			}
		}

		return 0.0F;
	}

}
