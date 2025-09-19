package com.example.catfoodv1.view.component;

import com.example.catfoodv1.view.util.NotificationUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.popover.PopoverPosition;
import com.vaadin.flow.component.popover.PopoverVariant;
import com.vaadin.flow.data.binder.Binder;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public abstract class FormPopover<T> extends Popover {

    protected final Binder<T> binder;
    private final H3 title = new H3();
    private final Button confirmButton = new Button("儲存");
    private final HorizontalLayout headerActions = new HorizontalLayout();
    private final VerticalLayout mainLayout = new VerticalLayout();
    private final VerticalLayout contentWrapper = new VerticalLayout();

    private Consumer<T> saveListener;

    public FormPopover(Class<T> beanType) {
        this.binder = new Binder<>(beanType);

        configurePopover();
        buildFrameLayout();

        // The title is determined by the subclass and is static. Set it once.
        this.title.setText(getFormTitle());

        confirmButton.addClickListener(e -> save());
    }

    /**
     * 提供確認按鈕，讓子類別可以將其放置在自定義表單佈局中。
     * @return The confirm button instance.
     */
    protected Button getConfirmButton() {
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return confirmButton;
    }

    /**
     * 提供一個容器，讓子類別可以向標頭添加自定義操作（例如按鈕）。
     * @return A HorizontalLayout container for header actions.
     */
    protected HorizontalLayout getHeaderActions() {
        return headerActions;
    }
    
    /**
     * 子類別必須實現此方法來提供 Popover 的標題。
     */
    protected abstract String getFormTitle();

    /**
     * 子類別必須實現此方法來執行實際的保存操作（例如，呼叫 Service）。
     */
    protected abstract T performSave(T bean);

    /**
     * 註冊一個監聽器，在成功保存後被呼叫。
     */
    public void addSaveListener(Consumer<T> listener) {
        this.saveListener = listener;
    }

    /**
     * 子類別呼叫此方法來設置表單內容。
     * @param formContent The component representing the form.
     */
    protected void setFormContent(Component formContent) {
        contentWrapper.removeAll();
        contentWrapper.add(formContent);
    }

    /**
     * 每次打開 Popover 時呼叫此方法，以重置表單狀態。
     */
    public void refresh(T bean) {
        binder.setBean(bean);
    }

    private void save() {
        if (!binder.validate().isOk()) {
            NotificationUtil.showWarning("請檢查表單必填項！");
            return;
        }
        try {
            T savedBean = performSave(binder.getBean());
            if (saveListener != null) {
                saveListener.accept(savedBean);
            }
            NotificationUtil.showSuccess("儲存成功");
            close();
        } catch (Exception e) {
            log.error("Save failed in FormPopover", e);
            NotificationUtil.showFailure("儲存失敗: " + e.getMessage());
        }
    }

    private void configurePopover() {
        addThemeVariants(PopoverVariant.LUMO_NO_PADDING, PopoverVariant.ARROW);
        setPosition(PopoverPosition.START_BOTTOM);
        setModal(true);
    }

    private void buildFrameLayout() {
        // Header
        headerActions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        HorizontalLayout header = new HorizontalLayout(title, headerActions);
        header.setPadding(true);
        header.getStyle().setMarginBottom("3px");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.setFlexGrow(1, title); // Title takes up available space, pushing actions to the right

        // Content (Placeholder)
        contentWrapper.setPadding(false);
        contentWrapper.setSpacing(false);

        mainLayout.add(header, contentWrapper);
        mainLayout.setPadding(true);
        mainLayout.setSpacing(false);
        mainLayout.getStyle().setMarginBottom("10px");
        add(mainLayout);
    }
}
