package com.example.catfoodv1.view.util;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;

import java.util.List;
import java.util.Optional;

public class NotificationUtil {

    private static void openNotification(Notification notification) {
        Optional.ofNullable(UI.getCurrent()).ifPresent(ui -> {
            if (ui.isAttached()) { // 確保 UI 仍然附加到 session
                ui.access(notification::open);
            }
        });
    }

    public static void showCreateSuccess() {
        Notification notification = BaseNotification.getSuccessNotification("新增成功", null);
        openNotification(notification);
    }

    public static void showCreateSuccess(String details) {
        Notification notification = BaseNotification.getSuccessNotification("新增成功", details);
        openNotification(notification);
    }

    public static void showUpdateSuccess() {
        Notification notification = BaseNotification.getSuccessNotification("編輯成功", null);
        openNotification(notification);
    }

    public static void showUpdateSuccess(String details) {
        Notification notification = BaseNotification.getSuccessNotification("編輯成功", details);
        openNotification(notification);
    }

    public static void showDeleteSuccess() {
        Notification notification = BaseNotification.getSuccessNotification("刪除成功", null);
        openNotification(notification);
    }

    public static void showDeleteSuccess(String details) {
        Notification notification = BaseNotification.getSuccessNotification("刪除成功", details);
        openNotification(notification);
    }

    public static void showSearchSuccess() {
        Notification notification = BaseNotification.getSuccessNotification("搜尋成功", null);
        openNotification(notification);
    }

    public static void showSearchSuccess(Integer count) {
        Notification notification = BaseNotification.getSuccessNotification("搜尋成功", String.format("共有%d筆資料", count));
        openNotification(notification);
    }

    public static void showSearchNotFound() {
        Notification notification = BaseNotification.getWarningNotification("搜尋成功", "沒有符合的資料");
        openNotification(notification);
    }

    public static void showCreateFail() {
        Notification notification = BaseNotification.getFailNotification("新增失敗", null);
        openNotification(notification);
    }

    public static void showCreateFail(String details) {
        Notification notification = BaseNotification.getFailNotification("新增失敗", details);
        openNotification(notification);
    }

    public static void showUpdateFail() {
        Notification notification = BaseNotification.getFailNotification("編輯失敗", null);
        openNotification(notification);
    }

    public static void showUpdateFail(String details) {
        Notification notification = BaseNotification.getFailNotification("編輯失敗", details);
        openNotification(notification);
    }

    public static void showDeleteFail() {
        Notification notification = BaseNotification.getFailNotification("刪除失敗", null);
        openNotification(notification);
    }

    public static void showDeleteFail(String details) {
        Notification notification = BaseNotification.getFailNotification("刪除失敗", details);
        openNotification(notification);
    }

    public static void showSuccess(String title) {
        Notification notification = BaseNotification.getSuccessNotification(title, null);
        openNotification(notification);
    }

    public static void showSuccess(String title, String details) {
        Notification notification = BaseNotification.getSuccessNotification(title, details);
        openNotification(notification);
    }

    public static void showFailure(String title) {
        Notification notification = BaseNotification.getFailNotification(title, null);
        openNotification(notification);
    }

    public static void showFailure(String title, String details) {
        Notification notification = BaseNotification.getFailNotification(title, details);
        openNotification(notification);
    }

    public static void showWarning(String title) {
        Notification notification = BaseNotification.getWarningNotification(title, null);
        openNotification(notification);
    }

    public static void showWarning(String title, String details) {
        Notification notification = BaseNotification.getWarningNotification(title, details);
        openNotification(notification);
    }

    public static <T> void loadGridWithNotification(List<T> data) {
        if (data.isEmpty()) {
            showSearchNotFound();
        } else {
            showSearchSuccess(data.size());
        }
    }
}
