package com.innoviti.emi.service;

import java.io.Serializable;
import java.util.List;

import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.exception.RequestNotCompletedException;
import com.innoviti.emi.repository.BaseRepository;

public abstract class CrudServiceHelper<T, ID extends Serializable> {
  
  private BaseRepository<T, ID> baseRepository;
  
  
  public CrudServiceHelper(BaseRepository<T, ID> baseRespository) {
   this.baseRepository = baseRespository;
  }
  
  public T helperCreate(T u){
    T t = baseRepository.saveAndFlush(u);
    if (t == null) {
      throw new RequestNotCompletedException("Exception while saving entity", 417);
    }
    return t;
  }
  
  public T helperUpdate(T u) {
    if (u == null)
      throw new BadRequestException("Entity to update must not be null !!");
    T t = baseRepository.save(u);
    if (t == null)
      throw new RequestNotCompletedException("Error ! Could not update requested entity.", 417);
    return t;
  }
  
  public void helperDeleteById(ID id){
    if(id == null){
      throw new BadRequestException("Please provide id to delete entity", 400);
    }
    baseRepository.delete(id);
  }
  
  public T helperFindById(ID id){
    if(id == null){
      throw new BadRequestException("Please provide id to search entity", 400);
    }
    T t = baseRepository.findOne(id);
    if(t == null){
      throw new NotFoundException("No record found for id : "+id, 404);
    }
    return t;
  }
  
  public List<T> helperFindAll(){
    List<T> result = baseRepository.findAll();
    if(result == null || result.isEmpty()){
      throw new NotFoundException("No records found", 404);
    }
    return result;
  }
}
