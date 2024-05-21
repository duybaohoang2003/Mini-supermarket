package Model;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static Map<String, Object> session = new HashMap<>();

    public static void setUserInfo(String userId, String Username) {
        session.put("userId", userId);
        session.put("name", Username); // Thay đổi "username" thành "name"
    }

    public static String getUserId() {
        return (String) session.get("userId");
    }

    public static String getUserName() {
        return (String) session.get("name"); // Thay đổi "username" thành "name"
    }
    
    public static void clearSession() {
        session.clear();
    }

    // Các phương thức khác để làm việc với session
}
