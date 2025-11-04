package DND.DND.ChatRoom;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapMessage {
    private String roomId;
    private MessageType type;
    private String sender;

    private String objectId;
    private String objectType;
    private String objectData;

    private String content;

    private String targetUser;
    private String adminAction;

    private List<String> mutedUsers;
    private List<String> operators;

    private List<Map<String, Object>> mapState;

    private Map<String, List<Map<String, Object>>> allMapState;
    private String adminName;
}