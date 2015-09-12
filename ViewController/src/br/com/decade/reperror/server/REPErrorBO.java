package br.com.decade.reperror.server;


public class REPErrorBO {
    private String code;
    private String erro;
    private String cause;
    private String action;
    private String nivel;
    private String tipo;
    private String impact;
    private String favorite;

    public REPErrorBO() {
        super();
    }
/*
    public REPErrorBO(String code, String erro, String cause, String action, String nivel, String tipo, String impact, String favorite) {
        super();

        this.code = code;
        this.erro = erro;
        this.cause = cause;
        this.action = action;
        this.nivel = nivel;
        this.tipo = tipo;
        this.impact = impact;
        this.favorite = favorite;
    }
*/
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getImpact() {
        return impact;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getFavorite() {
        return favorite;
    }
}
