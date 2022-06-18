package me.lemonypancakes.originsbukkit.wrapper;

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
