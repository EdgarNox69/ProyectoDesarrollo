
package erd.parser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class RelationData {
 public static final char DK_CHAR ='-';
 public static final char PK_CHAR ='+';
 public static final char FK_CHAR ='*';
 public static final char SINGLE_CHAR ='@';
 
 public static final char SINGLE ='@';
 public static final char DK ='-';
 public static final char PK ='+';
 public static final char FK ='*';
 
 private String name;
 private String primaryKey;
 private HashSet keys;
 
 private ArrayList<String> atributos;
 
 public RelationData(){
     name = "";
     primaryKey = null;
     keys = new HashSet<String>();
     atributos = new ArrayList<String>();
 }
 public RelationData(String n){
     name=n;
     primaryKey = null;
     keys = new HashSet<String>();
     atributos = new ArrayList<String>();
 }
 public boolean setPK(String pk){
     return keys.add(pk);
 }
 public boolean isPK(String a){
     return keys.contains(a);
 }
 public boolean add(String a){
     boolean result = false;
     if(a != null){
         if(!atributos.contains(a)){
             result = atributos.add(a);
         }
     }
     return result;
 }
 public boolean remove(String a){
     return atributos.remove(a);
 }
 public static String attribName(String a){
     return a.substring(1);
 }
 public static int attribType(String a){
     return a.charAt(0);
 }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder(name);
       sb.append("(");
       Iterator it = atributos.iterator();
       int n = atributos.size();
       
       for(int i=0; i<n;i++){
           String a = (String) it.next();
           sb.append(a);
           if(isPK(a)){
               sb.append("*");
           }
           if(i < n -1){
               sb.append(",");
           }
       }
       sb.append(")");
       return sb.toString();
    }
}
