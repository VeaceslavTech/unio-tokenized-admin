package at.unio.admin.util;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class Notifications {

    public static Notification getSuccess(String text){
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        Div content = new Div();
        content.addClassName("my-notification");
        content.setText(text);
        notification.add(content);
        notification.setDuration(3000);
        return notification;
    }
}
