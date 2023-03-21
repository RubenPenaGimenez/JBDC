package rubenpengim.dao;

import rubenpengim.model.Producto;

public interface ProductoDAO {

    public void insert(Producto product);

    public void update(Producto product);

    public void delete(Integer id);

    public Producto read(Integer id);
}
