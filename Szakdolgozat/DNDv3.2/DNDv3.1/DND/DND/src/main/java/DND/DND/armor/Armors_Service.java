package DND.DND.armor;

import DND.DND.Characters.Character;
import DND.DND.Weapons.Weapons;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Armors_Service
{
    @Autowired
    public Armors_Repository armors_repository;

    public List<Armors> getArmors(String name, String email)
    {
        return armors_repository.findArmorsByNameAndEmail(name, email);
    }

    public Armors saveArmors(Character character, String armor)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(armor);

            JsonNode armorsArray = rootNode.path("armors");

            for (JsonNode armoreNode : armorsArray) {
                String armorName = armoreNode.path("name").asText();
                String ac = armoreNode.path("armor_class").asText();
                String weight = armoreNode.path("weight").asText();
                String properties = armoreNode.path("properties").asText();

                Armors armors = new Armors();
                armors.setName(character.getName());
                armors.setEmail(character.getUser());
                armors.setArmor_name(armorName);
                armors.setAc(ac);
                armors.setWeight(Integer.parseInt(weight));
                armors.setProperties(properties);
                armors_repository.save(armors);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateArmors(Character character, String armorJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(armorJson);
            JsonNode armorsArray = rootNode.path("armors");

            if (!armorsArray.isArray()) return;

            List<Armors> existingArmors = armors_repository.findArmorsByNameAndEmail(character.getName(), character.getUser());

            Map<String, Armors> existingMap = existingArmors.stream()
                    .collect(Collectors.toMap(Armors::getArmor_name, a -> a));

            for (JsonNode armorNode : armorsArray) {
                String armorName = armorNode.path("armor_name").asText();
                String ac = armorNode.path("ac").asText();
                int weight = armorNode.path("weight").asInt();
                String properties = armorNode.path("properties").asText();

                if (existingMap.containsKey(armorName)) {
                    Armors existing = existingMap.get(armorName);
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
                        armors_repository.save(existing);
                    }

                    existingMap.remove(armorName);
                } else {
                    Armors newArmor = new Armors();
                    newArmor.setName(character.getName());
                    newArmor.setEmail(character.getUser());
                    newArmor.setArmor_name(armorName);
                    newArmor.setAc(ac);
                    newArmor.setWeight(weight);
                    newArmor.setProperties(properties);
                    armors_repository.save(newArmor);
                }
            }

            for (Armors armor : existingMap.values()) {
                armors_repository.deleteById(armor.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
