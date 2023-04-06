package dev.bulean.notwallet.data

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}
