package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.ItemRentado;


public interface ItemRentadoDAO {
    public void save(ItemRentado itr) throws PersistenceException;

    public ItemRentado load(int id) throws PersistenceException;

}
