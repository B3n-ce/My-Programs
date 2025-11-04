package DND.DND.Shields;

import DND.DND.Characters.Character;
import DND.DND.Weapons.Weapons;
import DND.DND.armor.Armors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Shields_Service
{
    @Autowired
    public Shields_Repository shields_repository;

    public List<Shields> getShields(String name, String email)
    {
        return shields_repository.findShieldsByNameAndEmail(name, email);
    }

    public Shields saveShields(Character character, String shield)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(shield);

            JsonNode shieldsArray = rootNode.path("shields");

            for (JsonNode shieldNode : shieldsArray) {
                String shieldName = shieldNode.path("name").asText();
                String ac = shieldNode.path("armor_class").asText();
                String weight = shieldNode.path("weight").asText();
                String properties = shieldNode.path("properties").asText();

                Shields shields = new Shields();
                shields.setName(character.getName());
                shields.setEmail(character.getUser());
                shields.setShield_name(shieldName);
                shields.setAc(ac);
                shields.setWeight(Integer.parseInt(weight));
                shields.setProperties(properties);
                shields_repository.save(shields);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateShields(Character character, String shieldJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(shieldJson);
            JsonNode shieldsArray = rootNode.path("shields");

            if (!shieldsArray.isArray()) return;

            List<Shields> existingShields = shields_repository.findShieldsByNameAndEmail(character.getName(), character.getUser());

            Map<String, Shields> existingMap = existingShields.stream()
                    .collect(Collectors.toMap(Shields::getShield_name, s -> s));

            for (JsonNode shieldNode : shieldsArray) {
                String shieldName = shieldNode.path("shield_name").asText();
                String ac = shieldNode.path("ac").asText();
                int weight = shieldNode.path("weight").asInt();
                String properties = shieldNode.path("properties").asText();

                if (existingMap.containsKey(shieldName)) {
                    Shields existing = existingMap.get(shieldName);
                    boolean needsUpdate = false;

                    if (!existing.getAc().equals(ac)) {
                        existing.setAc(ac);
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
                        shields_repository.save(existing);
                    }

                    existingMap.remove(shieldName);
                } else {
                    Shields newShield = new Shields();
                    newShield.setName(character.getName());
                    newShield.setEmail(character.getUser());
                    newShield.setShield_name(shieldName);
                    newShield.setAc(ac);
                    newShield.setWeight(weight);
                    newShield.setProperties(properties);
                    shields_repository.save(newShield);
                }
            }

            for (Shields shield : existingMap.values()) {
                shields_repository.deleteById(shield.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
