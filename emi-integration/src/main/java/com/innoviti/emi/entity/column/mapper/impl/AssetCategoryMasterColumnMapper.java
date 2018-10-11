package com.innoviti.emi.entity.column.mapper.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.CategoryComposite;
import com.innoviti.emi.entity.master.AssetCategoryMaster;
import com.innoviti.emi.repository.core.CategoryRepository;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;

@Component
public class AssetCategoryMasterColumnMapper
    implements ColumnMapper<AssetCategoryMaster, Category> {

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  private CategoryRepository categoryRepository;
  
  @Override
  public Category mapColumn(AssetCategoryMaster assetCategoryMaster) {
    Integer catgid = assetCategoryMaster.getAssetCategoryMasterComposite().getId();
    
    Category foundCategory = categoryRepository.findTop1ByBajajCategoryCode(String.valueOf(catgid));
    String innoCategoryCode = null;
    if(foundCategory != null){
      innoCategoryCode = foundCategory.getCategoryComposite().getInnoCategoryCode();
    }
    else{
      SequenceType sequenceType = SequenceType.CATEGORY;
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(sequenceType.getSequenceName());
      String newInsertId = String.valueOf(seqNum);
      innoCategoryCode =
          sequenceType.getSequenceIdentifier() + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);
    }

    Category category = new Category();
    category.setBajajCategoryCode(
        assetCategoryMaster.getAssetCategoryMasterComposite().getId().toString());
    category.setCategoryDesc(assetCategoryMaster.getDescription());
    category.setCategoryDisplayName(assetCategoryMaster.getDescription());
    category.setRecordActive(true);
    category.setCrtupdDate(new Date());
    category.setCrtupdReason(assetCategoryMaster.getCrtupdReason());
    category.setCrtupdStatus("N");
    category.setCrtupdUser(assetCategoryMaster.getCrtupdUser());
    CategoryComposite categoryComposite = new CategoryComposite();
    categoryComposite.setInnoCategoryCode(innoCategoryCode);
    categoryComposite
        .setCrtupdDate(assetCategoryMaster.getAssetCategoryMasterComposite().getCrtupdDate());
    category.setCategoryComposite(categoryComposite);

    return category;
  }

}
