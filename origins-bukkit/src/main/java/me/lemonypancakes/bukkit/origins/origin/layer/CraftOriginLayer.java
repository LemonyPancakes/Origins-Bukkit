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
package me.lemonypancakes.bukkit.origins.origin.layer;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.menu.CraftOriginLayerMenu;
import me.lemonypancakes.bukkit.origins.menu.Menu;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.GUITitle;

import java.util.*;

public class CraftOriginLayer implements OriginLayer {

    private OriginsBukkitPlugin plugin;
    private Identifier identifier;
    private JsonObject jsonObject;
    private final List<Origin> origins = new LinkedList<>();
    private int order = Integer.MAX_VALUE;
    private boolean enabled = true;
    private boolean replace;
    private String name;
    private GUITitle guiTitle;
    private String missingName;
    private String missingDescription;
    private boolean allowRandom;
    private boolean allowRandomUnchoosable;
    private boolean excludeRandom;
    private Origin defaultOrigin;
    private boolean autoChoose;
    private boolean hidden;
    private int loadingPriority;
    private Menu menu;

    public CraftOriginLayer(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        setPlugin(plugin);
        setIdentifier(identifier);
        setJsonObject(jsonObject);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {
        if (this.plugin == null) {
            this.plugin = plugin;
        }
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        if (this.identifier == null) {
            this.identifier = identifier;
        }
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        if (this.jsonObject == null) {
            this.jsonObject = jsonObject;
            if (jsonObject != null) {
                if (jsonObject.has("origins")) {
                    String[] strings = new Gson().fromJson(jsonObject.get("origins"), String[].class);

                    Arrays.stream(strings).forEach(string -> {
                        Origin origin = plugin.getRegistry().getRegisteredOrigin(Identifier.fromString(string));

                        if (origin != null) {
                            addOrigin(origin);
                        }
                    });
                }
                if (jsonObject.has("enabled")) {
                    this.enabled = jsonObject.get("enabled").getAsBoolean();
                }
                if (jsonObject.has("replace")) {
                    this.replace = jsonObject.get("replace").getAsBoolean();
                }
                if (jsonObject.has("name")) {
                    this.name = jsonObject.get("name").getAsString();
                }
                if (jsonObject.has("gui_title")) {
                    this.guiTitle = new GUITitle(jsonObject.getAsJsonObject("gui_title"));
                }
                if (jsonObject.has("missing_name")) {
                    this.missingName = jsonObject.get("missing_name").getAsString();
                }
                if (jsonObject.has("missing_description")) {
                    this.missingDescription = jsonObject.get("missing_description").getAsString();
                }
                if (jsonObject.has("allow_random")) {
                    this.allowRandom = jsonObject.get("allow_random").getAsBoolean();
                }
                if (jsonObject.has("allow_random_unchoosable")) {
                    this.allowRandomUnchoosable = jsonObject.get("allow_random_unchoosable").getAsBoolean();
                }
                if (jsonObject.has("exclude_random")) {
                    this.excludeRandom = jsonObject.get("exclude_random").getAsBoolean();
                }
                if (jsonObject.has("default_origin")) {
                    this.defaultOrigin = plugin.getRegistry().getRegisteredOrigin(Identifier.fromString(jsonObject.get("default_origin").getAsString()));
                }
                if (jsonObject.has("auto_choose")) {
                    this.autoChoose = jsonObject.get("auto_choose").getAsBoolean();
                }
                if (jsonObject.has("hidden")) {
                    this.hidden = jsonObject.get("hidden").getAsBoolean();
                }
                if (jsonObject.has("loading_priority")) {
                    this.loadingPriority = jsonObject.get("loading_priority").getAsInt();
                }
                this.menu = new CraftOriginLayerMenu(plugin, this);
            }
        }
    }

    @Override
    public List<Origin> getOrigins() {
        return Collections.unmodifiableList(new LinkedList<>(origins));
    }

    @Override
    public void addOrigin(Origin origin) {
        if (!origins.contains(origin)) {
            origins.add(origin);
            origins.sort(Comparator.comparingInt(Origin::getOrder).thenComparing(Origin::getImpact));
        }
    }

    @Override
    public void removeOrigin(Origin origin) {
        origins.remove(origin);
        origins.sort(Comparator.comparingInt(Origin::getOrder).thenComparing(Origin::getImpact));
    }

    @Override
    public boolean hasOrigin(Origin origin) {
        return origins.contains(origin);
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isReplace() {
        return replace;
    }

    @Override
    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public GUITitle getGuiTitle() {
        return guiTitle;
    }

    @Override
    public void setGuiTitle(GUITitle guiTitle) {
        this.guiTitle = guiTitle;
    }

    @Override
    public String getMissingName() {
        return missingName;
    }

    @Override
    public void setMissingName(String missingName) {
        this.missingName = missingName;
    }

    @Override
    public String getMissingDescription() {
        return missingDescription;
    }

    @Override
    public void setMissingDescription(String missingDescription) {
        this.missingDescription = missingDescription;
    }

    @Override
    public boolean isAllowRandom() {
        return allowRandom;
    }

    @Override
    public void setAllowRandom(boolean allowRandom) {
        this.allowRandom = allowRandom;
    }

    @Override
    public boolean isAllowRandomUnchoosable() {
        return allowRandomUnchoosable;
    }

    @Override
    public void setAllowRandomUnchoosable(boolean allowRandomUnchoosable) {
        this.allowRandomUnchoosable = allowRandomUnchoosable;
    }

    @Override
    public boolean isExcludeRandom() {
        return excludeRandom;
    }

    @Override
    public void setExcludeRandom(boolean excludeRandom) {
        this.excludeRandom = excludeRandom;
    }

    @Override
    public Origin getDefaultOrigin() {
        return defaultOrigin;
    }

    @Override
    public void setDefaultOrigin(Origin defaultOrigin) {
        this.defaultOrigin = defaultOrigin;
    }

    @Override
    public boolean isAutoChoose() {
        return autoChoose;
    }

    @Override
    public void setAutoChoose(boolean autoChoose) {
        this.autoChoose = autoChoose;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public int getLoadingPriority() {
        return loadingPriority;
    }

    @Override
    public void setLoadingPriority(int loadingPriority) {
        this.loadingPriority = loadingPriority;
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftOriginLayer)) return false;
        CraftOriginLayer that = (CraftOriginLayer) itemStack;
        return order == that.order && enabled == that.enabled && replace == that.replace && allowRandom == that.allowRandom && allowRandomUnchoosable == that.allowRandomUnchoosable && excludeRandom == that.excludeRandom && autoChoose == that.autoChoose && hidden == that.hidden && loadingPriority == that.loadingPriority && Objects.equals(plugin, that.plugin) && Objects.equals(identifier, that.identifier) && Objects.equals(jsonObject, that.jsonObject) && Objects.equals(origins, that.origins) && Objects.equals(name, that.name) && Objects.equals(guiTitle, that.guiTitle) && Objects.equals(missingName, that.missingName) && Objects.equals(missingDescription, that.missingDescription) && Objects.equals(defaultOrigin, that.defaultOrigin) && Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, identifier, jsonObject);
    }

    @Override
    public String toString() {
        return "CraftOriginLayer{" +
                "plugin=" + plugin +
                ", identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", origins=" + origins +
                ", order=" + order +
                ", enabled=" + enabled +
                ", replace=" + replace +
                ", name='" + name + '\'' +
                ", guiTitle=" + guiTitle +
                ", missingName='" + missingName + '\'' +
                ", missingDescription='" + missingDescription + '\'' +
                ", allowRandom=" + allowRandom +
                ", allowRandomUnchoosable=" + allowRandomUnchoosable +
                ", excludeRandom=" + excludeRandom +
                ", defaultOrigin=" + defaultOrigin +
                ", autoChoose=" + autoChoose +
                ", hidden=" + hidden +
                ", loadingPriority=" + loadingPriority +
                ", menu=" + menu +
                '}';
    }
}
