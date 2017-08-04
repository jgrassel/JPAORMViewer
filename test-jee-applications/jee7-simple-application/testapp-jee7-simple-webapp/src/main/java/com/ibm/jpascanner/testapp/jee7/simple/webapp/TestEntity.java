package com.ibm.jpascanner.testapp.jee7.simple.webapp;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class TestEntity {
    @Id
    private long id;
    
    @Basic
    private int intData;
    
    @Basic
    private String strData;
    
    @Version
    private long version;
    
    public TestEntity() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIntData() {
        return intData;
    }

    public void setIntData(int intData) {
        this.intData = intData;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TestEntity [id=" + id + ", intData=" + intData + ", strData=" + strData + ", version=" + version + "]";
    }
}
