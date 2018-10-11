package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import com.innoviti.emi.core.controller.SchemeController;
import com.innoviti.emi.entity.core.Scheme;

@Relation(value = "scheme", collectionRelation = "schemes")
public class SchemeResource extends ResourceSupport {

  private String innoIssuerSchemeCode;

  private Date crtupdDate;

  private String bajajIssuerSchemeCode;

  private String schemeDisplayName;

  private String innoIssuerBankCode;
  private String innoIssuerBankName;

  private String innoTenureCode;
  private String innoTenureName;

  private String processingFees;

  private String advanceEmi;

  private String brandSubvention;

  private String merchantSubvention;

  private String bankSubvention;

  private String innovitiSubvention;

  private String roi;

  private Date schemeStartDate;

  private String schemeStartDateValue;

  private Date schemeEndDate;

  private String schemeEndDateValue;

  private BigDecimal maxAmount;

  private BigDecimal minAmount;

  private String genScheme;

  private String cashbackFlag;

  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  public SchemeResource(Scheme scheme) {
    this.innoIssuerSchemeCode = scheme.getSchemeComposite().getInnoIssuerSchemeCode();
    this.crtupdDate = scheme.getSchemeComposite().getCrtupdDate();
    this.bajajIssuerSchemeCode = scheme.getBajajIssuerSchemeCode();
    this.schemeDisplayName = scheme.getSchemeDisplayName();
    this.innoIssuerBankCode =
        scheme.getIssuerBank().getIssuerBankComposite().getInnoIssuerBankCode();
    this.innoIssuerBankName = scheme.getIssuerBank().getIssuerBankDisplayName();
    this.innoTenureCode = scheme.getTenure().getTenureComposite().getInnoTenureCode();
    this.innoTenureName = scheme.getTenure().getTenureDisplayName();
    this.processingFees = scheme.getProcessingFees();
    this.advanceEmi = scheme.getAdvanceEmi();
    this.brandSubvention = scheme.getBrandSubvention();
    this.merchantSubvention = scheme.getMerchantSubvention();
    this.bankSubvention = scheme.getBankSubvention();
    this.innovitiSubvention = scheme.getInnovitiSubvention();
    this.roi = scheme.getRoi();
    this.schemeStartDate = scheme.getSchemeStartDate();
    this.schemeEndDate = scheme.getSchemeEndDate();
    this.schemeStartDateValue =

        new SimpleDateFormat("yyyy-MM-dd").format(scheme.getSchemeStartDate());
    this.schemeEndDateValue = new SimpleDateFormat("yyyy-MM-dd").format(scheme.getSchemeEndDate());
    this.maxAmount = scheme.getMaxAmount();
    this.minAmount = scheme.getMinAmount();
    this.genScheme = scheme.getGenScheme();

    this.cashbackFlag = scheme.getCashbackFlag() != null ? scheme.getCashbackFlag().toString() : "";
    this.isRecordActive = scheme.isRecordActive();
    this.crtupdReason = scheme.getCrtupdReason();
    this.crtupdStatus = scheme.getCrtupdStatus();
    this.crtupdUser = scheme.getCrtupdUser();
  }

  public String createSchemeLink() {
    return linkTo(SchemeController.class).slash(getInnoIssuerSchemeCode()).slash(getCrtupdDate())
        .withRel("scheme").getHref();
  }

  public void createSelfLink() {
    this.add(linkTo(SchemeController.class).slash(getInnoIssuerSchemeCode()).withSelfRel());
  }

  public String getInnoIssuerSchemeCode() {
    return innoIssuerSchemeCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  public String getBajajIssuerSchemeCode() {
    return bajajIssuerSchemeCode;
  }

  public String getSchemeDisplayName() {
    return schemeDisplayName;
  }

  public String getInnoIssuerBankCode() {
    return innoIssuerBankCode;
  }

  public String getInnoTenureCode() {
    return innoTenureCode;
  }

  public String getInnoIssuerBankName() {
    return innoIssuerBankName;
  }

  public String getInnoTenureName() {
    return innoTenureName;
  }

  public String getProcessingFees() {
    return processingFees;
  }

  public String getAdvanceEmi() {
    return advanceEmi;
  }

  public String getBrandSubvention() {
    return brandSubvention;
  }

  public String getMerchantSubvention() {
    return merchantSubvention;
  }

  public String getBankSubvention() {
    return bankSubvention;
  }

  public String getInnovitiSubvention() {
    return innovitiSubvention;
  }

  public String getRoi() {
    return roi;
  }

  public Date getSchemeStartDate() {
    return schemeStartDate;
  }

  public Date getSchemeEndDate() {
    return schemeEndDate;
  }

  public String getGenScheme() {
    return genScheme;
  }

  public String getCashbackFlag() {
    return cashbackFlag;
  }

  public String getCrtupdReason() {
    return crtupdReason;
  }

  public String getCrtupdStatus() {
    return crtupdStatus;
  }

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public String getSchemeStartDateValue() {
    return schemeStartDateValue;
  }

  public void setSchemeStartDateValue(String schemeStartDateValue) {
    this.schemeStartDateValue = schemeStartDateValue;
  }

  public String getSchemeEndDateValue() {
    return schemeEndDateValue;
  }

  public void setSchemeEndDateValue(String schemeEndDateValue) {
    this.schemeEndDateValue = schemeEndDateValue;
  }

  public boolean isRecordActive() {
    return isRecordActive;
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

}
