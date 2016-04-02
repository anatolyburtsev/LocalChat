package UI;

/**
 * Created by onotole on 03.04.16.
 */
public class Message {
    private String nickname;
    private String message;

    public void Message(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
