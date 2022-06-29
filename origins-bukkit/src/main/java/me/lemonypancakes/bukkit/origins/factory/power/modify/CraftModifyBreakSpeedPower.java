package me.lemonypancakes.bukkit.origins.factory.power.modify;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
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

public class CraftModifyBreakSpeedPower extends CraftPower {

    private int modifier = 0;
    private Condition<Block> blockCondition;
    private final Map<Player, PotionEffect> potionEffectMap = new HashMap<>();

    public CraftModifyBreakSpeedPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("modifier")) {
            this.modifier = jsonObject.get("modifier").getAsInt();
        }
        this.blockCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "block_condition");
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

                        if (getMembers().contains(player)) {
                            if (getCondition().test(player) && blockCondition.test(block)) {
                                if (material.isSolid()) {
                                    if (modifier < 0) {
                                        if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                                            Bukkit.getScheduler().runTask(CraftModifyBreakSpeedPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
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
                                            });
                                        } else if (digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                                            Bukkit.getScheduler().runTask(CraftModifyBreakSpeedPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                                if (potionEffectMap.containsKey(player)) {
                                                    if (potionEffectMap.get(player) != null) {
                                                        player.addPotionEffect(potionEffectMap.get(player));
                                                        potionEffectMap.remove(player);
                                                    }
                                                }
                                            });
                                        } else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) {
                                            Bukkit.getScheduler().runTask(CraftModifyBreakSpeedPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
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
                                            Bukkit.getScheduler().runTask(CraftModifyBreakSpeedPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                                if (location.getBlock().getType().isSolid()) {
                                                    PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.FAST_DIGGING);

                                                    if (potionEffect != null) {
                                                        if (potionEffect.getAmplifier() != modifier - 1 && potionEffect.getDuration() < 1000000 * 20) {
                                                            potionEffectMap.put(player, potionEffect);
                                                            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                                        }
                                                    }
                                                    player.addPotionEffect(
                                                            new PotionEffect(
                                                                    PotionEffectType.FAST_DIGGING,
                                                                    Integer.MAX_VALUE,
                                                                    modifier - 1,
                                                                    false,
                                                                    false
                                                            )
                                                    );
                                                } else {
                                                    PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.FAST_DIGGING);

                                                    if (potionEffect != null) {
                                                        if (potionEffect.getAmplifier() == Math.abs(modifier) - 1 && potionEffect.getDuration() > 1000000 * 20) {
                                                            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                                        }
                                                    }
                                                }
                                            });
                                        } else if (digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                                            Bukkit.getScheduler().runTask(CraftModifyBreakSpeedPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
                                                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                                if (potionEffectMap.containsKey(player)) {
                                                    if (potionEffectMap.get(player) != null) {
                                                        player.addPotionEffect(potionEffectMap.get(player));
                                                        potionEffectMap.remove(player);
                                                    }
                                                }
                                            });
                                        } else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) {
                                            Bukkit.getScheduler().runTask(CraftModifyBreakSpeedPower.this.getPlugin().getJavaPlugin(), bukkitTask -> {
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
                    }
                });
    }
}
