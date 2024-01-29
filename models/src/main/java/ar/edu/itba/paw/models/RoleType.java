package ar.edu.itba.paw.models;

public enum RoleType {
    PROVIDER("PROVIDER"),
    TRUCKER("TRUCKER");

    private final String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
