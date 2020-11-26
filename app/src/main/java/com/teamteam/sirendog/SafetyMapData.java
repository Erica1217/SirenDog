package com.teamteam.sirendog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SafetyMapData {
    @SerializedName("rn")
    @Expose
    private Integer rn;
    @SerializedName("lcSn")
    @Expose
    private Integer lcSn;
    @SerializedName("bsshNm")
    @Expose
    private String bsshNm;
    @SerializedName("telno")
    @Expose
    private String telno;
    @SerializedName("adres")
    @Expose
    private String adres;
    @SerializedName("etcAdres")
    @Expose
    private Object etcAdres;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("lcinfoLa")
    @Expose
    private Double lcinfoLa;
    @SerializedName("lcinfoLo")
    @Expose
    private Double lcinfoLo;
    @SerializedName("cl")
    @Expose
    private String cl;
    @SerializedName("clNm")
    @Expose
    private String clNm;
    @SerializedName("scopeCd")
    @Expose
    private Object scopeCd;
    @SerializedName("scope")
    @Expose
    private Object scope;
    @SerializedName("hmpg")
    @Expose
    private Object hmpg;

    public Integer getRn() {
        return rn;
    }

    public void setRn(Integer rn) {
        this.rn = rn;
    }

    public Integer getLcSn() {
        return lcSn;
    }

    public void setLcSn(Integer lcSn) {
        this.lcSn = lcSn;
    }

    public String getBsshNm() {
        return bsshNm;
    }

    public void setBsshNm(String bsshNm) {
        this.bsshNm = bsshNm;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Object getEtcAdres() {
        return etcAdres;
    }

    public void setEtcAdres(Object etcAdres) {
        this.etcAdres = etcAdres;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Double getLcinfoLa() {
        return lcinfoLa;
    }

    public void setLcinfoLa(Double lcinfoLa) {
        this.lcinfoLa = lcinfoLa;
    }

    public Double getLcinfoLo() {
        return lcinfoLo;
    }

    public void setLcinfoLo(Double lcinfoLo) {
        this.lcinfoLo = lcinfoLo;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getClNm() {
        return clNm;
    }

    public void setClNm(String clNm) {
        this.clNm = clNm;
    }

    public Object getScopeCd() {
        return scopeCd;
    }

    public void setScopeCd(Object scopeCd) {
        this.scopeCd = scopeCd;
    }

    public Object getScope() {
        return scope;
    }

    public void setScope(Object scope) {
        this.scope = scope;
    }

    public Object getHmpg() {
        return hmpg;
    }

    public void setHmpg(Object hmpg) {
        this.hmpg = hmpg;
    }
}
