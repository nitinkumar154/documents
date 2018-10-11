package com.innoviti.emi.entity.core;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.constant.CashbackType;
import com.innoviti.emi.entity.AuditColumns;
import com.innoviti.emi.entity.SchemeCompositeBridge;

@Indexed(index="auto")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "issuer_schemes")
public class Scheme extends AuditColumns {

  private static final long serialVersionUID = -804316030698123078L;

  @FieldBridge(impl = SchemeCompositeBridge.class)
  @EmbeddedId
  private SchemeComposite schemeComposite;

  @Column(name = "bajaj_issuer_scheme_code", length = 10)
  private String bajajIssuerSchemeCode;

  @Field
  @Column(name = "issuer_scheme_display_name", length = 50, nullable = false)
  private String schemeDisplayName;

  @Column(name = "issuer_scheme_description", length = 45)
  private String issuerSchemeDescription;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({@JoinColumn(name = "issuer_bank_code", referencedColumnName = "issuer_bank_code"),
      @JoinColumn(referencedColumnName = "record_update_timestamp")})
  private IssuerBank issuerBank;

  @Transient
  private String innoIssuerBankCode;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({@JoinColumn(name = "emi_tenure_code", referencedColumnName = "emi_tenure_code"),
      @JoinColumn(referencedColumnName = "record_update_timestamp")})
  private Tenure tenure;

  @Transient
  private String innoTenureCode;

  @Column(name = "issuer_scheme_processing_fees", length = 10)
  private String processingFees;

  @Column(name = "advance_emi", length = 6)
  private String advanceEmi;

  @Column(name = "brand_subvention", length = 10)
  private String brandSubvention;

  @Column(name = "merchant_subvention", length = 10)
  private String merchantSubvention;

  @Column(name = "bank_subvention", length = 10)
  private String bankSubvention;

  @Column(name = "innoviti_subvention", length = 10)
  private String innovitiSubvention;

  @Column(name = "issuer_rate_of_interest", length = 10)
  private String roi;

  @Column(name = "scheme_start_date")
  private Date schemeStartDate;

  @Column(name = "scheme_end_date")
  private Date schemeEndDate;

  @Column(name = "max_amount", precision = 18, scale = 2)
  private BigDecimal maxAmount;

  @Column(name = "min_amount", precision = 18, scale = 2)
  private BigDecimal minAmount;

  @Column(name = "general_scheme", length = 3)
  private String genScheme;

  @Enumerated(EnumType.STRING)
  @Column(name = "issuer_cashback_flag", length = 5, nullable = false)
  private CashbackType cashbackFlag;

  @Transient
  private String innoIssuerSchemeCode;

  @Transient
  private Date crtupdDate;

  public SchemeComposite getSchemeComposite() {
    return schemeComposite;
  }

  public void setSchemeComposite(SchemeComposite schemeComposite) {
    this.schemeComposite = schemeComposite;
  }

  public String getBajajIssuerSchemeCode() {
    return bajajIssuerSchemeCode;
  }

  public void setBajajIssuerSchemeCode(String bajajIssuerSchemeCode) {
    this.bajajIssuerSchemeCode = bajajIssuerSchemeCode;
  }

  public String getSchemeDisplayName() {
    return schemeDisplayName;
  }

  public IssuerBank getIssuerBank() {
    return issuerBank;
  }

  public void setIssuerBank(IssuerBank issuerBank) {
    this.issuerBank = issuerBank;
  }

  public Tenure getTenure() {
    return tenure;
  }

  public void setTenure(Tenure tenure) {
    this.tenure = tenure;
  }

  public void setSchemeDisplayName(String schemeDisplayName) {
    this.schemeDisplayName = schemeDisplayName;
  }

  public String getInnoIssuerBankCode() {
    return innoIssuerBankCode;
  }

  public void setInnoIssuerBankCode(String innoIssuerBankCode) {
    this.innoIssuerBankCode = innoIssuerBankCode;
  }

  public String getInnoTenureCode() {
    return innoTenureCode;
  }

  public void setInnoTenureCode(String innoTenureCode) {
    this.innoTenureCode = innoTenureCode;
  }

  public String getProcessingFees() {
    return processingFees;
  }

  public void setProcessingFees(String processingFees) {
    this.processingFees = processingFees;
  }

  public String getAdvanceEmi() {
    return advanceEmi;
  }

  public void setAdvanceEmi(String advanceEmi) {
    this.advanceEmi = advanceEmi;
  }

  public String getBrandSubvention() {
    return brandSubvention;
  }

  public void setBrandSubvention(String dealerSubvention) {
    this.brandSubvention = dealerSubvention;
  }

