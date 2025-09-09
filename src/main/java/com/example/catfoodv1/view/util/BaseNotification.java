package com.example.catfoodv1.view.util;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Objects;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

public class BaseNotification {

    public enum NotificationType {
        SUCCESS_NOTIFY,
        FAILURE_NOTIFY,
        WARNING_NOTIFY;
    }

    private static final int DEFAULT_DURATION = 5000;

    public static Notification getSuccessNotification(String title, String details) {
        return getSpecifiedNotification(NotificationType.SUCCESS_NOTIFY, title, details);
    }

    public static Notification getFailNotification(String title, String details) {
        return getSpecifiedNotification(NotificationType.FAILURE_NOTIFY, title, details);
    }

    public static Notification getWarningNotification(String title, String details) {
        return getSpecifiedNotification(NotificationType.WARNING_NOTIFY, title, details);
    }

    public static Notification getSpecifiedNotification(NotificationType type, String title, String details) {
        Notification notification = new Notification();
        notification.setDuration(DEFAULT_DURATION);
        Icon icon = null;

        Div titleContainer = new Div(new Text(title));
        titleContainer.getStyle().set("font-weight", "600");

        String prop = "";

        switch (type) {
            case SUCCESS_NOTIFY -> {
                icon = VaadinIcon.CHECK_CIRCLE.create();
                prop = "success";
                icon.setColor("var(--lumo-success-color)");
                titleContainer.getStyle().setColor("var(--lumo-success-text-color)");
            }
            case FAILURE_NOTIFY -> {
                icon = VaadinIcon.CLOSE_CIRCLE.create();
                prop = "error";
            }
            case WARNING_NOTIFY -> {
                icon = VaadinIcon.WARNING.create();
                prop = "warning";
            }
            default -> {
            }
        }

        icon.setColor("var(--lumo-" + prop + "-color)");
        titleContainer.getStyle().setColor("var(--lumo-" + prop + "-text-color)");

        Div info = null;
        if (details != null) {
            Div detailsContainer = new Div(new Text(details));
            detailsContainer.getStyle().set("font-size", "var(--lumo-font-size-s)")
                    .setColor("var(--lumo-secondary-text-color)");
            info = new Div(titleContainer, detailsContainer);
        }

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(icon, Objects.requireNonNullElse(info, titleContainer), createCloseBtn(notification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);

        return notification;
    }

    public static Button createCloseBtn(Notification notification) {
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        return closeBtn;
    }
}
