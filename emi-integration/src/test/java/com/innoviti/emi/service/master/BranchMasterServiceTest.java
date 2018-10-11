package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.BranchMaster;
import com.innoviti.emi.entity.master.BranchMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class BranchMasterServiceTest extends SetupWithJPA {

  @Autowired
  private BranchMasterService branchService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  
  private BranchMaster createBranch(){
    BranchMaster branch = new BranchMaster();
    branch.setBankCode("BT10");
    branch.setBranchAddr1("Cox town");
    branch.setBranchAddr2("4th cross");
    branch.setBranchAddr3("Vivek nagar");
    branch.setBranchAddr4("bangalore");
    branch.setBranchCity("Banglore");
    BranchMasterComposite composite = new BranchMasterComposite();
    composite.setBranchCode("121221");
    composite.setCrtupdDate(new Date());
    branch.setBranchMasterComposite(composite);
    branch.setBranchContactPersonName("mahesh");
    branch.setBranchEmailId("coxtown@axis.com");
    branch.setBranchName("axis");
    branch.setBranchPhoneNumber("987654321");
    branch.setBranchPin("12345");
    branch.setBranchRegionCode("123");
    branch.setBranchState("Karanataka");
    branch.setBranchStateCode("KA");
    branch.setBranchType("axis");
    branch.setBranchZoneCode("12");
    branch.setCorporateId("CORPO12212");
    branch.setMainBranch("Delhi");
    branch.setMainBranchFlag("Y");
    branch.setBranchAreaCode("KA");
    branch.setCrtupdReason("test");
    branch.setCrtupdReason("test");
    branch.setCrtupdStatus('N');
    branch.setCrtupdUser("test");
    
    return branch;
  }
  @Test
  public void createBranchTest() {
    BranchMaster branch = createBranch();
    BranchMaster createdBranch = branchService.create(branch);

    Assert.assertEquals("Branch saved", branch.getBranchMasterComposite(), createdBranch.getBranchMasterComposite());

  }
  
  @Test
  public void findBranchTest(){
    BranchMaster branch = createBranch();
    BranchMaster createdBranch = branchService.create(branch);
    Assert.assertEquals("Branch saved", branch.getBranchMasterComposite(), createdBranch.getBranchMasterComposite());
    
    BranchMaster foundBranch = branchService.findById(branch.getBranchMasterComposite());
    
    Assert.assertEquals("Branch saved", createdBranch.getBranchMasterComposite(), foundBranch.getBranchMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteByIdTest() {
    BranchMaster branch = createBranch();
    BranchMaster createdBranch = branchService.create(branch);
    Assert.assertEquals("Branch saved", branch.getBranchMasterComposite(), createdBranch.getBranchMasterComposite());
    
    branchService.deleteById(branch.getBranchMasterComposite());
    branchService.findById(branch.getBranchMasterComposite());
  }
}
