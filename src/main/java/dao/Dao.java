/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 * This is DAO interface for all other Dao classes to use.
 *
 * @author Jonkke
 */
public interface Dao<T> {

    public T get(int id);

    public List<T> getAll();

    public void save(T t);

    public void delete(T t);
}
