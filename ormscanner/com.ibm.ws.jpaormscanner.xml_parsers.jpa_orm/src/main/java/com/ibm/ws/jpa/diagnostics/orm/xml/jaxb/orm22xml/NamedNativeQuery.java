//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.07 at 01:36:00 PM CDT 
//


package com.ibm.ws.jpa.diagnostics.orm.xml.jaxb.orm22xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 *         @Target({TYPE}) @Retention(RUNTIME)
 *         public @interface NamedNativeQuery {
 *           String name();
 *           String query();
 *           QueryHint[] hints() default {};
 *           Class resultClass() default void.class;
 *           String resultSetMapping() default ""; //named SqlResultSetMapping
 *         }
 * 
 *       
 * 
 * <p>Java class for named-native-query complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="named-native-query">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="query" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hint" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}query-hint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="result-class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="result-set-mapping" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "named-native-query", propOrder = {
    "description",
    "query",
    "hint"
})
public class NamedNativeQuery {

    protected String description;
    @XmlElement(required = true)
    protected String query;
    protected List<QueryHint> hint;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "result-class")
    protected String resultClass;
    @XmlAttribute(name = "result-set-mapping")
    protected String resultSetMapping;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuery(String value) {
        this.query = value;
    }

    /**
     * Gets the value of the hint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryHint }
     * 
     * 
     */
    public List<QueryHint> getHint() {
        if (hint == null) {
            hint = new ArrayList<QueryHint>();
        }
        return this.hint;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the resultClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultClass() {
        return resultClass;
    }

    /**
     * Sets the value of the resultClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultClass(String value) {
        this.resultClass = value;
    }

    /**
     * Gets the value of the resultSetMapping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultSetMapping() {
        return resultSetMapping;
    }

    /**
     * Sets the value of the resultSetMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultSetMapping(String value) {
        this.resultSetMapping = value;
    }

}
