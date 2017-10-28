/**
 * ProdutoTask.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package movimentacao.webservice;

public class ProdutoTask  implements java.io.Serializable {
    private java.lang.String[] diasDaSemana;

    private java.lang.String emails;

    private java.lang.Integer hora;

    private java.lang.Integer id;

    private java.lang.String nomeRelatorio;

    private boolean status;

    public ProdutoTask() {
    }

    public ProdutoTask(
           java.lang.String[] diasDaSemana,
           java.lang.String emails,
           java.lang.Integer hora,
           java.lang.Integer id,
           java.lang.String nomeRelatorio,
           boolean status) {
           this.diasDaSemana = diasDaSemana;
           this.emails = emails;
           this.hora = hora;
           this.id = id;
           this.nomeRelatorio = nomeRelatorio;
           this.status = status;
    }


    /**
     * Gets the diasDaSemana value for this ProdutoTask.
     * 
     * @return diasDaSemana
     */
    public java.lang.String[] getDiasDaSemana() {
        return diasDaSemana;
    }


    /**
     * Sets the diasDaSemana value for this ProdutoTask.
     * 
     * @param diasDaSemana
     */
    public void setDiasDaSemana(java.lang.String[] diasDaSemana) {
        this.diasDaSemana = diasDaSemana;
    }

    public java.lang.String getDiasDaSemana(int i) {
        return this.diasDaSemana[i];
    }

    public void setDiasDaSemana(int i, java.lang.String _value) {
        this.diasDaSemana[i] = _value;
    }


    /**
     * Gets the emails value for this ProdutoTask.
     * 
     * @return emails
     */
    public java.lang.String getEmails() {
        return emails;
    }


    /**
     * Sets the emails value for this ProdutoTask.
     * 
     * @param emails
     */
    public void setEmails(java.lang.String emails) {
        this.emails = emails;
    }


    /**
     * Gets the hora value for this ProdutoTask.
     * 
     * @return hora
     */
    public java.lang.Integer getHora() {
        return hora;
    }


    /**
     * Sets the hora value for this ProdutoTask.
     * 
     * @param hora
     */
    public void setHora(java.lang.Integer hora) {
        this.hora = hora;
    }


    /**
     * Gets the id value for this ProdutoTask.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this ProdutoTask.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the nomeRelatorio value for this ProdutoTask.
     * 
     * @return nomeRelatorio
     */
    public java.lang.String getNomeRelatorio() {
        return nomeRelatorio;
    }


    /**
     * Sets the nomeRelatorio value for this ProdutoTask.
     * 
     * @param nomeRelatorio
     */
    public void setNomeRelatorio(java.lang.String nomeRelatorio) {
        this.nomeRelatorio = nomeRelatorio;
    }


    /**
     * Gets the status value for this ProdutoTask.
     * 
     * @return status
     */
    public boolean isStatus() {
        return status;
    }


    /**
     * Sets the status value for this ProdutoTask.
     * 
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdutoTask)) return false;
        ProdutoTask other = (ProdutoTask) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.diasDaSemana==null && other.getDiasDaSemana()==null) || 
             (this.diasDaSemana!=null &&
              java.util.Arrays.equals(this.diasDaSemana, other.getDiasDaSemana()))) &&
            ((this.emails==null && other.getEmails()==null) || 
             (this.emails!=null &&
              this.emails.equals(other.getEmails()))) &&
            ((this.hora==null && other.getHora()==null) || 
             (this.hora!=null &&
              this.hora.equals(other.getHora()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.nomeRelatorio==null && other.getNomeRelatorio()==null) || 
             (this.nomeRelatorio!=null &&
              this.nomeRelatorio.equals(other.getNomeRelatorio()))) &&
            this.status == other.isStatus();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDiasDaSemana() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDiasDaSemana());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDiasDaSemana(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEmails() != null) {
            _hashCode += getEmails().hashCode();
        }
        if (getHora() != null) {
            _hashCode += getHora().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getNomeRelatorio() != null) {
            _hashCode += getNomeRelatorio().hashCode();
        }
        _hashCode += (isStatus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdutoTask.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservice.movimentacao/", "produtoTask"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diasDaSemana");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diasDaSemana"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hora");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeRelatorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeRelatorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
