package com.example.catfoodv1.view.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.shared.Registration;

public class BaseDialog extends Dialog {

    protected final Button confirmButton = new Button("確定");
    protected final Button cancelButton = new Button("取消");
    protected Registration confirmRegistration;
    protected Registration cancelRegistration;
    protected Registration closeActionRegistration;

    public BaseDialog(String title, Component component) {
        setModal(true);
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        setWidth("25%");
        setMinWidth("250px");
        setHeaderTitle(title);

        if (component != null) {
            add(component);
        }

        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        getFooter().add(cancelButton, confirmButton);
    }

    public void removeFooterBtn(){
        getFooter().removeAll();
    }

    public BaseDialog(String title) {
        this(title, null);
    }

    public void setTitle(String title) {
        setHeaderTitle(title);
    }

    public void setCancelButtonDefaultEvent() {
        cancelButton.addClickListener(event -> close());
    }

    public void setCancelButtonEvent(Runnable runnable) {
        if (cancelRegistration != null) cancelRegistration.remove();
        cancelRegistration = cancelButton.addClickListener(event -> {
            runnable.run();
        });
    }

    public void setConfirmButtonEvent(Runnable runnable) {
        if (confirmRegistration != null) confirmRegistration.remove();
        confirmRegistration = confirmButton.addClickListener(event -> {
            runnable.run();
        });
    }

    public void setCancelButtonEventWithClose(Runnable runnable) {
        if (cancelRegistration != null) cancelRegistration.remove();
        cancelRegistration = cancelButton.addClickListener(event -> {
            runnable.run();
            close();
        });
    }

    public void setConfirmButtonEventWithClose(Runnable runnable) {
        if (confirmRegistration != null) confirmRegistration.remove();
        confirmRegistration = confirmButton.addClickListener(event -> {
            runnable.run();
            close();
        });
    }

    @Override
    public Registration addDialogCloseActionListener(ComponentEventListener<DialogCloseActionEvent> listener) {
        if (closeActionRegistration != null) closeActionRegistration.remove();
        closeActionRegistration = super.addDialogCloseActionListener(listener);
        return closeActionRegistration;
    }

    public void setDangerStyle() {
        confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }

    public void setCancelText(String text) {
        cancelButton.setText(text);
    }

    public void setConfirmText(String text) {
        confirmButton.setText(text);
    }
}
