package DND.DND.ChatRoom;

import DND.DND.ChatRoom.Maps.Map_Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.*;

@Controller
public class WebsocketController {

    private final SimpMessagingTemplate messagingTemplate;

    private final Map<String, Map<String, Role>> roomUsers = new HashMap<>();

    private final Map<String, Set<String>> mutedUsers = new HashMap<>();

    private final Map<String, Set<String>> operators = new HashMap<>();

    private final Map<String, List<Map<String, Object>>> roomMaps = new HashMap<>();

    @MessageMapping("/chat.sendMapState")
    public void receiveMapState(@Payload Map<String, Object> msg) {
        String roomId = (String) msg.get("roomId");
        List<Map<String, Object>> mapState = (List<Map<String, Object>>) msg.get("mapState");

        roomMaps.put(roomId, mapState);
    }

    public WebsocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload MapMessage mapMessage, SimpMessageHeaderAccessor headerAccessor, Principal principal) throws JsonProcessingException {
        String roomId = mapMessage.getRoomId();
        String username = mapMessage.getSender();

        roomUsers.putIfAbsent(roomId, new HashMap<>());
        Map<String, Role> usersInRoom = roomUsers.get(roomId);

        Role role = usersInRoom.isEmpty() ? Role.ADMIN : Role.OBSERVER;

        headerAccessor.getSessionAttributes().put("username", username);
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        headerAccessor.getSessionAttributes().put("role", role);

        usersInRoom.put(username, role);
        if (usersInRoom.size() == 1)
        {
            LogEvents.log("---------------------------------");
            LogEvents.log("Szoba létrehozva!");
            LogEvents.log("Szoba id: " + roomId);
            LogEvents.log("Admin: " + username);
            LogEvents.log("Felhasználók: " + usersInRoom);

            String email = principal.getName();
            List<DND.DND.ChatRoom.Maps.Map> maps = map_repository.findAllMaps(email);
            if (!maps.isEmpty())
            {
                ObjectMapper mapper = new ObjectMapper();

                Map<String, List<Map<String, Object>>> allMapsState = new HashMap<>();

                for (DND.DND.ChatRoom.Maps.Map map : maps) {
                    String json = map.getMap_data();
                    List<Map<String, Object>> mapState =
                            mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});

                    allMapsState.put(map.getMap_name(), mapState);
                }

                MapMessage map_choose = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.ALERT)
                        .content("MAP_CHOOSE")
                        .allMapState(allMapsState)
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, map_choose);
                LogEvents.log("---------------------------------");
                LogEvents.log("Map mentve Betöltve általa: " + username);
            }
        }
        else if (usersInRoom.size() > 1)
        {
            LogEvents.log("---------------------------------");
            LogEvents.log(username + "csatlakozott!");
            LogEvents.log("Felhasználók: " + usersInRoom);
        }

        mapMessage.setType(MessageType.JOIN);
        mapMessage.setContent(role.name());
        messagingTemplate.convertAndSend("/topic/" + roomId, mapMessage);

        Set<String> mutedSet = mutedUsers.getOrDefault(roomId, Collections.emptySet());

        MapMessage muteListMessage = MapMessage.builder()
                .roomId(roomId)
                .type(MessageType.MUTE_LIST)
                .mutedUsers(new ArrayList<>(mutedSet))
                .build();

        messagingTemplate.convertAndSend("/topic/" + roomId, muteListMessage);

        String adminName = usersInRoom.entrySet().stream()
                .filter(e -> e.getValue() == Role.ADMIN)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        Set<String> operatorSet = operators.getOrDefault(roomId, Collections.emptySet());

        MapMessage rolesMessage = MapMessage.builder()
                .roomId(roomId)
                .type(MessageType.OPERATOR_LIST)
                .adminName(adminName)
                .operators(new ArrayList<>(operatorSet))
                .build();

        messagingTemplate.convertAndSend("/topic/" + roomId, rolesMessage);

        List<Map<String, Object>> currentMap = roomMaps.get(roomId);
        if (currentMap != null && !currentMap.isEmpty()) {
            MapMessage mapMessageToSend = MapMessage.builder()
                    .roomId(roomId)
                    .type(MessageType.MAP_STATE)
                    .mapState(new ArrayList<>(currentMap))
                    .build();

            messagingTemplate.convertAndSend("/topic/" + roomId, mapMessageToSend);
        }
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MapMessage mapMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        Role role = (Role) headerAccessor.getSessionAttributes().get("role");

        String adminUsername = null;
        Map<String, Role> usersInRoom = roomUsers.get(roomId);
        if (usersInRoom != null) {
            for (Map.Entry<String, Role> entry : usersInRoom.entrySet()) {
                if (entry.getValue() == Role.ADMIN) {
                    adminUsername = entry.getKey();
                }
            }
        }

        if (username == null || roomId == null || role == null) {
            return;
        }

        Set<String> operatorSet = operators.getOrDefault(roomId, Collections.emptySet());
        boolean isAdmin = role == Role.ADMIN;
        boolean isOperator = operatorSet.contains(username);

        MessageType type = mapMessage.getType();
        if ((type == MessageType.OBJECT_ADD || type == MessageType.OBJECT_MOVE || type == MessageType.OBJECT_REMOVE)
                && !(isAdmin || isOperator)) {
            return;
        }

        if (!roomId.equals(mapMessage.getRoomId()) || !username.equals(mapMessage.getSender())) {
            return;
        }

        Set<String> mutedSet = mutedUsers.get(roomId);
        if (mutedSet != null && mutedSet.contains(username)) {
            return;
        }

        if (type == MessageType.ADMIN_ACTION) {
            if (role != Role.ADMIN) {
                return;
            }

            String targetUser = mapMessage.getTargetUser();
            String action = mapMessage.getAdminAction();

            if ("KICK".equalsIgnoreCase(action)) {
                roomUsers.get(roomId).remove(targetUser);

                Set<String> mutedSet1 = mutedUsers.get(roomId);
                if (mutedSet1 != null) {
                    mutedSet1.remove(targetUser);
                }

                Set<String> operatorSet3 = operators.get(roomId);
                if (operatorSet3 != null) {
                    operatorSet3.remove(targetUser);
                }

                MapMessage kickMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.LEAVE)
                        .sender(targetUser)
                        .content("KICKED")
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, kickMessage);


                MapMessage rolesMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.OPERATOR_LIST)
                        .adminName(adminUsername)
                        .operators(new ArrayList<>(operatorSet3))
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, rolesMessage);

                LogEvents.log("---------------------------------");
                LogEvents.log(targetUser + " ki lett dobva!");
                LogEvents.log("Felhasználók: " + roomUsers.get(roomId));
                LogEvents.log("Mute- olt felhasználók: " + mutedSet1);
                LogEvents.log("Operator felhasználók: " + operatorSet3);
            }
            else if ("MUTE".equalsIgnoreCase(action)) {
                mutedUsers.putIfAbsent(roomId, new HashSet<>());

                mutedUsers.get(roomId).add(targetUser);

                MapMessage muteMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.MUTE)
                        .sender(mapMessage.getSender())
                        .targetUser(targetUser)
                        .content("MUTE")
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, muteMessage);

                MapMessage systemMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.CHAT)
                        .targetUser(targetUser)
                        .content("SYSTEM-MUTE")
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, systemMessage);

                Set<String> mutedSet2 = mutedUsers.getOrDefault(roomId, Collections.emptySet());
                //System.out.println(mutedSet2);

                MapMessage muteListMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.MUTE_LIST)
                        .mutedUsers(new ArrayList<>(mutedSet2))
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, muteListMessage);

                LogEvents.log("---------------------------------");
                LogEvents.log(targetUser + " le lett mute-olva!");
                LogEvents.log("Felhasználók: " + roomUsers.get(roomId));
                LogEvents.log("Mute- olt felhasználók: " + mutedUsers.get(roomId));
                LogEvents.log("Operator felhasználók: " + operators.get(roomId));
            }
            else if ("UNMUTED".equalsIgnoreCase(action)) {
                Set<String> mutedSet1 = mutedUsers.get(roomId);
                if (mutedSet1 != null) {
                    mutedSet1.remove(targetUser);
                }

                MapMessage unmuteMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.MUTE)
                        .sender(mapMessage.getSender())
                        .targetUser(targetUser)
                        .content("Unmuted")
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, unmuteMessage);

                MapMessage systemMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.CHAT)
                        .targetUser(targetUser)
                        .content("SYSTEM-UNMUTE")
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, systemMessage);

                Set<String> mutedSet3 = mutedUsers.getOrDefault(roomId, Collections.emptySet());

                MapMessage muteListMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.MUTE_LIST)
                        .mutedUsers(new ArrayList<>(mutedSet3))
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, muteListMessage);

                LogEvents.log("---------------------------------");
                LogEvents.log(targetUser + " némítása feloldva!");
                LogEvents.log("Felhasználók: " + roomUsers.get(roomId));
                LogEvents.log("Mute- olt felhasználók: " + mutedUsers.get(roomId));
                LogEvents.log("Operator felhasználók: " + operators.get(roomId));
            }
            else if ("ADD_OPERATOR".equalsIgnoreCase(action)) {
                operators.putIfAbsent(roomId, new HashSet<>());
                operators.get(roomId).add(targetUser);

                Set<String> operatorSet1 = operators.getOrDefault(roomId, Collections.emptySet());

                MapMessage rolesMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.OPERATOR_LIST)
                        .adminName(adminUsername)
                        .operators(new ArrayList<>(operatorSet1))
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, rolesMessage);

                LogEvents.log("---------------------------------");
                LogEvents.log(targetUser + " operator lett!");
                LogEvents.log("Felhasználók: " + roomUsers.get(roomId));
                LogEvents.log("Mute- olt felhasználók: " + mutedUsers.get(roomId));
                LogEvents.log("Operator felhasználók: " + operators.get(roomId));
            }
            else if ("NO_OPERATOR".equalsIgnoreCase(action)) {
                operators.get(roomId).remove(targetUser);

                Set<String> operatorSet2 = operators.getOrDefault(roomId, Collections.emptySet());

                MapMessage rolesMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.OPERATOR_LIST)
                        .adminName(adminUsername)
                        .operators(new ArrayList<>(operatorSet2))
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, rolesMessage);

                LogEvents.log("---------------------------------");
                LogEvents.log(targetUser + " már nem oprator!");
                LogEvents.log("Felhasználók: " + roomUsers.get(roomId));
                LogEvents.log("Mute- olt felhasználók: " + mutedUsers.get(roomId));
                LogEvents.log("Operator felhasználók: " + operators.get(roomId));
            }
            return;
        }

        messagingTemplate.convertAndSend("/topic/" + roomId, mapMessage);
    }

    @PostMapping("/chat/leave")
    public ResponseEntity<String> handleLeave(@RequestBody Map<String, String> payload) {
        String username = payload.get("sender");
        String roomId = payload.get("roomId");

        if (username == null || roomId == null) {
            return ResponseEntity.badRequest().body("Missing username or roomId");
        }

        String adminUsername = null;
        Map<String, Role> usersInRoom = roomUsers.get(roomId);
        if (usersInRoom != null) {
            for (Map.Entry<String, Role> entry : usersInRoom.entrySet()) {
                if (entry.getValue() == Role.ADMIN) {
                    adminUsername = entry.getKey();
                }
            }
        }

        if (username.equals(adminUsername)) {
            MapMessage closeRoomMessage = MapMessage.builder()
                    .roomId(roomId)
                    .type(MessageType.ROOM_CLOSED)
                    .content("Az admin kilépett, a szoba megszűnt.")
                    .build();

            messagingTemplate.convertAndSend("/topic/" + roomId, closeRoomMessage);

            roomUsers.remove(roomId);
            mutedUsers.remove(roomId);
            operators.remove(roomId);
            roomMaps.remove(roomId);

            LogEvents.log("---------------------------------");
            LogEvents.log("Az admin kilépett, a szoba bezárva: " + roomId);
            LogEvents.log("Felhasználók: " + roomUsers.get(roomId));
            LogEvents.log("Mute-olt felhasználók: " + mutedUsers.get(roomId));
            LogEvents.log("Operator felhasználók: " + operators.get(roomId));
        }
        else {
            if (roomUsers.get(roomId) != null)
            {
                roomUsers.get(roomId).remove(username);
                if (mutedUsers.get(roomId) != null) {
                    mutedUsers.get(roomId).remove(username);
                }

                if (operators.get(roomId) != null) {
                    operators.get(roomId).remove(username);
                }

                Set<String> operatorSet1 = operators.getOrDefault(roomId, Collections.emptySet());
                MapMessage rolesMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.OPERATOR_LIST)
                        .adminName(adminUsername)
                        .operators(new ArrayList<>(operatorSet1))
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, rolesMessage);

                Set<String> mutedSet3 = mutedUsers.getOrDefault(roomId, Collections.emptySet());

                MapMessage muteListMessage = MapMessage.builder()
                        .roomId(roomId)
                        .type(MessageType.MUTE_LIST)
                        .mutedUsers(new ArrayList<>(mutedSet3))
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomId, muteListMessage);

                LogEvents.log("---------------------------------");
                LogEvents.log(username + " kilépett (sendBeacon)!");
                LogEvents.log("Felhasználók: " + roomUsers.get(roomId));
                LogEvents.log("Mute-olt felhasználók: " + mutedUsers.get(roomId));
                LogEvents.log("Operator felhasználók: " + operators.get(roomId));
            }
        }
        return ResponseEntity.ok("Leave processed");
    }

    @Autowired
    private Map_Repository map_repository;

    @MessageMapping("/chat.saveMapState")
    public void saveMapState(@Payload Map<String, Object> msg, Principal principal) throws JsonProcessingException {
        String email = principal.getName();

        String roomid = (String) msg.get("roomId");

        String MapName = (String) msg.get("content");
        List<Map<String, Object>> mapState = (List<Map<String, Object>>) msg.get("mapState");
        ObjectMapper mapper = new ObjectMapper();
        String map_string = mapper.writeValueAsString(mapState);

        List<DND.DND.ChatRoom.Maps.Map> maps = map_repository.findAllMaps(email);
        if (maps.size() >= 5)
        {
            MapMessage too_many_maps = MapMessage.builder()
                    .roomId(roomid)
                    .type(MessageType.ALERT)
                    .content("MAPS_NUMBER")
                    .build();

            messagingTemplate.convertAndSend("/topic/" + roomid, too_many_maps);
            return;
        }

        for (DND.DND.ChatRoom.Maps.Map map : maps)
        {
            if (map.getMap_name().equals(MapName))
            {
                map.setMap_data(map_string);
                map_repository.save(map);
                MapMessage update_message = MapMessage.builder()
                        .roomId(roomid)
                        .type(MessageType.ALERT)
                        .content("MAP_UPDATE")
                        .build();

                messagingTemplate.convertAndSend("/topic/" + roomid, update_message);
                LogEvents.log("---------------------------------");
                LogEvents.log("Map frissítve: " + MapName);
                return;
            }
        }

        DND.DND.ChatRoom.Maps.Map map = new DND.DND.ChatRoom.Maps.Map();
        map.setEmail(email);
        map.setMap_name(MapName);
        map.setMap_data(map_string);
        map_repository.save(map);

        MapMessage update_message = MapMessage.builder()
                .roomId(roomid)
                .type(MessageType.ALERT)
                .content("MAP_CREATE")
                .build();

        messagingTemplate.convertAndSend("/topic/" + roomid, update_message);
        LogEvents.log("---------------------------------");
        LogEvents.log("Map mentve: " + MapName);
    }
}