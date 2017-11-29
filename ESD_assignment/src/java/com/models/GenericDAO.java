/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import java.util.List;

/**
 *
 * @author Prithpal Sooriya
 *
 * This GenericDAO will be responsible for
 * CRUD:
 *  - Create (necessary)
 *  - Retrieve (necessary)
 *  - Update (will be more specific for each DAO)
 *  - Delete (we will not really be using delete)
 *
 * We use 2 generics:
 * - E (for the type of bean that this DAO will use)
 * - T (for the what the ID this bean, so we can relay it to the database)
 *   - For example, User and Member tables have String as their ID's
 *     and Claim and Payment tables have Integer's as their ID's
 */
public interface GenericDAO<E, T> {
    public boolean create(E e); //will be responsible for adding Object into Database table
    public List<E> getAll(); //will return all objects from related table
    public E getByID(T id); //will return 1 object from the objects unique ID from the table
}
