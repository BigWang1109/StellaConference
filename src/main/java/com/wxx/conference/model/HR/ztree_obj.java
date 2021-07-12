package com.wxx.conference.model.HR;

/**
 * Created by thinkpad on 2021-6-7.
 */
public class ztree_obj {

    private String id;

    private String pId;

    private String name;

    private boolean checked;

    private ztree_obj[] children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public ztree_obj[] getChildren() {
        return children;
    }

    public void setChildren(ztree_obj[] children) {
        this.children = children;
    }
}
