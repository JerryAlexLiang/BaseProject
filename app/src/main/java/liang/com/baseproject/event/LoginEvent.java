package liang.com.baseproject.event;
/**
 * 创建日期：2019/8/23 on 11:16
 * 描述: EventBus - LoginEvent
 * 作者: liangyang
 */
public class LoginEvent extends BaseEvent {

    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
