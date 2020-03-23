package edu.eci.cvds.test;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
    }

    @Test
    public void emptyDB() {
        for(int i = 0; i < 100; i += 10) {
            boolean r = false;
            try {
                Cliente cliente = serviciosAlquiler.consultarCliente(documento);
            } catch(ExcepcionServiciosAlquiler e) {
                r = true;
            } catch(IndexOutOfBoundsException e) {
                r = true;
            }
            // Validate no Client was found;
            Assert.assertTrue(r);
        };
    }

    /** Cliente ya está registrado **/
    @Test
    public void ClienteYaRegistrado(){
        Cliente c1=new Cliente("Oscar Alba", 1026585664, "6788952", "KRA 109#34-C30", "oscar@hotmail.com", false,null);
        boolean r = false;
        try{
            serviciosAlquiler.registrarCliente(c1);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    /** Cliente a vetar no existe **/
    @Test
    public void vetarCliente(){
        boolean r = false;
        try{
            serviciosAlquiler.vetarCliente(0, true);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);

    }
    /** Item no registrado **/
    @Test
    public void consultarItem(){
        boolean r = false;
        try{
            serviciosAlquiler.consultarItem(0);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    /** Item ya registrado **/
    @Test
    public void registrarItem(){
        Item i1=new Item(null, 4, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
        boolean r = false;
        try{
            serviciosAlquiler.registrarItem(i1);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    /**
     * Item no existe
     */
    @Test
    public void actualizarTarifaItem(){
        boolean r = false;
        try{
            serviciosAlquiler.actualizarTarifaItem(0, 0);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    /** Tipo item no existe **/
    @Test
    public void consultarTipoItem(){
        boolean r = false;
        try{
            serviciosAlquiler.consultarTipoItem(0);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    /** Cliente no registrado **/
    @Test
    public void registrarAlquilerCliente(){
        boolean r = false;
        Item i1=new Item(null, 4, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");

        try{
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf("2007-09-08"),0,i1,5);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    /** Cliente no registrado **/
    @Test
    public void consultarItemsCliente(){
        boolean r = false;
        try{
            serviciosAlquiler.consultarItemsCliente(0);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    @Test
    /** Item no rentado **/
    public void consultarMultaAlquiler(){
        boolean r = false;
        try{
            serviciosAlquiler.consultarMultaAlquiler(4,java.sql.Date.valueOf("2005-06-08"));
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }
    /** item no disponible **/
    @Test
    public void consultarCostoAlquiler(){
        boolean r = false;
        try{
            serviciosAlquiler.consultarCostoAlquiler(1,5);
        }catch(ExcepcionServiciosAlquiler e){
            r = true;
        }
        Assert.assertTrue(r);
    }

}