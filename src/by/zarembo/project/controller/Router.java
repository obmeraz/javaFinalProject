package by.zarembo.project.controller;

public class Router {
    public enum RouterType {
        FORWARD, REDIRECT, AJAX
    }

    private String pagePath;
    private String ajaxAnswer;
    private RouterType route = RouterType.FORWARD;

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public RouterType getRoute() {
        return route;
    }

    public void setRedirectRoute() {
        this.route = RouterType.REDIRECT;
    }

    public void setAjaxRoute(String answer) {
        this.ajaxAnswer = answer;
        this.route = RouterType.AJAX;
    }

    public String getAjaxAnswer() {
        return ajaxAnswer;
    }
}
