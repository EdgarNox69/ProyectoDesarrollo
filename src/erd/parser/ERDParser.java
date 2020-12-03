/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erd.parser;

import java.awt.TextField;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import javax.swing.JCheckBox;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author rnavarro
 */
public class ERDParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, MalformedURLException {

        FileReader fp = new FileReader("university-erd.json");
 
        JSONTokener tokenizer = new JSONTokener(fp);

        JSONObject JSONDoc = new JSONObject(tokenizer);
        JSONArray names = JSONDoc.names();
        System.out.println(names);

       
        JSONArray entidades = JSONDoc.getJSONArray("entidades");
        System.out.println(entidades);

        Iterator it = entidades.iterator();

     
        while (it.hasNext()) {

            JSONObject entidad = (JSONObject) it.next();

       
            String entityName = entidad.getString("nombre");
            System.out.println(entityName);

           
            JSONArray atributos = entidad.getJSONArray("atributos");
            Iterator attribIt = atributos.iterator();
            
            int i=0;
            String temp="";
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                System.out.print("\t");
                System.out.print(atributo.getString("nombre"));
                
                if (atributo.getInt("tipo") == 1) {
                    System.out.println(" *");
                    
                } else {
                    System.out.println();
                }
                i++;
            }
            attribIt=atributos.iterator();
            Object[][] tabla=new Object[i][6];
           
            i=0;
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
              
                tabla[i][0]=atributo.getString("nombre").toString();
               
                if (atributo.getInt("tipo") == 1) {
                    //System.out.println(" *");
                    temp=tabla[i][0].toString();
                    temp=temp+"*";
                    tabla[i][0]=temp;
                } else {
                  
                }
                
                i++;
            } 
            CreateTable tab=new CreateTable(tabla);
            
            tab.main(tabla,entityName);
        }
        
       
        JSONArray debiles = JSONDoc.getJSONArray("debiles");
        System.out.println(debiles);

        it = debiles.iterator();

     
        while (it.hasNext()) {

            JSONObject entidad = (JSONObject) it.next();

           
            String entityName = entidad.getString("nombre");
            System.out.println(entityName);

         
            JSONArray atributos = entidad.getJSONArray("atributos");
            Iterator attribIt = atributos.iterator();
            int i=0;
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                System.out.print("\t");
                System.out.print(atributo.getString("nombre"));

                if (atributo.getInt("tipo") == 1) {
                    System.out.println(" *");
                } else {
                    System.out.println();
                }
                i++;
            }
         
            String temp="";
            attribIt=atributos.iterator();
            Object[][] table=new Object[i][6];
           
            i=0;
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
           
                table[i][0]=atributo.getString("nombre");
                if (atributo.getInt("tipo") == 1) {
                    temp=table[i][0].toString();
                    temp=temp+"*";
                    table[i][0]=temp;
                    
                } else {
                  
                }
                
                i++;
            }
            CreateTable tab=new CreateTable(table);
            
            tab.main(table,entityName);
        }
        
        
        System.out.println("");
        
       
        JSONArray relations = JSONDoc.getJSONArray("relaciones");

        System.out.println("**** RELACIONES ****");

        it = relations.iterator();

        while (it.hasNext()) {
            JSONObject rel = (JSONObject) it.next();

         
            System.out.println( rel.getString("nombre") );

            JSONArray cards = rel.getJSONArray("cardinalidades");

            int n = cards.length();

            for (int i = 0; i < n; i++) {
                JSONObject e1 = cards.getJSONObject(i);

                System.out.printf("\t%s (%s,%s)\n", e1.getString("entidad"),
                        e1.getString("min"),
                        e1.getString("max"));

            }

        }
        System.out.println("----------------------------------------");
        ERRelacional();
        
        
    }
    static void ERRelacional() throws MalformedURLException, IOException{
        FileReader fp = new FileReader("university-erd.json");
        
        JsonElement element;
        element = JsonParser.parseReader(fp);
        if(element.isJsonObject()){
            JsonObject erd = element.getAsJsonObject();
            System.out.println("**** Entidades en el diagrama ****\n");
            JsonArray entidades = erd.getAsJsonArray("entidades");
            for(int i =0; i<entidades.size(); i++){
                JsonObject entidad = entidades.get(i).getAsJsonObject();
                //System.out.println(entidad.get("nombre").getAsString());
                
                RelationData Tablas = new RelationData (entidad.get("nombre").getAsString());
                //System.out.print("(");
                JsonArray atributos = entidad.getAsJsonArray("atributos");
                int attCount = atributos.size();
                for (int j = 0; j < attCount; j++) {
                JsonObject atributo = atributos.get(j).getAsJsonObject();
                String an = atributo.get("nombre").getAsString();
               // System.out.println(an);
                
                int type = atributo.get("tipo").getAsInt();
                Tablas.add(an);
                
                if(type == 1){
                   // System.out.println("*");
                    Tablas.setPK(an);
                }
                if( j < attCount - 1){
                    //System.out.println(",");
                }
                }
                //System.out.println(")");
                System.out.println(Tablas);
            }
            JsonArray links = erd.getAsJsonArray("relaciones");
            for(int i=0; i< links.size();i++){
                
            }
        }
     
    }
    
}