  public String getMerchantSubvention() {
    return merchantSubvention;
  }

  public void setMerchantSubvention(String merchantSubvention) {
    this.merchantSubvention = merchantSubvention;
  }

  public String getBankSubvention() {
    return bankSubvention;
  }

  public void setBankSubvention(String bankSubvention) {
    this.bankSubvention = bankSubvention;
  }

  public String getInnovitiSubvention() {
    return innovitiSubvention;
  }

  public void setInnovitiSubvention(String innovitiSubvention) {
    this.innovitiSubvention = innovitiSubvention;
  }

  public String getRoi() {
    return roi;
  }

  public void setRoi(String roi) {
    this.roi = roi;
  }

  public Date getSchemeStartDate() {
    return schemeStartDate;
  }

  public void setSchemeStartDate(Date schemeStartDate) {
    this.schemeStartDate = schemeStartDate;
  }

  public Date getSchemeEndDate() {
    return schemeEndDate;
  }

  public void setSchemeEndDate(Date schemeEndDate) {
    this.schemeEndDate = schemeEndDate;
  }

  public BigDecimal getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(BigDecimal maxAmount) {
    this.maxAmount = maxAmount;
  }

  public BigDecimal getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(BigDecimal minAmount) {
    this.minAmount = minAmount;
  }

  public String getGenScheme() {
    return genScheme;
  }

  public void setGenScheme(String genScheme) {
    this.genScheme = genScheme;
  }

  public CashbackType getCashbackFlag() {
    return cashbackFlag;
  }

  public void setCashbackFlag(CashbackType cashbackFlag) {
    this.cashbackFlag = cashbackFlag;
  }

  public String getInnoIssuerSchemeCode() {
    return innoIssuerSchemeCode;
  }

  public void setInnoIssuerSchemeCode(String innoIssuerSchemeCode) {
    this.innoIssuerSchemeCode = innoIssuerSchemeCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  public String getIssuerSchemeDescription() {
    return issuerSchemeDescription;
  }

  public void setIssuerSchemeDescription(String issuerSchemeDescription) {
    this.issuerSchemeDescription = issuerSchemeDescription;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(bajajIssuerSchemeCode);
    builder.append("|");
    builder.append(schemeDisplayName);
    builder.append("|");
    builder.append(issuerSchemeDescription);
    builder.append("|");
    builder.append(issuerBank);
    builder.append("|");
    builder.append(innoIssuerBankCode);
    builder.append("|");
    builder.append(innoTenureCode);
    builder.append("|");
    builder.append(processingFees);
    builder.append("|");
    builder.append(advanceEmi);
    builder.append("|");
    builder.append(brandSubvention);
    builder.append("|");
    builder.append(merchantSubvention);
    builder.append("|");
    builder.append(bankSubvention);
    builder.append("|");
    builder.append(innovitiSubvention);
    builder.append("|");
    builder.append(roi);
    builder.append("|");
    builder.append(maxAmount);
    builder.append("|");
    builder.append(minAmount);
    builder.append("|");
    builder.append(genScheme);
    builder.append("|");
    builder.append(cashbackFlag);
    builder.append("|");
    builder.append(innoIssuerSchemeCode);
    return builder.toString();
  }
  
  public void copyAuditDetailsInto(Scheme copy) {
	  copy.setRecordActive(isRecordActive());
	  copy.setCrtupdReason(getCrtupdReason());
	  copy.setCrtupdStatus(getCrtupdStatus());
	  copy.setCrtupdUser(getCrtupdUser());
  }
  
  public void copyNonAuditDetailsInto(Scheme copy) {
	  copy.schemeComposite = null;
	  copy.tenure = null;
	  copy.issuerBank = null;
	  copy.bajajIssuerSchemeCode = bajajIssuerSchemeCode;
	  copy.schemeDisplayName = schemeDisplayName;
	  copy.issuerSchemeDescription = issuerSchemeDescription;
	  copy.processingFees = processingFees;
	  copy.advanceEmi = advanceEmi;
	  copy.brandSubvention = brandSubvention;
	  copy.merchantSubvention = merchantSubvention;
	  copy.bankSubvention = bankSubvention;
	  copy.innovitiSubvention = innovitiSubvention;
	  copy.roi = roi;
	  copy.schemeStartDate = schemeStartDate;
	  copy.schemeEndDate = schemeEndDate;
	  copy.maxAmount = maxAmount;
	  copy.minAmount = minAmount;
	  copy.genScheme = genScheme;
	  copy.cashbackFlag = cashbackFlag;
  }
  
}
