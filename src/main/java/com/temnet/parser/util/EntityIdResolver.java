package com.temnet.parser.util;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Custom resolver used for resolving actual Java object from Object Identifiers (as annotated using {@link com.fasterxml.jackson.annotation.JsonIdentityInfo}).
 */
public class EntityIdResolver implements ObjectIdResolver {
    private final EntityManager entityManager;

    @Autowired
    public EntityIdResolver(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method called when a POJO is deserialized and has an Object Identifier
     *
     * @param idKey key class that can be used as a key for ObjectId-to-POJO mappings, when multiple ObjectId types and scopes are used
     * @param o     the POJO that is being deserialized
     */
    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object o) {
    }

    /**
     * Method called when deserialization encounters the given Object Identifier and requires the POJO associated with it
     *
     * @param idKey key class that can be used as a key for ObjectId-to-POJO mappings, when multiple ObjectId types and scopes are used
     * @return the POJO, or null if unable to resolve
     */
    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        return entityManager.find(idKey.scope, Long.parseLong(idKey.key.toString()));
    }

    /**
     * Factory method called to create a new instance to use for deserialization: needed since resolvers may have state (a pool of objects)
     *
     * @param o the POJO that is being deserialized
     * @return null
     */
    @Override
    public ObjectIdResolver newForDeserialization(Object o) {
        return null;
    }

    /**
     * Method called to check whether this resolver instance can be used for Object Ids of specific resolver type;
     * determination is based by passing a configured "blueprint" (prototype) instance;
     * from which the actual instances are created
     *
     * @param objectIdResolver resolver
     * @return True if this instance can be used as-is; false if not
     */
    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        return false;
    }
}
