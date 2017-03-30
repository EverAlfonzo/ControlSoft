/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Ever
 */
public class Usuario {
    private String NomUsuario;
    private String AliasUsuario;
    private String NivelUsuario;
    
    public Usuario(){
        this.NomUsuario="";
        this.AliasUsuario="";
        this.NivelUsuario="";
    }

    public String getNomUsuario() {
        return NomUsuario;
    }

    public void setNomUsuario(String NomUsuario) {
        this.NomUsuario = NomUsuario;
    }

    public String getAliasUsuario() {
        return AliasUsuario;
    }

    public void setAliasUsuario(String AliasUsuario) {
        this.AliasUsuario = AliasUsuario;
    }

    public String getNivelUsuario() {
        return NivelUsuario;
    }

    public void setNivelUsuario(String NivelUsuario) {
        this.NivelUsuario = NivelUsuario;
    }
    
    
    
}
