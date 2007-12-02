package com.goochiepoochie.dao;

import com.goochiepoochie.model.PetOwner;
import org.springframework.dao.DataAccessException;

public interface PetOwnerDAO {
	
  public abstract void addPetOwner(PetOwner petOwner) throws DataAccessException;

  public abstract PetOwner getPetOwnerInfo(PetOwner pPetOwner);  
}
