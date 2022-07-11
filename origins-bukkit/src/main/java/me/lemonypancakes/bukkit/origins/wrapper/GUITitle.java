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
package me.lemonypancakes.bukkit.origins.wrapper;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;

public class GUITitle {

    private String chooseOrigin;
    private String viewOrigin;

    public GUITitle(JsonObject jsonObject) {
        if (jsonObject.has("choose_origin")) {
            this.chooseOrigin = jsonObject.get("choose_origin").getAsString();
        }
        if (jsonObject.has("view_origin")) {
            this.viewOrigin = jsonObject.get("view_origin").getAsString();
        }
    }

    public String getChooseOrigin() {
        return chooseOrigin;
    }

    public void setChooseOrigin(String chooseOrigin) {
        this.chooseOrigin = chooseOrigin;
    }

    public String getViewOrigin() {
        return viewOrigin;
    }

    public void setViewOrigin(String viewOrigin) {
        this.viewOrigin = viewOrigin;
    }
}
