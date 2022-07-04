/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.factory.power.temporary;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class CraftAquaAffinityPower extends CraftPower {

    private int modifier = 0;
    private final Map<Player, PotionEffect> potionEffectMap = new HashMap<>();

    public CraftAquaAffinityPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("modifier")) {
            this.modifier = jsonObject.get("modifier").getAsInt();
        }
        registerBlockBreakPacketListener();
    }

    @Override
    protected void onMemberRemove(Player player) {
        if (modifier < 0) {
            PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.SLOW_DIGGING);

            if (potionEffect != null) {
                if (potionEffect.getAmplifier() == modifier - 1 && potionEffect.getDuration() > 1000000 * 20) {
                    player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                }
            }
        } else {
            PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.FAST_DIGGING);

            if (potionEffect != null) {
                if (potionEffect.getAmplifier() == Math.abs(modifier) - 1 && potionEffect.getDuration() > 1000000 * 20) {
                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                }
            }
        }
        if (potionEffectMap.containsKey(player)) {
            if (potionEffectMap.get(player) != null) {
                player.addPotionEffect(potionEffectMap.get(player));
                potionEffectMap.remove(player);
            }
        }
    }

    private void registerBlockBreakPacketListener() {
        getPlugin().getProtocolManager().addPacketListener(
                new PacketAdapter(getPlugin().getJavaPlugin(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {

                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().getValues().get(0);
                        BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
                        int x = blockPosition.getX();
                        int y = blockPosition.getY();
                        int z = blockPosition.getZ();
                        Player player = event.getPlayer();
                        World world = player.getWorld();
                        Location location = new Location(world, x, y, z);
                        Block block = location.getBlock();
                        Material material = block.getType();

                        if (hasMember(player)) {
                            if (material.isSolid()) {
                                if (modifier < 0) {
                                    if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                                        Bukkit.getScheduler().runTask(CraftAquaAffinityPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                            if (player.getEyeLocation().getBlock().getType() == Material.WATER) {
                                                if (location.getBlock().getType().isSolid()) {
                                                    PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.SLOW_DIGGING);

                                                    if (potionEffect != null) {
                                                        if (potionEffect.getAmplifier() != Math.abs(modifier) - 1 && potionEffect.getDuration() < 1000000 * 20) {
                                                            potionEffectMap.put(player, potionEffect);
                                                            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                                        }
                                                    }
                                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, Math.abs(modifier) - 1, false, false));
                                                } else {
                                                    PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.SLOW_DIGGING);

                                                    if (potionEffect != null) {
                                                        if (potionEffect.getAmplifier() == Math.abs(modifier) - 1 && potionEffect.getDuration() > 1000000 * 20) {
                                                            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    } else if (digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                                        Bukkit.getScheduler().runTask(CraftAquaAffinityPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                                    if (potionEffectMap.containsKey(player)) {
                                                        if (potionEffectMap.get(player) != null) {
                                                            player.addPotionEffect(potionEffectMap.get(player));
                                                            potionEffectMap.remove(player);
                                                        }
                                                    }
                                        });
                                    } else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) {
                                        Bukkit.getScheduler().runTask(CraftAquaAffinityPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                                    player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                                    if (potionEffectMap.containsKey(player)) {
                                                        if (potionEffectMap.get(player) != null) {
                                                            player.addPotionEffect(potionEffectMap.get(player));
                                                            potionEffectMap.remove(player);
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                                        Bukkit.getScheduler().runTask(CraftAquaAffinityPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                            if (player.getEyeLocation().getBlock().getType() == Material.WATER) {
                                                if (location.getBlock().getType().isSolid()) {
                                                    PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.FAST_DIGGING);

                                                    if (potionEffect != null) {
                                                        if (potionEffect.getAmplifier() != modifier - 1 && potionEffect.getDuration() < 1000000 * 20) {
                                                            potionEffectMap.put(player, potionEffect);
                                                            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                                        }
                                                    }
                                                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, modifier - 1, false, false));
                                                } else {
                                                    PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.FAST_DIGGING);

                                                    if (potionEffect != null) {
                                                        if (potionEffect.getAmplifier() == Math.abs(modifier) - 1 && potionEffect.getDuration() > 1000000 * 20) {
                                                            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    } else if (digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                                        Bukkit.getScheduler().runTask(CraftAquaAffinityPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                                    if (potionEffectMap.containsKey(player)) {
                                                        if (potionEffectMap.get(player) != null) {
                                                            player.addPotionEffect(potionEffectMap.get(player));
                                                            potionEffectMap.remove(player);
                                                        }
                                                    }
                                                });
                                    } else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) {
                                        Bukkit.getScheduler().runTask(CraftAquaAffinityPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                                    if (potionEffectMap.containsKey(player)) {
                                                        if (potionEffectMap.get(player) != null) {
                                                            player.addPotionEffect(potionEffectMap.get(player));
                                                            potionEffectMap.remove(player);
                                                        }
                                                    }
                                                });
                                    }
                                }
                            }
                        }
                    }
                });
    }
}
