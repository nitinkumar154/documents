package com.innoviti.emi.service.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.SchemeModelTerminalComposite;
import com.innoviti.emi.service.CrudService;

public interface SchemeModelTerminalService
    extends CrudService<SchemeModelTerminal, SchemeModelTerminalComposite> {

  public List<SchemeModelTerminal> getSchemeModelTerminalListById(String utid,
      String innoSchemeModelCode);
  
  public List<SchemeModelTerminal> getSchemeModelTerminalListById(
	      String innoSchemeModelCode);

  public List<SchemeModelTerminal> createIssuerSchemeModel(List<SchemeModelTerminal> schemeModel);
  
  Page<SchemeModelTerminal> findAllSchemeModelTerminal(Pageable pageable);

  public Page<SchemeModelTerminal> findAllSchemeModelTerminalForDataMovement(
      String recordStatus, Pageable pageable);

  public boolean findByCodeIfExists(String innoSchemeModelCode, String utid);

  public List<SchemeModelTerminal> updateSchemeModelTerminalSyncStatus(String innoSchemeModelCode,
      String utid, String status);
}
