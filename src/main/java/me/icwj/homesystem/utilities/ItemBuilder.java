package me.icwj.homesystem.utilities;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(final Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(final Material material, final int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(final Component name) {
        itemMeta.displayName(name);
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        itemMeta.lore(Arrays.stream(lore)
                .map(Component::text)
                .toList());
        return this;
    }

    public ItemBuilder setLore(final List<Component> lore) {
        itemMeta.lore(lore);
        return this;
    }

    public ItemBuilder addLoreLine(final Component line) {
        List<Component> lore = itemMeta.lore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(line);
        itemMeta.lore(lore);
        return this;
    }

    public ItemBuilder addEnchant(final Enchantment enchantment, final int level, final boolean unsafe) {
        itemMeta.addEnchant(enchantment, level, unsafe);
        return this;
    }

    public ItemBuilder addItemFlags(final ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setUnbreakable(final boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack get() {
        return build();
    }
}