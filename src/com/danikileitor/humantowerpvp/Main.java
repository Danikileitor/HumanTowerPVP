package com.danikileitor.humantowerpvp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("§2Plugin cargado correctamente");
		super.onEnable();
	}

	@Override
	public void onLoad() {
		getServer().broadcastMessage("Plugin cargado correctamente");
		super.onLoad();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	
	public ItemStack getEspada(){
		ItemStack item = new ItemStack(Material.GOLD_SWORD, 1);
		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add("Golpea y apila a tus enemigos");
		propied.setLore(lores);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName("§1§OLa Apiladora");
		item.setItemMeta(propied);

		return item;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		//if (!player.hasPlayedBefore()){
			PlayerInventory inventory = player.getInventory();
			ItemStack itemstack = getEspada();
			inventory.addItem(itemstack);
			player.sendMessage("§AHas recibido [§1La Apiladora§A]");
		//}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		PlayerInventory inventory = player.getInventory();
		ItemStack itemstack = getEspada();
		inventory.addItem(itemstack);
		player.sendMessage("§AHas recibido [§1La Apiladora§A]");
	}
	
	public boolean mismoItem(ItemStack a, ItemStack b){
		String n1, n2;
		if (a.hasItemMeta())
			n1= a.getItemMeta().getDisplayName();
		else
			n1 = a.getType().name();
		if (b.hasItemMeta())
			n2=b.getItemMeta().getDisplayName();
		else
			n2 = b.getType().name();
		return n1.equals(n2);
	}
	
	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e){
		if (e.getDamager() instanceof Player){
			getServer().broadcastMessage(e.getDamager().getName()+" > "+e.getEntity().getName());
			Player p = (Player) e.getDamager();
			@SuppressWarnings("deprecation")
			ItemStack mano = p.getItemInHand();
			ItemStack palo = getEspada();
			if (mismoItem(mano, palo)){
				List<Entity> montados = e.getEntity().getPassengers();
	        	for (int i = 0; i < montados.size(); i++) {
	    			p.addPassenger(montados.get(i));
				}
	        	p.addPassenger(e.getEntity());
			}
		}
	}
	
}
