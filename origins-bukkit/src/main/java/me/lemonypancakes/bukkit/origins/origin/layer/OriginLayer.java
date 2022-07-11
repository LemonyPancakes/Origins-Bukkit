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

import me.lemonypancakes.bukkit.origins.Identifiable;
import me.lemonypancakes.bukkit.origins.JsonObjectHolder;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPluginHolder;
import me.lemonypancakes.bukkit.origins.menu.Menu;
import me.lemonypancakes.bukkit.origins.wrapper.GUITitle;

import java.util.List;

public interface OriginLayer extends OriginsBukkitPluginHolder, Identifiable, JsonObjectHolder {

    List<Origin> getOrigins();

    void addOrigin(Origin origin);

    void removeOrigin(Origin origin);

    boolean hasOrigin(Origin origin);

    int getOrder();

    void setOrder(int order);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isReplace();

    void setReplace(boolean replace);

    String getName();

    void setName(String name);

    GUITitle getGuiTitle();

    void setGuiTitle(GUITitle guiTitle);

    String getMissingName();

    void setMissingName(String missingName);

    String getMissingDescription();

    void setMissingDescription(String missingDescription);

    boolean isAllowRandom();

    void setAllowRandom(boolean allowRandom);

    boolean isAllowRandomUnchoosable();

    void setAllowRandomUnchoosable(boolean allowRandomUnchoosable);

    boolean isExcludeRandom();

    void setExcludeRandom(boolean excludeRandom);

    Origin getDefaultOrigin();

    void setDefaultOrigin(Origin defaultOrigin);

    boolean isAutoChoose();

    void setAutoChoose(boolean autoChoose);

    boolean isHidden();

    void setHidden(boolean hidden);

    int getLoadingPriority();

    void setLoadingPriority(int loadingPriority);

    Menu getMenu();
}
