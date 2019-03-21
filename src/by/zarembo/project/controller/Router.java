package by.zarembo.project.controller;

/**
 * The type Router.
 */
public class Router {
    /**
     * Gets page path.
     *
     * @return the page path
     */
    String getPagePath() {
        return pagePath;
    }

    private String pagePath;
    private String ajaxAnswer;
    private RouterType route = RouterType.FORWARD;

    /**
     * Sets page path.
     *
     * @param pagePath the page path
     */
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    RouterType getRoute() {
        return route;
    }

    /**
     * Sets redirect route.
     */
    public void setRedirectRoute() {
        this.route = RouterType.REDIRECT;
    }

    /**
     * Sets ajax route.
     *
     * @param answer the answer
     */
    public void setAjaxRoute(String answer) {
        this.ajaxAnswer = answer;
        this.route = RouterType.AJAX;
    }

    /**
     * Gets ajax answer.
     *
     * @return the ajax answer
     */
    String getAjaxAnswer() {
        return ajaxAnswer;
    }

    /**
     * The enum Router type.
     */
    public enum RouterType {
        /**
         * Forward router type.
         */
        FORWARD,
        /**
         * Redirect router type.
         */
        REDIRECT,
        /**
         * Ajax router type.
         */
        AJAX
    }
}
