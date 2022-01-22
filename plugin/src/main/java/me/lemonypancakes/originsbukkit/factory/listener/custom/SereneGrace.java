package me.lemonypancakes.originsbukkit.factory.listener.custom;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SereneGrace extends ListenerPowerContainer {

    public SereneGrace(Identifier identifier,
                       JsonObject jsonObject,
                       Action<?>[] actions,
                       Condition<?> condition,
                       boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public SereneGrace(Identifier identifier,
                       JsonObject jsonObject,
                       Action<?>[] actions,
                       boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public SereneGrace(Identifier identifier,
                       JsonObject jsonObject,
                       boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public SereneGrace(Identifier identifier,
                       boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public SereneGrace(boolean isFactory) {
        this(null, isFactory);
    }

    public SereneGrace() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new SereneGrace(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new SereneGrace(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new SereneGrace(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new SereneGrace(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new SereneGrace();
    }

    public static Power getFactory() {
        return new SereneGrace(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/custom/serene_grace"
                ), true
        );
    }

    private static final List<Material> MATERIAL_LIST = new ArrayList<>(
            Arrays.asList(
                    Material.ALLIUM,
                    Material.AZURE_BLUET,
                    Material.BLUE_ORCHID,
                    Material.DANDELION,
                    Material.CORNFLOWER,
                    Material.LILY_OF_THE_VALLEY,
                    Material.OXEYE_DAISY,
                    Material.POPPY,
                    Material.PINK_TULIP,
                    Material.ORANGE_TULIP,
                    Material.RED_TULIP,
                    Material.WHITE_TULIP,
                    Material.WITHER_ROSE
            )
    );

    @EventHandler
    private void onSelfAttackEntitySereneGrace(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity damager = event.getDamager();

        if (damager instanceof Player && victim instanceof LivingEntity) {
            Player player = (Player) damager;
            LivingEntity livingEntity = (LivingEntity) victim;

            if (getPlayersToListen().contains(player)) {
                EntityEquipment entityEquipment = player.getEquipment();

                if (entityEquipment != null) {
                    ItemStack mainHandItem = entityEquipment.getItemInMainHand();
                    ItemStack offHandItem = entityEquipment.getItemInOffHand();

                    if (MATERIAL_LIST.contains(mainHandItem.getType())) {
                        switch (mainHandItem.getType()) {
                            case ALLIUM:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.FIRE_RESISTANCE.createEffect(
                                                80, 0
                                        )
                                );
                                break;
                            case AZURE_BLUET:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.BLINDNESS.createEffect(
                                                160, 0
                                        )
                                );
                                break;
                            case BLUE_ORCHID:
                            case DANDELION:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.SATURATION.createEffect(
                                                7, 0
                                        )
                                );
                                break;
                            case CORNFLOWER:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.JUMP.createEffect(
                                                120, 0
                                        )
                                );
                                break;
                            case LILY_OF_THE_VALLEY:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.POISON.createEffect(
                                                240, 0
                                        )
                                );
                                break;
                            case OXEYE_DAISY:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.REGENERATION.createEffect(
                                                160, 0
                                        )
                                );
                                break;
                            case POPPY:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.NIGHT_VISION.createEffect(
                                                100, 0
                                        )
                                );
                                break;
                            case ORANGE_TULIP:
                            case PINK_TULIP:
                            case RED_TULIP:
                            case WHITE_TULIP:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.WEAKNESS.createEffect(
                                                180, 0
                                        )
                                );
                                break;
                            case WITHER_ROSE:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.WITHER.createEffect(
                                                160, 0
                                        )
                                );
                                break;
                        }
                        mainHandItem.setAmount(mainHandItem.getAmount() - 1);
                    }

                    if (MATERIAL_LIST.contains(offHandItem.getType())) {
                        switch (offHandItem.getType()) {
                            case ALLIUM:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.FIRE_RESISTANCE.createEffect(
                                                80, 0
                                        )
                                );
                                break;
                            case AZURE_BLUET:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.BLINDNESS.createEffect(
                                                160, 0
                                        )
                                );
                                break;
                            case BLUE_ORCHID:
                            case DANDELION:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.SATURATION.createEffect(
                                                7, 0
                                        )
                                );
                                break;
                            case CORNFLOWER:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.JUMP.createEffect(
                                                120, 0
                                        )
                                );
                                break;
                            case LILY_OF_THE_VALLEY:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.POISON.createEffect(
                                                240, 0
                                        )
                                );
                                break;
                            case OXEYE_DAISY:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.REGENERATION.createEffect(
                                                160, 0
                                        )
                                );
                                break;
                            case POPPY:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.NIGHT_VISION.createEffect(
                                                100, 0
                                        )
                                );
                                break;
                            case ORANGE_TULIP:
                            case PINK_TULIP:
                            case RED_TULIP:
                            case WHITE_TULIP:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.WEAKNESS.createEffect(
                                                180, 0
                                        )
                                );
                                break;
                            case WITHER_ROSE:
                                livingEntity.addPotionEffect(
                                        PotionEffectType.WITHER.createEffect(
                                                160, 0
                                        )
                                );
                                break;
                        }
                        offHandItem.setAmount(offHandItem.getAmount() - 1);
                    }
                }
            }
        }
    }
}
