package at.vanishedwanderer

import io.quarkus.hibernate.orm.panache.Panache

interface AdvancedPanacheRepositoryBase<Entity, Key> {
    fun refresh(entity: Entity) {
        Panache.getEntityManager().refresh(entity)
    }

    fun merge(entity: Entity) {
        Panache.getEntityManager().merge(entity)
    }

    fun mergeAndFlush(entity: Entity) {
        merge(entity)
        Panache.getEntityManager().flush()
    }
}