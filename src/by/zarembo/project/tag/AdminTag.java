package by.zarembo.project.tag;

import by.zarembo.project.entity.RoleType;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * The type Admin tag.
 */
public class AdminTag extends TagSupport {
    private RoleType role;

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        return role == RoleType.ADMIN ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
}
