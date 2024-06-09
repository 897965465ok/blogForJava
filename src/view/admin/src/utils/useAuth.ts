
/*["blog:list",
    "admin:list",
    "article:list",
    "user:list",
    "role:list",
    "tag:list",
    "article:query",
    "article:edit",
    "article:plus",
    "article:delete",
    "menu:query",
    "menu:plus",
    "menu:delete",
    "menu:edit",
    "user:query",
    "user:plus",
    "user:edit",
    "user:delete",
    "role:plus",
    "role:delete",
    "role:query",
    "role:edit",
    "tags:plus",
    "tags:delete",
    "tags:query",
    "tags:edit"]*/



export function useAuth(perms: string): boolean {
    const { userInfo } = JSON.parse(localStorage.getItem("index") || "{}")
    const permissions: string[] = userInfo.permissions;
    const roles: string[] = userInfo.roles;
    if (roles.includes("admin")) {
        return true
    } else {
        return permissions.includes(perms);
    }

}