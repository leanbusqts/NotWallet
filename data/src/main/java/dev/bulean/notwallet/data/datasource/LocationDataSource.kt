package dev.bulean.notwallet.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
