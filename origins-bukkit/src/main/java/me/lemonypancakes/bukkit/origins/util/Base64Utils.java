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

import java.io.*;
import java.util.Base64;

public class Base64Utils {

    public static String encode(String string) {
        try{
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(io);

            os.writeObject(string);

            os.flush();
            byte[] rawData = io.toByteArray();
            String encodedData = Base64.getEncoder().encodeToString(rawData);

            os.close();

            return encodedData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decode(String string) {
        byte[] rawData = Base64.getDecoder().decode(string);

        try{
            ByteArrayInputStream io = new ByteArrayInputStream(rawData);
            ObjectInputStream in = new ObjectInputStream(io);

            String decodedData = (String) in.readObject();

            in.close();

            return decodedData;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
