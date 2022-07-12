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
package me.lemonypancakes.bukkit.origins.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public final class FileUtils {

    private FileUtils() {}

    public static void exportDataPack(JavaPlugin javaPlugin, String resourcePathFromJar, String toFileName) {
        String s = File.separator;
        File serverContainer = new File(javaPlugin.getServer().getWorldContainer().getAbsolutePath());
        File serverProperties = new File(serverContainer.getAbsolutePath() + s + "server.properties");

        try (InputStream stream = Files.newInputStream(serverProperties.toPath())) {
            Properties properties = new Properties();

            properties.load(stream);
            File defaultWorld = new File(serverContainer.getAbsolutePath() + s + properties.getProperty("level-name"));
            File datapacksFolder = new File(defaultWorld.getAbsolutePath() + s + "datapacks");
            InputStream inputStream = javaPlugin.getResource(resourcePathFromJar);

            if (inputStream != null) {
                try {
                    Files.copy(inputStream, new File(datapacksFolder.getAbsolutePath() + s + toFileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ignored) {}
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
