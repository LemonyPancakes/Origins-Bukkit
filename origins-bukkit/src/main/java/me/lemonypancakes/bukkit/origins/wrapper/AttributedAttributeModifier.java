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

import com.google.gson.JsonObject;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public class AttributedAttributeModifier {

    private Attribute attribute;
    private AttributeModifier modifier;

    public AttributedAttributeModifier(JsonObject jsonObject) {
        if (jsonObject != null) {
            String name = null;
            double value = 0;
            AttributeModifier.Operation operation = null;

            if (jsonObject.has("name")) {
                name = jsonObject.get("name").getAsString();
            }
            if (jsonObject.has("value")) {
                value = jsonObject.get("value").getAsDouble();
            }
            if (jsonObject.has("operation")) {
                operation = AttributeModifier.Operation.valueOf(jsonObject.get("operation").getAsString());
            }
            if (name == null) {
                return;
            }
            if (operation == null) {
                return;
            }
            this.modifier = new AttributeModifier(name, value, operation);
            if (jsonObject.has("attribute")) {
                this.attribute = Attribute.valueOf(jsonObject.get("attribute").getAsString());
            }
        }
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public AttributeModifier getModifier() {
        return modifier;
    }

    public void setModifier(AttributeModifier modifier) {
        this.modifier = modifier;
    }
}
