//~--- non-JDK imports --------------------------------------------------------

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

//~--- JDK imports ------------------------------------------------------------

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */

/**
 *
 * @author osita
 */
import javax.persistence.spi.PersistenceUnitTransactionType;

import javax.sql.DataSource;

public class JtaPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {
    private boolean                        jtaMode     = false;
    private PersistenceUnitTransactionType transacType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    private DataSource                     jtaDataSource;

    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {
        if (jtaMode) {
            transacType = PersistenceUnitTransactionType.JTA;
            mutablePersistenceUnitInfo.setJtaDataSource(this.getJtaDataSource());
        }

        mutablePersistenceUnitInfo.setTransactionType(transacType);
    }

    public boolean isJtaMode() {
        return jtaMode;
    }

    public void setJtaMode(boolean jtaMode) {
        this.jtaMode = jtaMode;
    }

    public DataSource getJtaDataSource() {
        return jtaDataSource;
    }

    public void setJtaDataSource(DataSource jtaDataSource) {
        this.jtaDataSource = jtaDataSource;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
