/*
 *  This file was created by Rick Hightower of ArcMinds Inc. 
 *
 */
package com.goochiepoochie.dao;

import com.goochiepoochie.model.PetOwner;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import java.util.List;

public class PetOwnerDAOImpl extends JpaDaoSupport implements PetOwnerDAO {

  public void addPetOwner(PetOwner petOwner) {
    getJpaTemplate().merge(petOwner);
  }

  public PetOwner getPetOwnerInfo(PetOwner petOwner) {
    PetOwner po = null;

    List list = getJpaTemplate().
        find("from PetOwner petOwner " + "where petOwner.lastName = ?",
            petOwner.getLastName());

    if (list.size() > 0) {
      po = (PetOwner) list.get(0);
    }

    return po;
  }

}
