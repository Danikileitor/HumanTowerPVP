package com.danikileitor.humantowerpvp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
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
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e){
		if (e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			ItemStack mano = p.getItemInHand();
			ItemStack espada = getEspada();
			if (mismoItem(mano, espada)){
				getServer().broadcastMessage(e.getDamager().getName()+" > "+e.getEntity().getName());
				e.getEntity().setSilent(true);
				List<Entity> montados = e.getEntity().getPassengers();
	        	for (int i = 0; i < montados.size(); i++) {
	        		
	        		//if (p.isEmpty() == true) {
	        		//Location loc1 = p.getLocation().add(2,0,0);
	        		//Location loc2 = p.getLocation().add(4,0,0);
	        		//Location loc3 = p.getLocation().add(6,0,0);
	        		//World world = p.getWorld();
	        		//byte cero = 0;
	        		//FallingBlock hueco1 = (FallingBlock) world.spawnFallingBlock(loc1, Material.AIR, cero);
	        		//FallingBlock hueco2 = (FallingBlock) world.spawnFallingBlock(loc2, Material.AIR, cero);
	        		//FallingBlock hueco3 = (FallingBlock) world.spawnFallingBlock(loc3, Material.AIR, cero);
	        		//	p.addPassenger(hueco1);
	        		//	hueco1.addPassenger(hueco2);
	        		//	hueco2.addPassenger(hueco3);
	        		//}
	        		
	    			p.addPassenger(montados.get(i));
	        	}
	        	p.addPassenger(e.getEntity());
			}
		}
	}
	
}
