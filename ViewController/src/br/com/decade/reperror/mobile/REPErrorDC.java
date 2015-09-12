package br.com.decade.reperror.mobile;

import br.com.decade.reperror.database.ConnectionFactory;
import br.com.decade.reperror.server.REPErrorBO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;
import oracle.adfmf.util.Utility;

public class REPErrorDC {
    private REPErrorBO[] REPErrorVO;

    private ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);

    public REPErrorDC() {
        super();

        REPErrorVO = getREPErrorDB(new String(""));
    }

    public void setREPErrorVO(REPErrorBO[] REPErrorVO) {
        this.REPErrorVO = REPErrorVO;
        providerChangeSupport.fireProviderRefresh("REPErrorVO");
    }

    public REPErrorBO[] getREPErrorVO() {
        return REPErrorVO;
    }

    private REPErrorBO[] getREPErrorDB(String findString) {
        List listError = new ArrayList();

        String doDML;

        if (findString.equals("")) {
            doDML = "SELECT code, erro, cause, action, nivel, tipo, impact, favorite FROM rep_error WHERE favorite = 'Y' ORDER BY code;";
        } else {
            doDML = "SELECT code, erro, cause, action, nivel, tipo, impact, favorite FROM rep_error WHERE code LIKE '%" + findString + "%' OR erro LIKE '%" + findString + "%' ORDER BY code;";
        }

        try {
            Connection conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            Statement stREPError = conn.createStatement();
            ResultSet rsREPError = stREPError.executeQuery(doDML);

            while (rsREPError.next()) {
                REPErrorBO repError = new REPErrorBO();

                repError.setCode(rsREPError.getString("code"));
                repError.setErro(rsREPError.getString("erro"));
                repError.setCause(rsREPError.getString("cause"));
                repError.setAction(rsREPError.getString("action"));
                repError.setNivel(rsREPError.getString("nivel"));
                repError.setTipo(rsREPError.getString("tipo"));
                repError.setImpact(rsREPError.getString("impact"));
                repError.setFavorite(rsREPError.getString("favorite"));

                listError.add(repError);
            }
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());

            AdfmfContainerUtilities.invokeContainerJavaScriptFunction("main"
                                                                     ,"navigator.notification.alert"
                                                                     ,new Object[] { "DB: IS DOWN OR DEAD", "null", "Warning", "OK" });
        }

        return (REPErrorBO[]) listError.toArray(new REPErrorBO[listError.size()]);
    }

    public void findError (String repError) {
        setREPErrorVO(getREPErrorDB(repError));
    }

    public void makeFavorite (String code, String favorite) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement psDML = conn.prepareStatement("UPDATE rep_error SET favorite = ? WHERE code = ?;");

            psDML.setString(1, favorite);
            psDML.setString(2, code);

            psDML.execute();

            conn.commit();
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());

            AdfmfContainerUtilities.invokeContainerJavaScriptFunction("main"
                                                                     ,"navigator.notification.alert"
                                                                     ,new Object[] { "DB: IS DOWN OR DEAD", "null", "Warning", "OK" });
        }
    }

    public void addProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.addProviderChangeListener(l);
    }

    public void removeProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.removeProviderChangeListener(l);
    }
}
