package DND.DND.Weapons;

import DND.DND.Characters.Character;
import DND.DND.Characters.CharacterDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Weapons_Service
{
    @Autowired
    private Weapons_Repository weapons_repository;

    public Weapons saveWeapons(Character character, String weapon)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(weapon);

            JsonNode weaponsArray = rootNode.path("weapons");

            for (JsonNode weaponNode : weaponsArray) {
                String weaponName = weaponNode.path("name").asText();
                String damage = weaponNode.path("damage").asText();
                String weight = weaponNode.path("weight").asText();
                String properties = weaponNode.path("properties").asText();

                Weapons weapons = new Weapons();
                weapons.setName(character.getName());
                weapons.setEmail(character.getUser());
                weapons.setWeapon_name(weaponName);
                weapons.setDamage(damage);
                weapons.setWeight(Integer.parseInt(weight));
                weapons.setProperties(properties);
                weapons_repository.save(weapons);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateWeapons(Character character, String weaponJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(weaponJson);
            JsonNode weaponsArray = rootNode.path("weapons");

            if (!weaponsArray.isArray()) return;

            List<Weapons> existingWeapons = weapons_repository.findWeaponsByNameAndEmail(character.getName(), character.getUser());

            Map<String, Weapons> existingMap = existingWeapons.stream()
                    .collect(Collectors.toMap(Weapons::getWeapon_name, w -> w));

            for (JsonNode weaponNode : weaponsArray) {
                String weaponName = weaponNode.path("weapon_name").asText();
                String damage = weaponNode.path("damage").asText();
                int weight = Integer.parseInt(weaponNode.path("weight").asText());
                String properties = weaponNode.path("properties").asText();

                if (existingMap.containsKey(weaponName)) {
                    Weapons existing = existingMap.get(weaponName);
                    boolean needsUpdate = false;

                    if (!existing.getDamage().equals(damage)) {
                        existing.setDamage(damage);
                        needsUpdate = true;
                    }
                    if (existing.getWeight() != weight) {
                        existing.setWeight(weight);
                        needsUpdate = true;
                    }
                    if (!existing.getProperties().equals(properties)) {
                        existing.setProperties(properties);
                        needsUpdate = true;
                    }

                    if (needsUpdate) {
                        weapons_repository.save(existing);
                    }

                    existingMap.remove(weaponName);
                } else {
                    Weapons newWeapon = new Weapons();
                    newWeapon.setName(character.getName());
                    newWeapon.setEmail(character.getUser());
                    newWeapon.setWeapon_name(weaponName);
                    newWeapon.setDamage(damage);
                    newWeapon.setWeight(weight);
                    newWeapon.setProperties(properties);
                    weapons_repository.save(newWeapon);
                }
            }

            for (Weapons weapon : existingMap.values()) {
                weapons_repository.deleteById(weapon.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Weapons> getWeapons(String name, String email)
    {
        return weapons_repository.findWeaponsByNameAndEmail(name, email);
    }
}
