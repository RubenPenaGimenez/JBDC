
package rubenpengim;

import rubenpengim.dao.ProductoDAO;
import rubenpengim.dao.ProductoDAOImpl;
import rubenpengim.model.Producto;

public class ProductoManager {
    public static void main(String[] args) {
        ProductoDAO product =new ProductoDAOImpl();
        
        //insert
        product.insert(new Producto(666,"Silla Gaming",250.0));
        
        //delete
        product.delete(200);
        
        //update
        
        //read
        System.out.println("   ");
        Producto p = product.read(666);
        System.out.println(p);
    }
}
