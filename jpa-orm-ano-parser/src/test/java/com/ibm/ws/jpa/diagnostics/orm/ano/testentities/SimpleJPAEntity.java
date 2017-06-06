/*******************************************************************************
 * Copyright (c) 2011, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.jpa.diagnostics.orm.ano.testentities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class SimpleJPAEntity {
    @Id
    private int id;
    
    @Version
    private int version;
    
    @Basic
    private String persistentString;
    
    @Basic
    private int persistentInteger;
    
    @Basic
    private Integer persistentIntegerWrapper;
    
    public SimpleJPAEntity() {
        
    }

    public SimpleJPAEntity(int id, String persistentString, int persistentInteger,
            Integer persistentIntegerWrapper) {
        this.id = id;
        this.persistentString = persistentString;
        this.persistentInteger = persistentInteger;
        this.persistentIntegerWrapper = persistentIntegerWrapper;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPersistentString() {
        return persistentString;
    }

    public void setPersistentString(String persistentString) {
        this.persistentString = persistentString;
    }

    public int getPersistentInteger() {
        return persistentInteger;
    }

    public void setPersistentInteger(int persistentInteger) {
        this.persistentInteger = persistentInteger;
    }

    public Integer getPersistentIntegerWrapper() {
        return persistentIntegerWrapper;
    }

    public void setPersistentIntegerWrapper(Integer persistentIntegerWrapper) {
        this.persistentIntegerWrapper = persistentIntegerWrapper;
    }

    public void doPanic() {
        String help = new SimpleInterfaceForAnonymousClassTesting() {
            @Override
            public String doSomething() {
                return "Do Something!";
            }
            
        }.doSomething();
        System.out.println(help);
    }
    
    @Override
    public String toString() {
        return "SimpleJPAEntity [id=" + id + ", version=" + version + ", persistentString=" + persistentString
                + ", persistentInteger=" + persistentInteger + ", persistentIntegerWrapper=" + persistentIntegerWrapper
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + persistentInteger;
        result = prime * result + ((persistentIntegerWrapper == null) ? 0 : persistentIntegerWrapper.hashCode());
        result = prime * result + ((persistentString == null) ? 0 : persistentString.hashCode());
        result = prime * result + version;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SimpleJPAEntity other = (SimpleJPAEntity) obj;
        if (id != other.id)
            return false;
        if (persistentInteger != other.persistentInteger)
            return false;
        if (persistentIntegerWrapper == null) {
            if (other.persistentIntegerWrapper != null)
                return false;
        } else if (!persistentIntegerWrapper.equals(other.persistentIntegerWrapper))
            return false;
        if (persistentString == null) {
            if (other.persistentString != null)
                return false;
        } else if (!persistentString.equals(other.persistentString))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    private class DumbInnerClass {
        private int innerInt;
        private String innerString;
        
        private DumbInnerClass() {
            
        }

        private DumbInnerClass(int innerInt, String innerString) {
            super();
            this.innerInt = innerInt;
            this.innerString = innerString;
        }

        private int getInnerInt() {
            return innerInt;
        }

        private void setInnerInt(int innerInt) {
            this.innerInt = innerInt;
        }

        private String getInnerString() {
            return innerString;
        }

        private void setInnerString(String innerString) {
            this.innerString = innerString;
        }
        
        
    }
}
