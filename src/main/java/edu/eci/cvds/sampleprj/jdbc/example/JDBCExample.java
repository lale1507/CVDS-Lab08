/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCExample {
    
    public static void main(String args[]){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2019";
                        
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);
                 
            
            System.out.println("Valor total pedido 1:"+valorTotalPedido(con, 1));
            
            List<String> prodsPedido=nombresProductosPedido(con, 1);
            
            
            System.out.println("Productos del pedido 1:");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");
            
            
            int suCodigoECI=2113791;
            registrarNuevoProducto(con, suCodigoECI, "Laura Bernal", 999999999);
            con.commit();
                        
            
            con.close();
                                   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Agregar un nuevo producto con los parámetros dados
     * @param con la conexión JDBC
     * @param codigo
     * @param nombre
     * @param precio
     * @throws SQLException 
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{

        //cosulta
        String insert = "INSERT INTO ORD_PRODUCTOS(codigo, nombre, precio) VALUES(?,?,?)";

        //Crear preparedStatement
        PreparedStatement nuevoProducto = null;
        nuevoProducto =  con.prepareStatement(insert);


        //Asignar parámetros
        nuevoProducto.setInt(1,codigo);
        nuevoProducto.setString(2,nombre);
        nuevoProducto.setInt(3,precio);

        //usar 'execute'
        //try{
            nuevoProducto.execute();
            con.commit();
        //}catch(SQLException exception){
          //  System.out.println("No inserto.");
        //}
        
    }
    
    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexión JDBC
     * @param codigoPedido el código del pedido
     * @return 
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido) throws SQLException {
        List<String> np=new LinkedList<>();

        //consulta
        String consulta="SELECT nombre FROM ORD_DETALLE_PEDIDO,ORD_PRODUCTOS WHERE ORD_DETALLE_PEDIDO.pedido_fk=? AND ORD_DETALLE_PEDIDO.producto_fk = ORD_PRODUCTOS.codigo ";
        
        //Crear prepared statement
        PreparedStatement pruebaProducto = null;
        pruebaProducto = con.prepareStatement(consulta);

        //asignar parámetros
        pruebaProducto.setInt(1,codigoPedido);

        //usar executeQuery
        ResultSet new_table = pruebaProducto.executeQuery();

        //Sacar resultados del ResultSet y llenar lista
        while (new_table.next()){
            String text = new_table.getString("nombre");
            np.add(text);
        }

        //Retornarla
        return np;
    }

    
    /**
     * Calcular el costo total de un pedido
     * @param con
     * @param codigoPedido código del pedido cuyo total se calculará
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static int valorTotalPedido(Connection con, int codigoPedido) throws SQLException {
        //consulta
        String consulta = "SELECT SUM(precio*cantidad) AS total FROM ORD_DETALLE_PEDIDO,ORD_PRODUCTOS WHERE ORD_DETALLE_PEDIDO.pedido_fk=? AND ORD_DETALLE_PEDIDO.producto_fk = ORD_PRODUCTOS.codigo";
        
        //Crear prepared statement
        PreparedStatement costoTotalPedido = null;
        costoTotalPedido = con.prepareStatement(consulta);

        //asignar parámetros
        costoTotalPedido.setInt(1,codigoPedido);

        //usar executeQuery
        ResultSet new_data = costoTotalPedido.executeQuery();

        //Sacar resultado del ResultSet

        new_data.next();
        return new_data.getInt("total");
    }
    

    
    
    
}